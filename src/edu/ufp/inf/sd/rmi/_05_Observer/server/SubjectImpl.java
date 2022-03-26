package edu.ufp.inf.sd.rmi._05_Observer.server;

import edu.ufp.inf.sd.rmi._05_Observer.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class SubjectImpl extends UnicastRemoteObject implements SubjectRI{
   private State subjectState;
   private ArrayList<ObserverRI> observers= new ArrayList<ObserverRI>();

    public SubjectImpl(State subjectState) throws RemoteException {
        super();
        this.subjectState = subjectState;
    }

    public SubjectImpl() throws RemoteException {
        super();

    }

    @Override
    public void setState(State s) throws RemoteException {
        this.subjectState=s;
        this.notifyAllObservers();
    }

    @Override
    public State getState() {
        return this.subjectState;
    }

    @Override
    public void attach(ObserverRI obsRI) {

       if( this.observers.contains(obsRI)) {
           this.observers.add(obsRI);
       }
    }

    @Override
    public void deattach(ObserverRI obsRI) {
    this.observers.remove(obsRI);
    }
    public void notifyAllObservers() throws RemoteException {
        for (int i = 0; i <this.observers.size() ; i++) {
            observers.get(i).update();
        }

    }
}
