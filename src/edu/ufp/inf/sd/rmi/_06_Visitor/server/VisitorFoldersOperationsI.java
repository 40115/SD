package edu.ufp.inf.sd.rmi._06_Visitor.server;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VisitorFoldersOperationsI extends Serializable {
     public Object visitConcreteElementStateBooks(ElementFolderRI element) throws RemoteException;
    public Object visitConcreteElementStateMagazines(ElementFolderRI element) throws RemoteException;

}
