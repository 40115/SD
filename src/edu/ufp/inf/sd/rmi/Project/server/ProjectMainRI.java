package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface ProjectMainRI extends Remote {
String Connect() throws RemoteException;
boolean Register(String Username, String Password) throws RemoteException;
    GameSessionRI Login(String Username, String Password) throws RemoteException;
}
