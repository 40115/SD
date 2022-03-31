package edu.ufp.inf.sd.rmi._06_Visitor.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VisitorFoldersOperationDeleteFile implements VisitorFoldersOperationsI{
  private   String FiletoDelete;

    protected VisitorFoldersOperationDeleteFile(String filetoDelete) throws RemoteException {
        super();
        this.FiletoDelete=filetoDelete;

    }

    @Override
    public Object visitConcreteElementStateBooks(ElementFolderRI element) throws RemoteException {
        ConcreteElementFolderbooksImpl j=  (ConcreteElementFolderbooksImpl) element;
        SingletonFolderOperationsBooks k= j.getStateBooksFolder();
        return  k.deleteFile(this.getFiletoDelete());

    }

    @Override
    public Object visitConcreteElementStateMagazines(ElementFolderRI element) throws RemoteException{
        return null;
    }

    public String getFiletoDelete() {
        return FiletoDelete;
    }

    public void setFiletoDelete(String filetoDelete) {
        FiletoDelete = filetoDelete;
    }
}
