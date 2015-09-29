package fr.alma.middleware.interfaces;

import java.util.List;
import java.rmi.*;

public interface IServerForum extends Remote {

	public ITopic getTopic(String title) throws RemoteException;
	public List<String> getTopicList() throws RemoteException;
	public void postTopic(String title, ITopic topic) throws RemoteException;
}

