package edu.ufp.inf.sd.rmi.Project.server;
import edu.ufp.inf.sd.rmi.Project.client.ProjectClientRI;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;

public class DB {
ArrayList<Util>Database=new ArrayList<>();

    public DB() {
    }
    public Boolean Check_Util(String Username,String Password) throws RemoteException {
        for (Util k : this.Database) {
            if (Objects.equals(k.getEmail(), Username) && Objects.equals(k.getPassword(), Password)) {
                return true;
            }
        }
        return false;
    }
    public boolean Insert_Util(String Username, String Password, ProjectClientRI projectClientRI) throws RemoteException {
        if (Check_Util(Username,Password)){
            return false;
        }else {
            Database.add(new Util(Username,Password,projectClientRI));
            return true;
        }

        }




    }


