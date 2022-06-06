package edu.ufp.inf.sd.rmi.Project.client;

import edu.ufp.inf.sd.rmi.Project.server.FroggerGameRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {

   int Refference=0;
   State state;
   FroggerGameRI froggerGameRI;


    boolean Ready =false;
boolean MAster=false;
    public  boolean started=false;
    public  boolean isfinnsihed=false;
    public  int points=0;

    protected ObserverImpl(int ref,State state,FroggerGameRI froggerGameRI) throws RemoteException {
        super();
        this.Refference=ref;
        this.state=state;
        this.froggerGameRI=froggerGameRI;
    }

    @Override
    public void update(State state) throws  RemoteException {
        this.state=state;
    }

    public boolean isReady()throws  RemoteException {
        return Ready;
    }
    public void setReady3(boolean ready)throws  RemoteException {
        Ready = ready;

    }
    public void setReady(boolean ready)throws  RemoteException {
        Ready = ready;
        froggerGameRI.Ready_UP_CHECK();
    }
    public void setReady2(boolean ready)throws  RemoteException {
        Ready = ready;
        froggerGameRI.Ready_UP_CHECK2();
    }

    public int getRefference() throws RemoteException {
        return Refference;
    }

    public void setRefference(int refference) throws RemoteException{
        Refference = refference;
    }

    public State getState() throws RemoteException{
        return state;
    }

    public void setState(State state)throws RemoteException {
        this.state = state;
    }

    public FroggerGameRI getFroggerGameRI()throws RemoteException {
        return froggerGameRI;
    }

    public void setFroggerGameRI(FroggerGameRI froggerGameRI)throws RemoteException {
        this.froggerGameRI = froggerGameRI;
    }

    public boolean isMAster() throws RemoteException {
        return MAster;
    }

    public void setMAster(boolean MAster)  throws RemoteException{
        this.MAster = MAster;
    }
    @Override
    public boolean isStarted() throws RemoteException{
        return started;
    }

    public void setStarted(boolean started) throws  RemoteException{
        this.started = started;
    }

    public boolean isIsfinnsihed() throws  RemoteException{
        return isfinnsihed;
    }

    public void setIsfinnsihed(boolean isfinnsihed) throws  RemoteException{
        this.isfinnsihed = isfinnsihed;
    }

    public int getPoints()throws  RemoteException {
        return points;
    }

    public void setPoints(int points)throws  RemoteException {
        this.points = points;
    }
}
