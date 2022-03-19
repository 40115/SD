package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ProjectMainImpl extends UnicastRemoteObject implements ProjectMainRI {
   DB Database=new DB(new ArrayList<>());
    public ProjectMainImpl() throws RemoteException {

        super();
    }


    @Override
    public String Connect()  throws RemoteException {
        return "\nHello, Welcome to City 17 you have chosen or been Chosen to...\n" +"Select An option:\n" + "1-Register\n" + "2-Login In\n" + "3-Leave\n";
    }
    public boolean Register()  throws RemoteException {


        return false;
    }

}
