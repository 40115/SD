package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


import java.util.HashMap;
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
        for(int i =0; i<this.PM.Database.Database.size(); i++){
            Logger.getLogger("Lista: "+ PM.Database.Database.get(i).getEmail());
        }
    }

    @Override
    public String Connect() throws RemoteException {
        if (this.PM.Valid(this.Token,this.Util)) {
            return "\nGame Froogger...\n" + "Select An option:\n" + "1-ListGames\n" + "2-Join Game\n" +"3-Create Game\n" + "4-Log Out\n";
        }
        return  "\nInvalid";
    }
    public String List_Games()throws RemoteException{
        StringBuilder j= new StringBuilder("List of Games:\n");
        for (int i = 0; i <this.PM.Game.size() ; i++) {
            j.append(i).append("- ");
            for (Util k :this.PM.Game.get(i).Utils.keySet()) {
           j.append(k.getEmail()).append(" ");

            }
            j.append("\n ");
        }
return j.toString();

    }
    public FroggerGame Create_Game(String D) throws RemoteException{
if (this.Check_Games()){
 return null;
}else{
    HashMap<Util,GameState> j=new HashMap<>();
    j.put(this.Util,new GameState());
    FroggerGame l=new FroggerGame(j,D,j.size(),getPM());
    this.PM.Game.add(l);
    return l;
}
    }
    public FroggerGame join_Game(Integer I) throws RemoteException {
        if (this.Check_Games()){
          return null;

        }else {
            this.PM.Game.get(I).Utils.put(this.Util,new GameState());
            return this.PM.Game.get(I);

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
