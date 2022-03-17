package edu.ufp.inf.sd.rmi._04_Diglib.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DibLibSessionImpl extends UnicastRemoteObject implements DibLibSessionRI{
     DBMockup h;
    public DibLibSessionImpl(DibLibFactoryImpl d) throws RemoteException {
        super();
        this.h=d.db;

    }


    @Override
    public Book[] Search(String Author, String Title) {
       return h.db.select(Title,Author);

    }

    @Override
    public void Logout() {

    }
}
