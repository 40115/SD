package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DB {
HashMap<String,String>Database=new HashMap<>();
ArrayList<Integer> points=new ArrayList<>();
    public DB() {
        Database.put("Ruben","Pinto");
        Database.put("John","Cena");
        points.add(0);
        points.add(0);
    }
    public boolean Check_Util(String Username,String Password) throws RemoteException {
        return Objects.equals(this.Database.get(Username), Password);
    }
    public boolean Check_Util(String Username) throws RemoteException {
        return this.Database.get(Username) != null;
    }
    public boolean Insert_Util(String Username, String Password) throws RemoteException {
        if (Check_Util(Username)){
            return false;
        }else {
            Database.put(Username,Password);
            points.add(0);
            return true;
        }

        }

    public void Add_Points(String Username,Integer point) throws RemoteException{
  int i=0;

        for (String h:Database.keySet() ) {
          if (Objects.equals(h, Username)){
              points.set(i,points.get(i)+point);
              return;
          }
            i++;
        }

    }


    }


