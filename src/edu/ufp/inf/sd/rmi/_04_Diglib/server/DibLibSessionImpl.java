package edu.ufp.inf.sd.rmi._04_Diglib.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DibLibSessionImpl extends UnicastRemoteObject implements DibLibSessionRI{

    DibLibFactoryImpl D;
    User j;
    public DibLibSessionImpl(DibLibFactoryImpl d,User j) throws RemoteException {
        super();
        this.D=d;
        this.j=j;
    }


    @Override
    public Book[] Search(String Author, String Title) {

        return this.D.db.select(Title,Author);

    }

    @Override
    public void Logout() {
      this.D.HAsh.remove(this.j.getUname());
    }
}
