package fr.alma.middleware.interfaces;

import java.rmi.*;

public interface IClientDisplay extends Remote {
	public void display(String message) throws RemoteException;
}
