package fr.alma.middleware.loki.common;

import java.rmi.*;

public interface IClient extends Remote {
	public String getNickname() throws RemoteException;
	public void setNickname(String nickname) throws RemoteException;
	
	public void topicCreated(ITopic topic) throws RemoteException;
	public void topicRemoved(ITopic topic) throws RemoteException;
}
