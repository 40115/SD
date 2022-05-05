package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


import java.util.HashMap;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI{
   private final UtilRI Util;
   private final ProjectMainImpl PM;
   private String Token;
    public GameSessionImpl (ProjectMainImpl pm,UtilRI util,String token) throws RemoteException {
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
            for (UtilRI k :this.PM.Game.get(i).getUtils().keySet()) {
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
    HashMap<UtilRI,GameStateRI> j=new HashMap<>();
    GameStateRI m=new GameState();
    m.setMAster(true);
    System.out.println(this.Util);
    m.setUtil(this.Util);
    m.setRefe(0);
    j.put(this.Util,m);
    FroggerGameRI l=new FroggerGameImpl(j,j.size(),getPM());
    m.setC(l);
    this.PM.Game.add(l);
    return l;
}
    }

    public FroggerGameRI join_Game(Integer I) throws RemoteException {
        if (this.PM.Game.size()<I ||I<0){
          return null;

        }else {
            FroggerGameRI  x=this.Check_Games2();
            if (x==null) {
                GameStateRI m = new GameState();
                m.setRefe(this.PM.Game.get(I).getUtils().size());
                m.setC(this.PM.Game.get(I));
                System.out.println(this.Util);
                m.setUtil(this.Util);
                this.PM.Game.get(I).getUtils().put(this.Util, m);
                return this.PM.Game.get(I);
            }else {
                return x;

            }
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

    public FroggerGameRI Check_Games2()throws RemoteException{
        for (int i = 0; i <this.PM.Game.size() ; i++) {
            FroggerGameRI k=this.PM.Game.get(i);
            if (k.getUtils().containsKey(this.Util)){
                return this.PM.Game.get(i);
            }
        }
        return null;
    }
    public UtilRI getUtil()throws RemoteException {
        return Util;
    }

    public ProjectMainImpl getPM() throws RemoteException{
        return PM;
    }

    public String getToken()throws RemoteException {
        return Token;
    }

}
