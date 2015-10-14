package fr.alma.middleware.loki.common;

import fr.alma.middleware.loki.common.ServerAddress;

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

	private ServerAddress address;
	
	private RMIClient(ServerAddress address) {
		this.address = address;
	}
	
	public static RMIClient getInstance() throws RemoteException {
		if(RMIClient.instance == null){
			RMIClient.instance = new RMIClient(
				new ServerAddress(RMIClient.SERVER_IP,RMIClient.SERVER_PORT)
			);
		}
		return RMIClient.instance;
	}

	public Remote retrieve(String name) throws RemoteException {
		System.out.println("rmi://" + this.address.getIp() +":" + this.address.getPort() + "/" + RMIClient.APP_NAME + "/" + name);
		try {
			return Naming.lookup("rmi://" + this.address.getIp() +":" + this.address.getPort() + "/" + RMIClient.APP_NAME + "/" + name);
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

	public String getServerIp(){
		return this.address.getIp();
	}
	
	public int getServerPort(){
		return this.address.getPort();
	}
	
	public void setServerIp(String serverIp){
		this.address.setIp(serverIp);
	}
	
	public void setServerPort(int serverPort){
		this.address.setPort(serverPort);
	}
}
