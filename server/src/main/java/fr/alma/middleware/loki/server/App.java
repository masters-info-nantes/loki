package fr.alma.middleware.loki.server;

import fr.alma.middleware.loki.common.IServer;
import fr.alma.middleware.loki.common.RMIClient;
import fr.alma.middleware.loki.common.RMIServer;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class App {
	
    public static void main(String[] args) {
		String databasePath = "storage.db";
		if(args.length >= 1) {
			databasePath = args[0];
		} else {
			System.err.println("You must specify a database path");
			System.exit(0);
		}
		try {
			Server server = new Server(databasePath);

			RMIServer rmi = RMIServer.getInstance();
			if(args.length >= 2){
				String[] address = args[1].split(":");
				rmi.setIp(address[0]);
				if(address.length > 1) {
					rmi.setPort(Integer.parseInt(address[1]));
				}
				
				if(args.length >= 3) {
					String[] secondAddress = args[2].split(":");
					if(secondAddress.length > 1) {
						RMIClient rmiClient = RMIClient.getInstance();
						rmiClient.setServerIp(secondAddress[0]);
						rmiClient.setServerPort(Integer.parseInt(secondAddress[1]));
						IServer secondServer = (IServer) rmiClient.retrieve("Server");
						secondServer.registerServer(server);
						
						List<IServer> knownServers = secondServer.getRegisteredServer();
						for(IServer s : knownServers) {
							server.registerServer(s);
						}
					}
				}
			}
			rmi.share(server,"Server");
			System.out.println("Running server on " + rmi.getIp() + ":" + rmi.getPort());
			
			boolean run = true;
			Scanner in = new Scanner(System.in);
			String cmd = "";
			
			while(run) {
				System.out.print(" > ");
				cmd = in.nextLine();
				switch(cmd) {
				case "stop":
					stopServer();
				default:
					System.out.println("Unrecognize command.");
				}
			}
			
		} catch(RemoteException ex) {
			System.err.println("Can't create remote server object");
            ex.printStackTrace();
		}
    }
    
    public static void stopServer() {
		System.exit(0);
	}
}
