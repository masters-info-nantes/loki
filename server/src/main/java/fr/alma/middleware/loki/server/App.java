package fr.alma.middleware.loki.server;

import fr.alma.middleware.loki.common.IServer;
import fr.alma.middleware.loki.common.RMIClient;
import fr.alma.middleware.loki.common.RMIServer;

import java.rmi.RemoteException;
import java.util.List;

public class App {
	
    public static void main(String[] args) {
		try {
			Server server = new Server();

			RMIServer rmi = RMIServer.getInstance();
			if(args.length >= 1){
				String[] address = args[0].split(":");
				rmi.setIp(address[0]);
				if(address.length > 1) {
					rmi.setPort(Integer.parseInt(address[1]));
				}
				
				if(args.length >= 2) {
					String[] secondAddress = args[1].split(":");
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
			System.out.println("Running server on " + rmi.getIp() + ":" + rmi.getPort());
			rmi.share(server,"Server");

			while(true);
		} catch(RemoteException ex) {
			System.err.println("Can't create remote server object");
            ex.printStackTrace();
		}
    }
}
