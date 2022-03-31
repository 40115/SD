package edu.ufp.inf.sd.rmi.Project.server;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;

public class DB {
ArrayList<Util>Database=new ArrayList<>();

    public DB() {
        Database.add(new Util("Ruben","1234"));
        Database.add(new Util("Joana","2345"));

    }
    public Boolean Check_Util(String Username,String Password) throws RemoteException {
        for (int i = 0; i <this.Database.size() ; i++) {
            Util k=this.Database.get(i);

            if (Objects.equals(k.getEmail(), Username) && Objects.equals(k.getPassword(), Password)){
                return true;
            }
        }
        return false;
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


