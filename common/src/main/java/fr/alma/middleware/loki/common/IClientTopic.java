package fr.alma.middleware.loki.common;

import java.rmi.*;

public interface IClientTopic extends Remote {
	public void newMessage(Message message) throws RemoteException;
}
