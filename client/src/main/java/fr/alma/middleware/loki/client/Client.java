package fr.alma.middleware.loki.client;

import fr.alma.middleware.loki.common.*;

import java.io.Serializable;
import java.rmi.*;

public class Client implements IClient,Serializable {
	
	public Client() {
	}
	
	public void newMessage(Message message) throws RemoteException {
		System.out.println(message.getAuthor()+" : "+message.getMessage());
	}
}
