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
import java.util.Set;

public class Topic extends UnicastRemoteObject implements ITopic, Serializable {
	
	private String name;
	private Set<IClientTopic> subscribers;
	private List<Message> messageHistory;
	
	public Topic(String name) throws RemoteException {
        super();
		this.name = name;
        this.subscribers = new HashSet<IClientTopic>();
		this.messageHistory = new ArrayList<Message>();
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
		this.messageHistory.add(message);

		LinkedList offlineClients = new LinkedList<IClientTopic>();
		for(IClientTopic client : this.subscribers){
			try {
				client.newMessage(message);
			}
			catch(Exception ex) {
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
