package fr.alma.middleware.loki.common;

import java.rmi.*;

public interface IClient extends Remote {
	public void newMessage(Message message) throws RemoteException;
}
