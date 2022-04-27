package edu.ufp.inf.sd.rmi.Project.server;

import edu.ufp.inf.sd.rmi.Project.client.ProjectClientRI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UtilRI extends Remote {
    public String getEmail() throws RemoteException;
    public String getPassword() throws RemoteException;
    public ProjectClientRI getProjectClientRI() throws RemoteException;
}
