package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DB2 {
    HashMap<String,String>Database=new HashMap();
    HashMap<String,String>Database_SERVERS=new HashMap();
    ArrayList<FroggerGame2> Games=new ArrayList<>();
    int id_Tot=0;
    public DB2() {
   
    }
    public boolean Check_Util(String Username) throws RemoteException {
        for (String s : Database.keySet()) {
            if (Objects.equals(s, Username)) {
                return true;
            }

        }
        return false;
    }
    public boolean Check_Server(String Username) throws RemoteException {
        for (String s : Database_SERVERS.keySet()) {
            if (Objects.equals(s, Username)) {
                return true;
            }

        }
        return false;
    }
    public boolean Check_Games(int id){
        if (id>Games.size())return false;
return !Games.get(id).isrunning;
    }
    public boolean Check_Games_Players(String Name){
        for (FroggerGame2 game : Games) {
            for (int j = 0; j < game.gameState2s.size(); j++) {
                if (Objects.equals(game.gameState2s.get(j).Name, Name)) return true;
            }
        }
        return false;
    }
    public boolean Insert_Util(String Username,String pass) throws RemoteException {
   if (!Check_Util(Username)) {
       Database.put(Username,pass);
       return true;
   }
return false;
    }

    public boolean Insert_Server(String Username,String pass) throws RemoteException {
        if (!Check_Server(Username)) {
            Database_SERVERS.put(Username,pass);
            return true;
        }
        return false;
    }
    public String _Get_Server_Route(String Username) throws RemoteException {
        for (String s:Database_SERVERS.keySet()) {
            if (Objects.equals(Username, s)){
                return  Database_SERVERS.get(s);
            }
        }
        return null;
    }
    public ArrayList<FroggerGame2> getGames() {
        return Games;
    }

    public void setGames(ArrayList<FroggerGame2> games) {
        Games = games;
    }
}


