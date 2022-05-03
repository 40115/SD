package edu.ufp.inf.sd.rmi.Project.server;

import edu.ufp.inf.sd.rmi.Project.client.ProjectClientRI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProjectMainRI extends Remote {
String Connect() throws RemoteException;
boolean Register(String Email, String Password, ProjectClientRI projectClientRI) throws RemoteException;
    GameSessionRI Login(String Email, String Password,ProjectClientRI projectClientRI) throws RemoteException;

}
