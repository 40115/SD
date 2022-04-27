package edu.ufp.inf.sd.rmi.Project.server;
import edu.ufp.inf.sd.rmi.Project.client.ProjectClientRI;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DB {
HashMap<String,String>Database=new HashMap<>();

    public DB() {

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