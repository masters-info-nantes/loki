package fr.alma.middleware.loki.common;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Message implements Serializable {
	
	private String author;
	private ITopic topic;
	private String message;
	
	public Message(String author, ITopic topic, String message) {
		this.author = author;
		this.topic = topic;
		this.message = message;
	}
	
	public Message() throws RemoteException {
		this("",null,"");
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public ITopic getTopic() {
		return this.topic;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setTopic(ITopic topic) {
		this.topic = topic;
	}
}
