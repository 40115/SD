package edu.ufp.inf.sd.rmi._04_Diglib.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DibLibFactoryRI extends Remote {
    public void Register(String Username,String Password);
    public DibLibSessionRI Login(String Username,String Password) throws RemoteException;

}
