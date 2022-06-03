package edu.ufp.inf.sd.rmi.Project.server;



import edu.ufp.inf.sd.rmi.Project.client.ObserverRI;
import edu.ufp.inf.sd.rmi.Project.client.State;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface FroggerGameRI extends Remote {
    public void NotifyAll()throws RemoteException;
    public FroggerGameRI attach(ObserverRI h,UtilRI d) throws RemoteException;
    public  boolean dettach(ObserverRI h,UtilRI d)throws RemoteException;
    public void setState(State state,int stat)throws RemoteException;
    public State get_State() throws RemoteException;
    public ArrayList<UtilRI> getUtil()throws RemoteException;

    void Ready_UP_CHECK() throws RemoteException;

    void Ready_UP_CHECK2()throws RemoteException;

    void finnished(ObserverRI y)throws RemoteException;
}
