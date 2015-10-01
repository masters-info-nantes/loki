package fr.alma.middleware.loki.common;

import java.rmi.*;

public interface ITopic extends Remote {

	public static final String GENERAL_TOPIC_NAME = "General";
	
	public void subscribe(IClient client) throws RemoteException;
	public void unsubscribe(IClient client) throws RemoteException;
	public void broadcast(Message message) throws RemoteException;
}