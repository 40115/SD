package edu.ufp.inf.sd.rmi.Project.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ProjectClientImpl  extends UnicastRemoteObject implements ProjectClientRI {

    public ProjectClientImpl() throws RemoteException {

        super();
    }


    @Override
    public void start_Game() {

    }
}
