package fr.alma.middleware.loki.common;

import fr.alma.middleware.loki.common.ServerAddress;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Permission;

public class RMIServer {

	// Server settings
	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 9999;

	private static final String APP_NAME = "Loki";

	// Singleton
	private static RMIServer instance;
	
	private ServerAddress address;

	private Registry localRegistry;

	public static RMIServer getInstance() throws RemoteException {

		if(RMIServer.instance == null){
			RMIServer.instance = new RMIServer(
				new ServerAddress(RMIServer.SERVER_IP,RMIServer.SERVER_PORT)
			);
		}
		return RMIServer.instance;
	}

	private RMIServer(ServerAddress address) throws RemoteException {
		this.address = address;
		// create a Security Manager that allow everything
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager() {
				@Override
				public void checkPermission(Permission perm) {
					return;
				}
			});
		}

		this.localRegistry = LocateRegistry.createRegistry(this.address.getPort());
	}

	public void share(UnicastRemoteObject object, String name) throws RemoteException {
		String url = "rmi://" + this.address.getIp() +":" + this.address.getPort() + "/" + RMIServer.APP_NAME + "/" + name;
		this.localRegistry.rebind(url, object);
	}
	
	public String getIp(){
		return this.address.getIp();
	}
	
	public int getPort(){
		return this.address.getPort();
	}
	
	public void setIp(String ip){
		this.address.setIp(ip);
	}
	
	public void setPort(int port){
		this.address.setPort(port);
	}
}
