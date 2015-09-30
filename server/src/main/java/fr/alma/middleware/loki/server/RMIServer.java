package fr.alma.middleware.loki.server;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {

	// Server settings
	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 9999;

	private static final String APP_NAME = "Loki";

	// Singleton
	private static RMIServer instance;

	public static RMIServer getInstance() throws RemoteException {
		if(RMIServer.instance == null){
			RMIServer.instance = new RMIServer();
		}
		return RMIServer.instance;
	}

	private RMIServer() throws RemoteException {
		LocateRegistry.createRegistry(RMIServer.SERVER_PORT);
	}

	public void share(UnicastRemoteObject object, String name) throws RemoteException {
		String url = "rmi://" + RMIServer.SERVER_IP +":" + RMIServer.SERVER_PORT + "/" + RMIServer.APP_NAME + "/" + name;
		try {
			Naming.rebind(url, object);
		}
		catch (MalformedURLException e) {
			System.err.println("RMI: Object URL malformed");
			e.printStackTrace();
		}
	}
}
