package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Objects;

public class DB {
HashMap<String,String>Database=new HashMap<>();

    public DB() {
        Database.put("Ruben","Pinto");
        Database.put("John","Cena");
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
            return true;
        }

        }




    }


