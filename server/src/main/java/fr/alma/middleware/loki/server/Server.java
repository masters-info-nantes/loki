package fr.alma.middleware.loki.server;

import fr.alma.middleware.loki.common.IClient;
import fr.alma.middleware.loki.common.IServer;
import fr.alma.middleware.loki.common.ITopic;
import fr.alma.middleware.loki.common.Message;
import fr.alma.middleware.loki.common.TopicAlreadyExistedException;

import java.io.Serializable;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.mapdb.*;

public class Server extends UnicastRemoteObject implements IServer,Serializable {

	private final static String DB_TOPIC_LIST = "topicList";
	private HashMap<String, ITopic> topics;
	private List<IClient> clients;
	
	private String nickname;
	
	private DB db;
	private Set<String> dbTopics;
	
	public Server() throws RemoteException {
		super();
		
		this.nickname = UUID.randomUUID().toString();
		
		this.clients = new LinkedList<IClient>();
		this.topics = new HashMap<String, ITopic>();
		
		this.db = DBMaker.fileDB(new File("storage.db"))
			.closeOnJvmShutdown()
			.transactionDisable()// no need to commit to save
			.make();
		
		if(db.exists(DB_TOPIC_LIST)) {
			this.dbTopics = this.db.treeSet(DB_TOPIC_LIST);
			for(String topicName : this.dbTopics) {
				ITopic topic = new Topic(topicName,this.db);
				this.topics.put(topicName, topic);
			}
		} else {
			this.dbTopics = this.db.treeSet(DB_TOPIC_LIST);
			try {
				createTopic(ITopic.GENERAL_TOPIC_NAME);
			}
			catch(TopicAlreadyExistedException e) {
				System.err.println("[Server] General topic already exists");
			}
		}
	}
	
	public ITopic getTopic(String title) throws RemoteException {
		return this.topics.get(title);
	}
	
	public List<String> getTopicList() throws RemoteException {
		return new ArrayList(this.topics.keySet());
	}
	
	public ITopic createTopic(String title) throws RemoteException,TopicAlreadyExistedException {
		return this.createTopic(title,true);
	}
	
	public ITopic createTopic(String title, boolean broadcastChange) throws RemoteException,TopicAlreadyExistedException {

		if(this.topics.containsKey(title)) {
			throw new TopicAlreadyExistedException();
		}

		ITopic topic = new Topic(title,this.db);
		this.topics.put(title, topic);
		this.dbTopics.add(title);
		
		LinkedList offlineClients = new LinkedList<IClient>();
		for(IClient client : this.clients) {
			try {
				client.topicCreated(topic);
			} catch(Exception ex) {
				offlineClients.add(client);
			}
		}
		this.clients.removeAll(offlineClients);
		
		return topic;
	}
	
	public void removeTopic(ITopic topic) throws RemoteException {
		this.removeTopic(topic,true);
	}
	
	public void removeTopic(ITopic topic, boolean broadcastChange) throws RemoteException {
		if(!topic.getName().equals(ITopic.GENERAL_TOPIC_NAME)) {
			ITopic previous = this.topics.remove(topic.getName());
			this.db.delete(Topic.DB_TOPIC_PREFIX+topic.getName());
			this.dbTopics.remove(topic.getName());
			
			if(previous != null) {
				LinkedList offlineClients = new LinkedList<IClient>();
				for(IClient client : this.clients) {
					try {
						client.topicRemoved(topic);
					} catch(Exception ex) {
						offlineClients.add(client);
					}
				}
				this.clients.removeAll(offlineClients);
			}
		}
	}
	
	public void registerClient(IClient client) throws RemoteException {
		this.clients.add(client);
		for(ITopic topic : this.topics.values()) {
			client.topicCreated(topic);
		}
	}
	
	public void unregisterClient(IClient client) throws RemoteException {
		this.clients.remove(client);
	}
	
	
	public String getNickname() throws RemoteException {
		return this.nickname;
	}
	
	public void setNickname(String nickname) throws RemoteException {
		this.nickname = nickname;
	}
	
	public void topicCreated(ITopic topic) throws RemoteException {
		try {
			createTopic(topic.getName(),false);
		} catch(TopicAlreadyExistedException ex) {
			System.out.println("TopicAlreadyExistedException "+topic.getName());
		}
	}
	
	public void topicRemoved(ITopic topic) throws RemoteException {
		removeTopic(getTopic(topic.getName()),false);
	}
	
}
