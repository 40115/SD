package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI{
   private final Util Util;
   private final ProjectMainImpl PM;
   private enum Difficulty{
       LOW,
       MEDIUM,
       HIGH;
   };
    public GameSessionImpl (ProjectMainImpl pm,Util util) throws RemoteException {
        super();
        this.PM=pm;
        this.Util=util;
    }

    @Override
    public void LogOut() throws RemoteException {
        this.PM.users.remove(Util);
    }

    @Override
    public void ConnectSessionToServer()throws RemoteException{
        for(int i =0; i<PM.Database.Database.size(); i++){
            Logger.getLogger("Lista: "+ PM.Database.Database.get(i));
        }
    }

    @Override
    public String Connect() throws RemoteException {
        return "\nGame Froogger...\n" +"Select An option:\n" + "1-ListGames\n" + "2-\n" + "3-Leave\n";

    }
    public Difficulty Difficulty(int difficulty) throws RemoteException{
        Difficulty var;
        switch (difficulty){
            case 1:
                var=Difficulty.LOW;
                return var/*"Easy difficult has been selected\n"*/;
            case 2:
                var=Difficulty.MEDIUM;
                return var /*"Normal difficult has been selected\n"*/;
            case 3:
                var=Difficulty.HIGH;
                return var;
            default:
                var=Difficulty.MEDIUM;
                return var;
        }
    }
}
