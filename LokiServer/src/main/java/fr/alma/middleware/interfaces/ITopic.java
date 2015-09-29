package fr.alma.middleware.interfaces;

import java.rmi.*;

public interface ITopic extends Remote {

	public void subscribe(IClientDisplay client) throws RemoteException;
	public void unsubscribe(IClientDisplay client) throws RemoteException;
	public void broadcast(String message) throws RemoteException;
}
