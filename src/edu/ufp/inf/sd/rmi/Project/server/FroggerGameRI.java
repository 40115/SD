package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface FroggerGameRI extends Remote {

    public void update_the_game(GameStateRI j,int type) throws RemoteException ;
    public void update_the_game2(UtilRI g,int i) throws RemoteException;
    public HashMap<UtilRI, GameStateRI> getUtils() throws RemoteException;
    public String getDific() throws RemoteException;
    public void ready_the_game(UtilRI h) throws RemoteException;
    public int getN() throws RemoteException;
    public boolean isRun() throws RemoteException;
    public String Difficulty(int difficulty) throws RemoteException;
    public void leve_the_game(GameSessionRI j) throws RemoteException;
}
