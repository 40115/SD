package edu.ufp.inf.sd.rmi._06_Visitor.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VisitorFoldersOperationCreateFile implements VisitorFoldersOperationsI {
    private    String Filetocreate;
    public VisitorFoldersOperationCreateFile(String filetocreate) throws RemoteException {
        super();
        this.Filetocreate=filetocreate;

    }

    @Override
    public Object visitConcreteElementStateBooks(ElementFolderRI element) throws RemoteException {
      ConcreteElementFolderbooksImpl j=  (ConcreteElementFolderbooksImpl) element;
        SingletonFolderOperationsBooks k= j.getStateBooksFolder();
        return  k.createFile(this.Filetocreate);

    }

    @Override
    public Object visitConcreteElementStateMagazines(ElementFolderRI element) throws RemoteException{
        return null;
    }

    public String getFiletocreate() {
        return Filetocreate;
    }

    public void setFiletocreate(String filetocreate) {
        Filetocreate = filetocreate;
    }
}
