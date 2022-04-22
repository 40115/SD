package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface FroggerGameRI extends Remote {
    public boolean check_Util(String User) throws RemoteException;
    public void update_the_game(GameState j) throws RemoteException;
    public HashMap<Util, GameState> getUtils() throws RemoteException;
    public String getDific() throws RemoteException;
    public void ready_the_game(Util k) throws RemoteException;
    public int getN() throws RemoteException;
    public boolean isRun() throws RemoteException;
    public String Difficulty(int difficulty) throws RemoteException;
    public void leve_the_game(GameSessionRI j) throws RemoteException;
}
