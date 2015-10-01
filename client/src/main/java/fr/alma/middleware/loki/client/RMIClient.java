package fr.alma.middleware.loki.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class RMIClient {

	// Server settings
	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 9999;

	private static final String APP_NAME = "Loki";

	// Singleton
	private static RMIClient instance;

	public static RMIClient getInstance() throws RemoteException {
		if(RMIClient.instance == null){
			RMIClient.instance = new RMIClient();
		}
		return RMIClient.instance;
	}

	public Remote retrieve(String name) throws RemoteException {
		try {
			return Naming.lookup("rmi://" + RMIClient.SERVER_IP +":" + RMIClient.SERVER_PORT + "/" + RMIClient.APP_NAME + "/" + name);
		}
		catch (NotBoundException e) {
			e.printStackTrace();
		}
		catch (MalformedURLException e) {
			System.err.println("RMI: Retrieve malformed URL");
			e.printStackTrace();
		}
		return null;
	}
}
