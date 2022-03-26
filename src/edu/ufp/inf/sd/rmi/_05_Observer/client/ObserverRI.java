package edu.ufp.inf.sd.rmi._05_Observer.client;

import edu.ufp.inf.sd.rmi._05_Observer.server.State;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObserverRI extends Remote {
public void update() throws RemoteException;

}
