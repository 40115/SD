package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


import java.util.HashMap;
import java.util.logging.Logger;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI{
   private  Util Util;
   private  ProjectMainImpl PM;
   private String Token;
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
    public String Connect() throws RemoteException {
        if (this.PM.Valid(this.Token,this.Util)) {
            System.out.println(this.Util.getEmail());
            return "\nGame Froogger...\n" + "Select An option:\n" + "1-ListGames\n" + "2-Join Game\n" +"3-Create Game\n" + "4-Log Out\n";
        }
        return  "\nInvalid";
    }
    public String List_Games()throws RemoteException{
        StringBuilder j= new StringBuilder("List of Games:\n");
        for (int i = 0; i <this.PM.Game.size() ; i++) {
            j.append(i).append("- ");
            for (Util k :this.PM.Game.get(i).getUtils().keySet()) {
           j.append(k.getEmail()).append(" ");

            }
            j.append("\n ");
        }
return j.toString();

    }
    public FroggerGameRI Create_Game() throws RemoteException{
if (this.Check_Games()){
 return null;
}else{
    HashMap<Util,GameState> j=new HashMap<>();
    GameState m=new GameState();
    m.setMAster(true);
    m.setRefe(0);
    j.put(this.Util,m);
    FroggerGameRI l=new FroggerGameImpl(j,j.size(),getPM());
    m.setC(l);
    this.PM.Game.add(l);
    return l;
}
    }

    public FroggerGameRI join_Game(Integer I) throws RemoteException {
        if (this.Check_Games()||this.PM.Game.get(I)==null){
          return null;

        }else {
            GameState m=new GameState();
            m.setRefe( this.PM.Game.get(I).getUtils().size());
            m.setC(this.PM.Game.get(I));

            this.PM.Game.get(I).getUtils().put(this.Util,m);
            return this.PM.Game.get(I);
        }

    }

    public boolean Check_Games()throws RemoteException{
        for (int i = 0; i <this.PM.Game.size() ; i++) {
            FroggerGameRI k=this.PM.Game.get(i);
           if (k.getUtils().containsKey(this.Util)){
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
