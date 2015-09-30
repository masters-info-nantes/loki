package fr.alma.middleware.loki.common;

import java.io.Serializable;

public class Message implements Serializable {
	private String author;
	private String message;
	
	public Message(String author, String message) {
		this.author = author;
		this.message = message;
	}
	
	public Message() {
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
