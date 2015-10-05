package fr.alma.middleware.loki.client;

import fr.alma.middleware.loki.client.gui.MainWindow;
import fr.alma.middleware.loki.client.gui.TopicWindow;
import fr.alma.middleware.loki.common.IServer;
import fr.alma.middleware.loki.common.ITopic;
import fr.alma.middleware.loki.common.Message;

public class App {
	public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow("Loki chat -- main");
		
		try {
			RMIClient rmi = RMIClient.getInstance();
			IServer server = (IServer) rmi.retrieve("Server");
			
			Client client = new Client(server,mainWindow);
			mainWindow.listenCreateButton(client);
			server.registerClient(client);
		}
		catch (Exception e) {
			System.err.println("Can't access to the server");
			e.printStackTrace();
		}
	}
}
