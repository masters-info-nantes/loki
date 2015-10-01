package fr.alma.middleware.loki.server;

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

	private Registry localRegistry;

	public static RMIServer getInstance() throws RemoteException {

		if(RMIServer.instance == null){
			RMIServer.instance = new RMIServer();
		}
		return RMIServer.instance;
	}

	private RMIServer() throws RemoteException {
		// create a Security Manager that allow everything
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager() {
				@Override
				public void checkPermission(Permission perm) {
					return;
				}
			});
		}

		this.localRegistry = LocateRegistry.createRegistry(RMIServer.SERVER_PORT);
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
