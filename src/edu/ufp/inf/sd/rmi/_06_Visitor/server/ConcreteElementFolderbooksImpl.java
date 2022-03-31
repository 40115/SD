package edu.ufp.inf.sd.rmi._06_Visitor.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConcreteElementFolderbooksImpl extends UnicastRemoteObject implements ElementFolderRI{
private SingletonFolderOperationsBooks StateBooksFolder;

    public ConcreteElementFolderbooksImpl(String books) throws RemoteException {
        super();
        this.StateBooksFolder=SingletonFolderOperationsBooks.createSingletonFolderOperationsBooks(books);
    }

    public SingletonFolderOperationsBooks getStateBooksFolder() throws RemoteException{
        return StateBooksFolder;
    }

    @Override
    public Object acceptVisitor(VisitorFoldersOperationsI visitor) throws RemoteException {
        return visitor.visitConcreteElementStateBooks(this);
    }
}
