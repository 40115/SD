package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProjectMainRI extends Remote {
String Connect() throws RemoteException;
boolean Register(String Email, String Password) throws RemoteException;
    GameSessionRI Login(String Email, String Password) throws RemoteException;
}
