package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameSessionRI extends Remote {
void LogOut() throws RemoteException;

   String Connect() throws RemoteException;
    String List_Games() throws RemoteException;
    FroggerGameRI Create_Game() throws RemoteException;
    UtilRI getUtil()throws RemoteException;
    ProjectMainImpl getPM()throws RemoteException;
    String getToken()throws RemoteException;
    public FroggerGameRI join_Game(Integer I) throws RemoteException;


}
