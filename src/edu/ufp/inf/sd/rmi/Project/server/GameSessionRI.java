package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameSessionRI extends Remote {
    void LogOut() throws RemoteException;
    void ConnectSessionToServer() throws RemoteException;
    String Connect() throws RemoteException;
    public String List_Games() throws RemoteException;
    public FroggerGame Create_Game(String D) throws RemoteException;
    public Util getUtil()throws RemoteException;
    public ProjectMainImpl getPM()throws RemoteException;
    public String getToken()throws RemoteException;
}
