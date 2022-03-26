package edu.ufp.inf.sd.rmi._05_Observer.server;

import edu.ufp.inf.sd.rmi._05_Observer.client.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SubjectRI extends Remote {
    public void setState(State s) throws RemoteException;
    public State getState()throws RemoteException;
    public void attach(ObserverRI obsRI) throws RemoteException;
    public void deattach(ObserverRI obsRI) throws RemoteException;
}
