package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameSessionRI extends Remote {
    void LogOut() throws RemoteException;
    void ConnectSessionToServer() throws RemoteException;
    String Connect() throws RemoteException;
}
