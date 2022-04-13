package edu.ufp.inf.sd.rmi.Project.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProjectClientRI extends Remote {
public void start_Game()throws RemoteException;
}
