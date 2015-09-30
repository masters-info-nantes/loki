package fr.alma.middleware.loki.server;

import fr.alma.middleware.loki.common.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server extends UnicastRemoteObject implements IServer,Serializable {

	private HashMap<String, ITopic> topics;
	
	public Server() throws RemoteException {
		super();
		this.topics = new HashMap<String, ITopic>();
		try {
			createTopic("General");
		}catch(TopicAlreadyExistedException e) {}
	}
	
	public ITopic getTopic(String title) throws RemoteException {
		return this.topics.get(title);
	}
	
	public List<Message> getTopicList() throws RemoteException {
		return new ArrayList(this.topics.keySet());
	}
	
	public ITopic createTopic(String title) throws RemoteException,TopicAlreadyExistedException {
		ITopic topic = new Topic();
		if(this.topics.get(title) != null) {
			throw new TopicAlreadyExistedException();
		}
		this.topics.put(title,topic);
		return topic;
	}
}
