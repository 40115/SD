package edu.ufp.inf.sd.rmi.Project.server;

import edu.ufp.inf.sd.rmi.Project.client.ProjectClientRI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UtilRI extends Remote {
     String getEmail() throws RemoteException;
     String getPassword() throws RemoteException;
    ProjectClientRI getProjectClientRI() throws RemoteException;
}
