package fr.alma.middleware.loki.common;

import java.rmi.*;

public interface ITopic extends Remote {

	public void subscribe(IClient client) throws RemoteException;
	public void unsubscribe(IClient client) throws RemoteException;
	public void broadcast(Message message) throws RemoteException;
}
