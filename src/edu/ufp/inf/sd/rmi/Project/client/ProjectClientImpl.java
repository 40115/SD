package edu.ufp.inf.sd.rmi.Project.client;

import edu.ufp.inf.sd.rmi.Project.server.FroggerGame;
import edu.ufp.inf.sd.rmi.Project.server.GameSessionRI;
import edu.ufp.inf.sd.rmi.Project.server.GameState;
import edu.ufp.inf.sd.rmi.Project.server.ProjectMainRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectClientImpl  extends UnicastRemoteObject implements ProjectClientRI {
    private SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold the Servant proxy
     */
    private ProjectMainRI projectServerMainRI;
    public ProjectClientImpl(String[] args) throws RemoteException {

        super();
        try {
            //List ans set args
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            //Create a context for RMI setup
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(ProjectClientClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    Remote lookupService() {
        try {
            //Get proxy MAIL_TO_ADDR rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR lookup service @ {0}", serviceUrl);

                //============ Get proxy MAIL_TO_ADDR HelloWorld service ============
                projectServerMainRI = (ProjectMainRI)  registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return projectServerMainRI;
    }

    void playService() throws IOException, RemoteException{
        //============ Call HelloWorld remote service ============

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR finish, bye. ;)");
        int op=-1;
        do{
            System.out.println(  this.projectServerMainRI.Connect());

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            // Reading data using readLine
            String name = reader.readLine();
            op=Integer.parseInt(name);
            GameSessionRI h=null;
            switch (op){
                case 1:
                    Register();
                    break;

                case 2:
                    h=Login();
                    if(h==null){
                        System.out.println("\n Login Invalid\n");
                    }else {
                        System.out.println("\n Login valid\n");
Gamesession(h);
                    }

                    break;

                case 3:

                    break;

                default:
                    System.out.println("Not Valied Input");
            }

        }while(op!=3);
    }

    public void Register() throws IOException,RemoteException {
        System.out.println("\nEnter Name:\n");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        String name = reader.readLine();
        System.out.println("\nEnter Password:\n");
        reader = new BufferedReader(
                new InputStreamReader(System.in));
        String password= reader.readLine();

        if( this.projectServerMainRI.Register(name, password,this)) {
            System.out.println("\n User Registered\n");
            return;
        }
        System.out.println("\n User Already EXists\n");


    }
    public GameSessionRI Login() throws IOException ,RemoteException{
        System.out.println("\nEnter Name:\n");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        String name = reader.readLine();
        System.out.println("\nEnter Password:\n");
        reader = new BufferedReader(
                new InputStreamReader(System.in));
        String password= reader.readLine();
        return this.projectServerMainRI.Login(name,password,this);
    }
    public void Gamesession(GameSessionRI Si) throws IOException ,RemoteException{

        int op=-1;
        do{
            System.out.println(  Si.Connect());

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            // Reading data using readLine
            String name = reader.readLine();
            op=Integer.parseInt(name);

            switch (op){
                case 1:
                     System.out.println(Si.List_Games());
                    break;

                case 2:
Join_Game(Si);
                    break;
                case 3:

                    break;

                case 4:
      Si.LogOut();


                default:
                    System.out.println("Not Valied Input");
            }

        }while(op != 3);


    }


    @Override
    public void start_Game()throws RemoteException {


    }

    public void Join_Game(GameSessionRI h) throws IOException {
        System.out.println("\n What game would you like to play with\n");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
       Integer Id = Integer.valueOf(reader.readLine());
        FroggerGame l=h.join_Game(Id);


do{

}while(true);
    }

    public void update_the_game(GameState j)throws RemoteException{



    }

}
