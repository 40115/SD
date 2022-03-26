package edu.ufp.inf.sd.rmi.Project.server;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class DB {
ArrayList<Util>Database=new ArrayList<>();

    public DB() {
        Database.add(new Util("Ruben","1234"));
        Database.add(new Util("Joana","2345"));

    }
    public Boolean Check_Util(String Username,String Password) throws RemoteException {
        return !Database.contains(new Util(Username, Password));
    }
    public boolean Insert_Util(String Username,String Password) throws RemoteException {
        if (Check_Util(Username,Password)){
            return false;
        }else {
            Database.add(new Util(Username,Password));
            return true;
        }

        }




    }


