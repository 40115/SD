package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ProjectMainImplServer extends UnicastRemoteObject implements ProjectMainRIServer {
    public ProjectMainImplServer() throws RemoteException {

        super();
    }


    @Override
    public String Connect() {
        return "Hello, Welcome to City 17 you have chosen or been Chosen to...\n" +
                "Select An option:" +
                "1-Register" +
                "2-Login In" +
                "3-Leave";
    }
}
