package fr.alma.middleware.loki.server;

import fr.alma.middleware.loki.common.IClientTopic;
import fr.alma.middleware.loki.common.ITopic;
import fr.alma.middleware.loki.common.Message;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.mapdb.*;

public class Topic extends UnicastRemoteObject implements ITopic, Serializable {
	
	public final static String DB_TOPIC_PREFIX = "Topic.";
	
	private String name;
	private Set<IClientTopic> subscribers;
	private List<Message> messageHistory;
	
	private DB db;
	private Map<Integer,Message> dbHistory;
	
	public Topic(String name,DB db) throws RemoteException {
        super();
		this.name = name;
        this.subscribers = new HashSet<IClientTopic>();
		this.messageHistory = new ArrayList<Message>();
		this.db = db;
		
		if(this.db.exists(DB_TOPIC_PREFIX+this.name)) {
			this.dbHistory = this.db.hashMap(DB_TOPIC_PREFIX+this.name);
			for(int i=0 ; i<this.dbHistory.size() ; i++) {
				Message msg = this.dbHistory.get(i);
				msg.setTopic(this);
				this.messageHistory.add(i,msg);
			}
		} else {
			this.dbHistory = this.db.hashMap(DB_TOPIC_PREFIX+this.name);
		}
    }
	
	public String getName() throws RemoteException {
		return this.name;
	}
	
	public void setName(String name) throws RemoteException {
		this.name = name;
	}

	@Override
	public void subscribe(IClientTopic client) throws RemoteException {
		this.subscribers.add(client);
	}

	@Override
	public void unsubscribe(IClientTopic client) throws RemoteException {
		this.subscribers.remove(client);
	}

	@Override
	public void broadcast(Message message) throws RemoteException {
		this.dbHistory.put(this.messageHistory.size(),message);
		this.messageHistory.add(message);
		
		LinkedList offlineClients = new LinkedList<IClientTopic>();
		for(IClientTopic client : this.subscribers){
			try {
				client.newMessage(message);
			} catch(Exception ex) {
				offlineClients.add(client);
			}
        }
        this.subscribers.removeAll(offlineClients);
	}

	@Override
	public List<Message> history() throws RemoteException {
		return this.messageHistory;
	}
}
