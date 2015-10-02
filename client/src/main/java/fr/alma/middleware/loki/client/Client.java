package fr.alma.middleware.loki.client;

import fr.alma.middleware.loki.client.gui.MainWindow;
import fr.alma.middleware.loki.common.IClient;
import fr.alma.middleware.loki.common.ITopic;
import fr.alma.middleware.loki.common.Message;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;

public class Client extends UnicastRemoteObject implements IClient,ActionListener,Serializable {
	
	private MainWindow display;
	private HashMap<String,Color> usersColor;
	private String currentTopic;
	private HashMap<String,ITopic> subscribedTopics;
	
	public Client(MainWindow display) throws RemoteException {
		super();
		this.display = display;
		this.usersColor = new HashMap<String,Color>();
		this.subscribedTopics = new HashMap<String,ITopic>();
	}
	
	public void newMessage(Message message) throws RemoteException {
		System.out.println("[Client] " + message.getAuthor() + " : " + message.getMessage());

		if(!this.usersColor.containsKey(message.getAuthor())) {
			Random rand = new Random();
			Color c = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
			this.usersColor.put(message.getAuthor(),c);
		}

		this.display.appendToHistory(
			"\n" + message.getAuthor() + " : " + message.getMessage(),
			this.usersColor.get(message.getAuthor())
		);
	}
	
	public void addSubscribedTopic(String topicName, ITopic topic) {
		this.subscribedTopics.put(topicName, topic);
		if(this.currentTopic == null) {
			this.setCurrentTopic(topicName);
		}
	}
	
	public void setCurrentTopic(String topicName) {
		ITopic topic = this.subscribedTopics.get(topicName);
		if(topic != null) {
			this.currentTopic = topicName;
			this.display.setCurrentTopicName(topicName);
		}
	}
	
	public String getCurrentTopic() {
		return this.currentTopic;
	}
	
	public HashMap<String,ITopic> getSubscribedTopics() {
		return this.subscribedTopics;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(this.display.getNickname().length() != 0 && this.display.getMessage().length() != 0) {
			String nickname = this.display.getNickname();
			String message = this.display.getMessage();
			this.display.setMessage("");
			try {
				ITopic topic = this.subscribedTopics.get(this.currentTopic);
				topic.broadcast(new Message(nickname,topic,message));
			} catch(RemoteException ex) {
				System.err.println("Can't send message ["+nickname+","+message+"]");
				ex.printStackTrace();
			}
		}
	}
}
