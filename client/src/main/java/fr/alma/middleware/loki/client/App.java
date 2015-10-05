package fr.alma.middleware.loki.client;

import fr.alma.middleware.loki.client.gui.TopicWindow;
import fr.alma.middleware.loki.common.IServer;
import fr.alma.middleware.loki.common.ITopic;
import fr.alma.middleware.loki.common.Message;

public class App {
	public static void main(String[] args) {
		TopicWindow window = new TopicWindow("Loki chat");
		
		try {
			Client client = new Client(window);
			window.listenSendButton(client);
			
			RMIClient rmi = RMIClient.getInstance();
			IServer server = (IServer) rmi.retrieve("Server");
			
			System.out.println("Topics list: " + server.getTopicList());
			ITopic topicGeneral = server.getTopic(ITopic.GENERAL_TOPIC_NAME);
			
			topicGeneral.subscribe(client);
			client.addSubscribedTopic(ITopic.GENERAL_TOPIC_NAME,topicGeneral);
		}
		catch (Exception e) {
			System.err.println("Can't access to the server");
			e.printStackTrace();
		}
	}
}
