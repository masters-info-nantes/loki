package fr.alma.middleware.loki.common;

import java.rmi.*;
import java.util.List;

public interface ITopic extends Remote {

	public static final String GENERAL_TOPIC_NAME = "General";
	
	public String getName() throws RemoteException;
	public void setName(String name) throws RemoteException;
	
	public void subscribe(IClient client) throws RemoteException;
	public void unsubscribe(IClient client) throws RemoteException;
	public void broadcast(Message message) throws RemoteException;
	public List<Message> history() throws RemoteException;
}
