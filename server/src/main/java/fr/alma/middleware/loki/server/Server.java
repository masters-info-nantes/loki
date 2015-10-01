package fr.alma.middleware.loki.server;

import fr.alma.middleware.loki.common.IServer;
import fr.alma.middleware.loki.common.ITopic;
import fr.alma.middleware.loki.common.Message;
import fr.alma.middleware.loki.common.TopicAlreadyExistedException;

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
			createTopic(ITopic.GENERAL_TOPIC_NAME);
		}
		catch(TopicAlreadyExistedException e) {
			System.err.println("[Server] General topic already exists");
		}
	}
	
	public ITopic getTopic(String title) throws RemoteException {
		return this.topics.get(title);
	}
	
	public List<Message> getTopicList() throws RemoteException {
		return new ArrayList(this.topics.keySet());
	}
	
	public ITopic createTopic(String title) throws RemoteException,TopicAlreadyExistedException {

		if(this.topics.containsKey(title)) {
			throw new TopicAlreadyExistedException();
		}

		ITopic topic = new Topic();
		this.topics.put(title, topic);

		return topic;
	}
}
