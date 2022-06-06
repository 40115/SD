package edu.ufp.inf.sd.rmi.Project.client;

import edu.ufp.inf.sd.rmi.Project.server.FroggerGameRI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObserverRI extends Remote {
    public boolean isMAster() throws RemoteException;
    public void setMAster(boolean MAster)  throws RemoteException;
    public void update(State state)throws RemoteException;
    public void setReady(boolean ready)throws  RemoteException;
    public void setReady2(boolean ready)throws  RemoteException;
    public boolean isIsfinnsihed() throws  RemoteException;
    public void setIsfinnsihed(boolean isfinnsihed) throws  RemoteException;
    public void setReady3(boolean ready)throws  RemoteException;
    public boolean isReady()throws  RemoteException;

    public int getRefference() throws RemoteException ;

    public void setRefference(int refference)  throws RemoteException;

    public State getState() throws RemoteException;

    public void setState(State state)throws RemoteException;
    public FroggerGameRI getFroggerGameRI()throws RemoteException ;

    public void setFroggerGameRI(FroggerGameRI froggerGameRI)throws RemoteException ;

    boolean isStarted()throws RemoteException;
    void setStarted(boolean started) throws  RemoteException;
    public void setPoints(int points)throws  RemoteException;
    public int getPoints()throws  RemoteException;
    }
