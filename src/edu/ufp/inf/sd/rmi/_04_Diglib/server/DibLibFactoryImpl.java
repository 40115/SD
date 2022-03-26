package edu.ufp.inf.sd.rmi._04_Diglib.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class DibLibFactoryImpl extends UnicastRemoteObject implements DibLibFactoryRI{
    DBMockup db=new DBMockup();
    HashMap<String,DibLibSessionImpl> HAsh=new HashMap<String,DibLibSessionImpl>();
    public DibLibFactoryImpl() throws RemoteException {

        super();
    }


    @Override
    public void Register(String Username, String Password) {
        db.register(Username,Password);
    }


    @Override
    public DibLibSessionRI Login(String Username, String Password) throws RemoteException {


       if (db.exists(Username,Password)){
           HAsh.put(Username,new DibLibSessionImpl(this,new User(Username,Password)));
           return new DibLibSessionImpl(this,new User(Username,Password));
       }


        return null;
    }
}
