package fr.alma.middleware.loki.common;

import java.util.List;
import java.rmi.*;

public interface IServer extends Remote {

	public ITopic getTopic(String title) throws RemoteException;
	public List<String> getTopicList() throws RemoteException;
	public ITopic createTopic(String title) throws RemoteException,TopicAlreadyExistedException;
	
	public void registerClient(IClient client) throws RemoteException;
	public void unregisterClient(IClient client) throws RemoteException;
}

