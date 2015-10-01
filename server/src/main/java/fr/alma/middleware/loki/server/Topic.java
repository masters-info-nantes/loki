package fr.alma.middleware.loki.server;

import fr.alma.middleware.loki.common.IClient;
import fr.alma.middleware.loki.common.ITopic;
import fr.alma.middleware.loki.common.Message;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Topic extends UnicastRemoteObject implements ITopic, Serializable {
	
	private Set<IClient> subscribers;
	
	public Topic() throws RemoteException {
        super();
        this.subscribers = new HashSet<IClient>();
    }
	
	public void subscribe(IClient client) throws RemoteException {
		this.subscribers.add(client);
	}
	
	public void unsubscribe(IClient client) throws RemoteException {
		this.subscribers.remove(client);
	}
	
	public void broadcast(Message message) throws RemoteException {

		System.out.println("[Topic] broadcast [" + message.getAuthor() + " : " + message.getMessage() + "]");

		LinkedList offlineClients = new LinkedList<IClient>();
		for(IClient client : this.subscribers){
			try {
				client.newMessage(message);
			}
			catch(Exception ex) {
				offlineClients.add(client);
			}
        }
        this.subscribers.removeAll(offlineClients);
	}
}
