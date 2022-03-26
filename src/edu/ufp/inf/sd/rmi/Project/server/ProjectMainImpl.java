package edu.ufp.inf.sd.rmi.Project.server;

import edu.ufp.inf.sd.rmi.Project.client.ProjectClientRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProjectMainImpl extends UnicastRemoteObject implements ProjectMainRI {
   DB Database=new DB();
   HashMap<Util,GameSessionRI> users = new HashMap<>();
    public ProjectMainImpl() throws RemoteException {
        super();
    }


    @Override
    public String Connect()  throws RemoteException {
        return "\nHello, Welcome to City 17 you have chosen or been Chosen to...\n" +"Select An option:\n" + "1-Register\n" + "2-Login In\n" + "3-Leave\n";
    }
    public boolean Register(String Username, String Password) throws RemoteException{
            return Database.Insert_Util(Username, Password);
    }

    @Override
    public GameSessionRI Login(String Username, String Password) throws RemoteException {
        if (Database.Check_Util(Username, Password)){
            if (users.get(new Util(Username,Password))==null){
                GameSessionRI j=new GameSessionImpl(this,new Util(Username,Password));
                users.put(new Util(Username,Password),j);
                return j;
            }
            return users.get(new Util(Username,Password));
        }
        return null;

    }

}
