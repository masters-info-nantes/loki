package fr.alma.middleware.loki.server;

import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;

public class App {
	
    public static void main(String[] args) {
		System.out.println("Running server...");
		try {
			Server server = new Server();
			RMIServer rmi = RMIServer.getInstance();
			rmi.share(server,"Server");
			
			while(true);
		} catch(RemoteException ex) {
			System.err.println("Can't create remote server object");
            ex.printStackTrace();
		}
    }
}
