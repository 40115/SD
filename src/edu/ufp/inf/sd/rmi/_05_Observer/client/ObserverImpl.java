package edu.ufp.inf.sd.rmi._05_Observer.client;

import edu.ufp.inf.sd.rmi._05_Observer.server.State;
import edu.ufp.inf.sd.rmi._05_Observer.server.SubjectRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI{
private String id;
   private State LastObserverState;
  SubjectRI subjectRI;
   private ObserverGuiClient chatFrame;
public ObserverImpl(String id,ObserverGuiClient f,SubjectRI subjectRI) throws RemoteException {
    super();
    this.id=id;
    this.chatFrame=f;
    this.subjectRI=subjectRI;
    this.subjectRI.attach(this);
}



    public State getLastObserverState() {
    return this.LastObserverState;

    }


    @Override
    public void update() throws RemoteException {
    this.LastObserverState=this.subjectRI.getState();
this.chatFrame.updateTextArea();
    }
}
