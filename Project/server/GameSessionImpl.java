package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI{
   private final Util Util;
        private final ProjectMainImpl PM;
    public GameSessionImpl (ProjectMainImpl pm,Util util) throws RemoteException {
        super();
        this.PM=pm;
        this.Util=util;
    }
    public void ConnectSessionToServer()throws RemoteException{
        Logger.getLogger("Lista: "+ PM.Database);
    }

    public void LogOut()throws RemoteException{
        this.PM.users.remove(Util);
    }

}
