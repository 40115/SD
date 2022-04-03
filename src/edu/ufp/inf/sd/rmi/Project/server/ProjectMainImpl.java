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
   ArrayList<FroggerGame> Game=new ArrayList<>();
    public ProjectMainImpl() throws RemoteException {
        super();
    }


    @Override
    public String Connect()  throws RemoteException {
        return "\nHello, Welcome to City 17 you have chosen or been Chosen to...\n" +"Select An option:\n" + "1-Register\n" + "2-Login In\n" + "3-Leave\n";
    }
    public boolean Register(String Username, String Password,ProjectClientRI projectClientRI) throws RemoteException{
            return Database.Insert_Util(Username, Password,projectClientRI);
    }

    @Override
    public GameSessionRI Login(String Email, String Password,ProjectClientRI projectClientRI) throws RemoteException {
        if (Database.Check_Util(Email, Password)){
            if (users.get(new Util(Email,Password,projectClientRI))==null){
                GameSessionRI j=new GameSessionImpl(this,new Util(Email,Password,projectClientRI));
                users.put(new Util(Email,Password,projectClientRI),j);
                System.out.println("DOesnt\n");
                return j;
            }
            System.out.println("DO\n");
            return users.get(new Util(Email,Password,projectClientRI));
        }
        return null;

    }

}
