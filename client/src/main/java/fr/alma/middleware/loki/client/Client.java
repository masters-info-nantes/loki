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
import javax.swing.JOptionPane;

public class Client extends UnicastRemoteObject implements IClient,ActionListener,Serializable {
	
	private IServer server;
	private MainWindow display;
	private HashMap<String,Color> usersColor;
	private String currentTopic;
	private HashMap<String,ITopic> subscribedTopics;
	private HashMap<ITopic,ClientTopic> childs;
	
	public Client(IServer server, MainWindow display) throws RemoteException {
		super();
		this.server = server;
		this.display = display;
		this.display.setClient(this);
		this.usersColor = new HashMap<String,Color>();
		this.subscribedTopics = new HashMap<String,ITopic>();
		this.childs = new HashMap<ITopic,ClientTopic>();
	}
		
	public Color getUserColor(String nickname) {
		if(!this.usersColor.containsKey(nickname)) {
			Color c = ColorGenerator.random();
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
		if(this.childs.containsKey(topic)) {
			this.subscribedTopics.remove(topic.getName());
			ClientTopic child = this.childs.remove(topic);
			child.close();
		}
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
			if(!this.childs.containsKey(topic)) {
				TopicWindow topicWindow = new TopicWindow(this.display.getTitle());
				topicWindow.setTopicName(topic.getName());
				ClientTopic clientTopic = new ClientTopic(this,topicWindow);
				topicWindow.setClientTopic(clientTopic);
				this.childs.put(topic,clientTopic);
				
				topic.subscribe(clientTopic);
				clientTopic.setTopic(topic);
			} else {
				this.childs.get(topic).bringToFront();
			}
		} catch(RemoteException ex) {
			System.err.println("Can't open new topic");
		}
	}
	
	public void closeTopic(ITopic topic) {
		this.childs.remove(topic);
	}
	
	public void deleteTopic(String topicName) {
		int response = JOptionPane.showConfirmDialog(
			this.display,
			"Delete topic \""+topicName+"\"?",
			"Deleting",
			JOptionPane.YES_NO_OPTION
		);
		if(response == JOptionPane.YES_OPTION) {
			try {
				ITopic topic = this.server.getTopic(topicName);
				if(this.childs.containsKey(topic)) {
					this.subscribedTopics.remove(topicName);
					ClientTopic child = this.childs.remove(topic);
					child.close();
				}
				
				this.server.removeTopic(topic);
			} catch(RemoteException ex) {
				System.err.println("Can't remove topic "+topicName);
			}
		}
	}
	
	public void unregister() {
		for(ClientTopic child : childs.values()) {
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
