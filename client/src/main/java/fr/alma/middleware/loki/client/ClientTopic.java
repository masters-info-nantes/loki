package fr.alma.middleware.loki.client;

import fr.alma.middleware.loki.client.gui.TopicWindow;
import fr.alma.middleware.loki.common.IClient;
import fr.alma.middleware.loki.common.IClientTopic;
import fr.alma.middleware.loki.common.ITopic;
import fr.alma.middleware.loki.common.Message;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ClientTopic extends UnicastRemoteObject implements IClientTopic,ActionListener,Serializable {
	
	private Client parent;
	private TopicWindow display;
	private ITopic topic;
	
	public ClientTopic(Client parent,TopicWindow display) throws RemoteException {
		super();
		this.parent = parent;
		this.display = display;
		this.display.listenSendButton(this);
	}
	
	public void newMessage(Message message) throws RemoteException {
		if(message.getAuthor().equalsIgnoreCase("nyan-cat")) {
			String str = message.getAuthor() + " : " + message.getMessage();
			List<Color> rainbow = ColorGenerator.rainbow(str.length());
			this.display.appendToHistory("\n");
			for(int i=0 ; i<str.length() ; i++) {
				this.display.appendToHistory(
					Character.toString(str.charAt(i)),
					rainbow.get(i)
				);
			}
		} else {
			this.display.appendToHistory(
				"\n" + message.getAuthor() + " : " + message.getMessage(),
				this.parent.getUserColor(message.getAuthor())
			);
		}
	}
		
	public void setTopic(ITopic topic) {
		this.topic = topic;
		
		try {
			for(Message msg : topic.history()){
				this.newMessage(msg);
			}
		} catch(RemoteException ex) {
			System.err.println("Can't load topic history");
		}
	}
	
	public ITopic getTopic() {
		return this.topic;
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			if(this.parent.getNickname().length() != 0 && this.display.getMessage().length() != 0) {
				String nickname = this.parent.getNickname();
				String message = this.display.getMessage();
				this.display.setMessage("");
				try {
					this.topic.broadcast(new Message(nickname,this.topic,message));
				} catch(RemoteException ex) {
					System.err.println("Can't send message ["+nickname+","+message+"]");
					ex.printStackTrace();
				}
			}
		} catch(RemoteException ex) {
			System.err.println("Can't access to nickname");
		}
	}
	
	public void close() {
		this.display.close();
		this.parent.closeTopic(this.topic);
	}
	
	public void bringToFront() {
		this.display.toFront();
	}
}
