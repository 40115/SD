package edu.ufp.inf.sd.rmi.Project.client;

import edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.Main;
import edu.ufp.inf.sd.rmi.Project.server.*;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectClientImpl  extends UnicastRemoteObject implements ProjectClientRI {
    private SetupContextRMI contextRMI;
    private boolean distrated=false;
    private GameStateRI currentgamestate;
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

    void lookupService() {
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
    }

    void playService() throws IOException{
        //============ Call HelloWorld remote service ============

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDRESS finish, bye. ;)");
        int op;
        do{
            System.out.println(this.projectServerMainRI.Connect());

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Reading data using readLine
            String name = reader.readLine();
            op=Integer.parseInt(name);
            GameSessionRI h;
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

    public void Register() throws IOException{
        System.out.println("\nEnter Name:\n");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine
        String name = reader.readLine();
        System.out.println("\nEnter Password:\n");
        reader = new BufferedReader(new InputStreamReader(System.in));
        String password= reader.readLine();

        if( this.projectServerMainRI.Register(name, password,this)) {
            System.out.println("\n User Registered\n");
            return;
        }
        System.out.println("\n User Already Exists\n");


    }
    public GameSessionRI Login() throws IOException{
        System.out.println("\nEnter Name:\n");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // Reading data using readLine
        String name = reader.readLine();
        System.out.println("\nEnter Password:\n");
        reader = new BufferedReader(new InputStreamReader(System.in));
        String password= reader.readLine();
        return this.projectServerMainRI.Login(name,password,this);
    }
    public void Gamesession(GameSessionRI Si) throws IOException{
        int op;
        do{
            System.out.println(  Si.Connect());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

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
                    Create_Game(Si);
                    break;
                case 4:
                    Si.LogOut();
                    System.out.println("Log outed");
                    return;
                default:
                    System.out.println("Not valid Input");
            }

        }while(op != 3);
    }


    @Override
    public void start_Game(GameStateRI j)throws RemoteException {
        currentgamestate=j;
        distrated=true;
        System.out.println("\nHERE\n");
    }

    @Override
    public void test()throws RemoteException {
        System.out.println("Start ");
    }

    public void Join_Game(GameSessionRI h) throws IOException {
        System.out.println("\n What game would you like to play with\n");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine
       int Id = Integer.parseInt(reader.readLine());
        FroggerGameRI l=h.join_Game(Id);
        if (l==null){
            System.out.println("\nGame not Found\n");
            return;
        }
        do{
            System.out.println("\nGame Found\n 0-Ready\n 1-Left\n");
            reader = new BufferedReader(new InputStreamReader(System.in));
            Id = Integer.parseInt(reader.readLine());
            if (Id==1){
                l.leve_the_game(h);
                return;
            }
        }while(Id!=0);
        l.ready_the_game(h.getUtil());
        while(!distrated){
            System.out.println("\nGame Ready\n1-Left\n");
            reader = new BufferedReader(new InputStreamReader(System.in));
            Id = Integer.parseInt(reader.readLine());
            if (Id==1){
                l.leve_the_game(h);
                return;
            }
            System.out.println("\nNot valid");
        }
        String[] f =new String[2];
        System.out.println("Start ");
        Main.main(f,currentgamestate);

    }
    public void Create_Game(GameSessionRI h) throws IOException {
        System.out.println("\n Creating Game\n");
        int n;
        FroggerGameRI l=h.Create_Game();
        if (l==null){
            System.out.println("\nGame not Made \n your probably already in a game\n");
            return;
        }
        do{
            System.out.println("\n Select Difficulty 1-Low 2-Medium 3-Hard\n");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            n = Integer.parseInt(reader.readLine());
            System.out.println(l.Difficulty(n));
        }while (n<0 || n>3);
        int Id;
        do{
        System.out.println("\nGame Created\n 0-Ready\n 1-Left\n");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Id = Integer.parseInt(reader.readLine());
        if (Id==1){
            l.leve_the_game(h);
            return;
        }
        l.ready_the_game(h.getUtil());
    }while(Id!=0);
        do{
            System.out.println("\nGame Ready\n1-Left\n0-check\n");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Id = Integer.parseInt(reader.readLine());
            if (Id==1){
                l.leve_the_game(h);
                return;
            }
        }while(!distrated);
        String[] f =new String[2];
        System.out.println("Start ");
        Main.main(f,currentgamestate);
    }
}
