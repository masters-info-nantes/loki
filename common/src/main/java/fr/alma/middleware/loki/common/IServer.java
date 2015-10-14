package fr.alma.middleware.loki.common;

import java.util.List;
import java.rmi.*;

public interface IServer extends IClient {

	public ITopic getTopic(String title) throws RemoteException;
	public List<String> getTopicList() throws RemoteException;
	public ITopic createTopic(String title) throws RemoteException,TopicAlreadyExistedException;
	public void removeTopic(ITopic topic) throws RemoteException;
	
	public void registerClient(IClient client) throws RemoteException;
	public void unregisterClient(IClient client) throws RemoteException;
	
	public void registerServer(IServer server) throws RemoteException;
	public void unregisterServer(IServer server) throws RemoteException;
	public List<IServer> getRegisteredServer() throws RemoteException;
}

