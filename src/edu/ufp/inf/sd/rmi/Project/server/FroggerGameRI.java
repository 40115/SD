package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface FroggerGameRI extends Remote {

    void update_the_game(GameStateRI j,int type,int nroad) throws RemoteException;
    void update_the_game2(UtilRI g,int i) throws RemoteException;
    void update_the_game2River(UtilRI g,int i) throws RemoteException;
    void update_the_Frogger(Vect d, int h, UtilRI g) throws RemoteException;
    void  update_the_Frogger2(UtilRI g,int h) throws RemoteException;
    void update_the_gameloggs(GameStateRI j,int type,int nriver) throws RemoteException;
    HashMap<UtilRI, GameStateRI> getUtils() throws RemoteException;
    String getDific() throws RemoteException;
    void ready_the_game(UtilRI h) throws RemoteException;
     boolean check_Ready2()throws RemoteException;
    void Sync_Timer(int j, UtilRI g)throws RemoteException;

    int getN() throws RemoteException;
    boolean isRun() throws RemoteException;
    String Difficulty(int difficulty) throws RemoteException;
    void leve_the_game(GameSessionRI j) throws RemoteException;
    GameStateRI Get_The_Game_State(UtilRI h) throws RemoteException;
    void Froogdie(UtilRI g,int i,int status) throws RemoteException;
    void I_HAVE_ENDED(UtilRI g) throws RemoteException;
}
