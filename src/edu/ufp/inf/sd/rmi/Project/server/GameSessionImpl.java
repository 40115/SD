package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


import java.util.ArrayList;
import java.util.logging.Logger;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI{
   private final Util Util;
   private final ProjectMainImpl PM;
   private final String Token;
    public GameSessionImpl (ProjectMainImpl pm,Util util,String token) throws RemoteException {
        super();
        this.PM=pm;
        this.Util=util;
        this.Token=token;
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
        if (this.PM.Valid(this.Token,this.Util)) {
            return "\nGame Froogger...\n" + "Select An option:\n" + "1-ListGames\n" + "2-\n" + "3-Leave\n";
        }
        return  "\nInvalid";
    }
    public String List_Games()throws RemoteException{
        StringBuilder j= new StringBuilder("List of Games:\n");
        for (int i = 0; i <this.PM.Game.size() ; i++) {
            j.append(i).append("- ");
            for (int k = 0; k <this.PM.Game.get(i).Utils.size() ; k++) {
       Util l=this.PM.Game.get(k).Utils.get(k);

                j.append(l.getEmail()).append(" | ");

            }
            j.append("\n ");
        }
return j.toString();

    }
    public FroggerGame Create_Game(String D) throws RemoteException{
if (this.Check_Games()){
 return null;
}else{
    ArrayList<Util> k=new ArrayList<>();
    k.add(this.Util);
    return new FroggerGame(k,D);
}
    }


    public boolean Check_Games()throws RemoteException{
        for (int i = 0; i <this.PM.Game.size() ; i++) {
            FroggerGame k=this.PM.Game.get(i);
           if (k.check_Util(this.Util.getEmail())){
               return true;
           }
        }
     return false;
    }


    public Util getUtil()throws RemoteException {
        return Util;
    }

    public ProjectMainImpl getPM() throws RemoteException{
        return PM;
    }

    public String getToken()throws RemoteException {
        return Token;
    }
}
