package fr.alma.middleware.loki.client;

import fr.alma.middleware.loki.client.gui.MainWindow;
import fr.alma.middleware.loki.client.gui.TopicWindow;
import fr.alma.middleware.loki.common.IClient;
import fr.alma.middleware.loki.common.IServer;
import fr.alma.middleware.loki.common.ITopic;
import fr.alma.middleware.loki.common.Message;
import fr.alma.middleware.loki.common.TopicAlreadyExistedException;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Client extends UnicastRemoteObject implements IClient,ActionListener,Serializable {
	
	private IServer server;
	private MainWindow display;
	private HashMap<String,Color> usersColor;
	private String currentTopic;
	private HashMap<String,ITopic> subscribedTopics;
	private List<ClientTopic> childs;
	
	public Client(IServer server, MainWindow display) throws RemoteException {
		super();
		this.server = server;
		this.display = display;
		this.display.setClient(this);
		this.usersColor = new HashMap<String,Color>();
		this.subscribedTopics = new HashMap<String,ITopic>();
		this.childs = new LinkedList<ClientTopic>();
	}
		
	public Color getUserColor(String nickname) {
		if(!this.usersColor.containsKey(nickname)) {
			Random rand = new Random();
			Color c = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
			this.usersColor.put(nickname,c);
		}
		return this.usersColor.get(nickname);
	}
	
	public void addSubscribedTopic(String topicName, ITopic topic) {
		this.subscribedTopics.put(topicName, topic);
	}
	
	public void removeSubscribedTopic(String topicName) {
		this.subscribedTopics.remove(topicName);
	}
		
	public HashMap<String,ITopic> getSubscribedTopics() {
		return this.subscribedTopics;
	}
	
	public String getNickname() throws RemoteException {
		return this.display.getNickname();
	}
	
	public void setNickname(String nickname) throws RemoteException {
		this.display.setNickname(nickname);
	}
	
	public void topicCreated(ITopic topic) throws RemoteException {
		this.display.addTopic(topic.getName());
	}
	
	public void topicRemoved(ITopic topic) throws RemoteException {
		this.display.removeTopic(topic.getName());
	}
	
	public void actionPerformed(ActionEvent e) {
		if(this.display.getNewTopicName().length() != 0) {
			String topicName = this.display.getNewTopicName();
			this.display.setNewTopicName("");
			try {
				this.server.createTopic(topicName);
			} catch(TopicAlreadyExistedException ex) {
				System.err.println("Topic "+topicName+" already exists");
			} catch(RemoteException ex) {
				System.err.println("Can't create topic ["+topicName+"]");
				ex.printStackTrace();
			}
		}
	}
	
	public void openTopic(String topicName) {
		try {
			ITopic topic = this.server.getTopic(topicName);
			
			TopicWindow topicWindow = new TopicWindow(this.display.getTitle());
			topicWindow.setCurrentTopicName(topic.getName());
			ClientTopic clientTopic = new ClientTopic(this,topicWindow);
			this.childs.add(clientTopic);
			
			topic.subscribe(clientTopic);
			clientTopic.setCurrentTopic(topic);
		} catch(RemoteException ex) {
			System.err.println("Can't open new topic");
		}
	}
	
	public void unregister() {
		for(ClientTopic child : childs) {
			child.close();
		}
		
		try {
			this.server.unregisterClient(this);
		} catch(RemoteException ex) {
			System.err.println("Can't unregister client");
			ex.printStackTrace();
		}
	}
}
