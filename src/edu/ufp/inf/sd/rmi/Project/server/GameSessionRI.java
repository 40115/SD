package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameSessionRI extends Remote {
    public void LogOut() throws RemoteException;
}
