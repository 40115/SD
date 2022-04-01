package edu.ufp.inf.sd.rmi.Project.server;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI{
   private final Util Util;
   private final ProjectMainImpl PM;

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
    public void List_Games(){
        StringBuilder j= new StringBuilder("List of Games:\n");
        for (int i = 0; i <this.PM.Game.size() ; i++) {

        }


    }
    public FroggerGame Create_Game(){



return null;
    }
    public boolean Check_Games(){
        for (int i = 0; i <this.PM.Game.size() ; i++) {
            FroggerGame k=this.PM.Game.get(i);
           if (k.check_Util(this.Util.getEmail())){
               return true;
           }

        }


     return false;

    }


}
