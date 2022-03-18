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
        return "Hello, Welcome to City 17 you have chosen or been Chosen to...\n" +"Select An option:" + "1-Register" + "2-Login In" + "3-Leave";
    }
    public boolean Register()  throws RemoteException {


        return false;
    }

}
