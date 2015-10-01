package fr.alma.middleware.loki.common;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Message extends UnicastRemoteObject implements Serializable {
	private String author;
	private String message;
	
	public Message(String author, String message) throws RemoteException {
		super();
		this.author = author;
		this.message = message;
	}
	
	public Message() throws RemoteException {
		this("","");
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
