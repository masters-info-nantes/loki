package fr.alma.middleware.loki.server;

import fr.alma.middleware.loki.common.IClient;
import fr.alma.middleware.loki.common.IServer;
import fr.alma.middleware.loki.common.ITopic;
import fr.alma.middleware.loki.common.Message;
import fr.alma.middleware.loki.common.TopicAlreadyExistedException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Server extends UnicastRemoteObject implements IServer,Serializable {

	private HashMap<String, ITopic> topics;
	private List<IClient> clients;
	
	public Server() throws RemoteException {
		super();

		this.clients = new LinkedList<IClient>();
		this.topics = new HashMap<String, ITopic>();

		try {
			createTopic(ITopic.GENERAL_TOPIC_NAME);
		}
		catch(TopicAlreadyExistedException e) {
			System.err.println("[Server] General topic already exists");
		}
	}
	
	public ITopic getTopic(String title) throws RemoteException {
		return this.topics.get(title);
	}
	
	public List<String> getTopicList() throws RemoteException {
		return new ArrayList(this.topics.keySet());
	}
	
	public ITopic createTopic(String title) throws RemoteException,TopicAlreadyExistedException {

		if(this.topics.containsKey(title)) {
			throw new TopicAlreadyExistedException();
		}

		ITopic topic = new Topic(title);
		this.topics.put(title, topic);
		
		for(IClient client : this.clients) {
			client.topicCreated(topic);
		}
		
		return topic;
	}
	
	public void removeTopic(ITopic topic) throws RemoteException {
		ITopic previous = this.topics.remove(topic.getName());
		
		if(previous != null) {
			for(IClient client : this.clients) {
				client.topicRemoved(topic);
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
}
