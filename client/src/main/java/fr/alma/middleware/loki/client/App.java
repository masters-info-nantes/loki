package fr.alma.middleware.loki.client;

import fr.alma.middleware.loki.client.gui.MainWindow;
import fr.alma.middleware.loki.common.IServer;
import fr.alma.middleware.loki.common.ITopic;
import fr.alma.middleware.loki.common.Message;

public class App {
	public static void main(String[] args) {
		MainWindow window = new MainWindow("Loki chat");
		
		try {
			Client client = new Client(window);
			
			RMIClient rmi = RMIClient.getInstance();
			IServer server = (IServer) rmi.retrieve("Server");
			
			System.out.println("Topics list: " + server.getTopicList());
			ITopic topicGeneral = server.getTopic(ITopic.GENERAL_TOPIC_NAME);
			
			topicGeneral.subscribe(client);
			
			topicGeneral.broadcast(new Message("Doctor","TARDIS!"));
		}
		catch (Exception e) {
			System.err.println("Can't access to the server");
			e.printStackTrace();
		}
	}
}
