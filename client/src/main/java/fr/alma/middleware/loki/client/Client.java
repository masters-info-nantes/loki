package fr.alma.middleware.loki.client;

import fr.alma.middleware.loki.client.gui.*;
import fr.alma.middleware.loki.common.*;

import java.awt.Color;
import java.io.Serializable;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;

public class Client extends UnicastRemoteObject implements IClient,Serializable {
	
	private MainWindow display;
	private HashMap<String,Color> usersColor;
	
	public Client(MainWindow display) throws RemoteException {
		super();
		this.display = display;
		this.usersColor = new HashMap<String,Color>();
	}
	
	public void newMessage(Message message) throws RemoteException {
		System.out.println(message.getAuthor()+" : "+message.getMessage());
		if(!this.usersColor.containsKey(message.getAuthor())) {
			Random rand = new Random();
			Color c = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
			this.usersColor.put(message.getAuthor(),c);
		}
		this.display.appendToHistory(message.getAuthor()+" : "+message.getMessage(),this.usersColor.get(message.getAuthor()));
	}
}