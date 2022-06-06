package edu.ufp.inf.sd.rmi.Project.client;

import edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.Main;
import edu.ufp.inf.sd.rmi.Project.server.*;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectClientImpl  extends UnicastRemoteObject implements ProjectClientRI {
    private SetupContextRMI contextRMI;
    private boolean distrated=false;

    ObserverRI observerRI=new ObserverImpl(-1,new State(-1),null);
    /**
     * Remote interface that will hold the Servant proxy
     */
    private ProjectMainRI projectServerMainRI;
    // Faz a ligação ao ContextRMI

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
    // Verifica se o registo está correto e aguarda pela confirmação

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
    // Recebe e coordena a interface inicial no registo do jogo

    void playService() throws IOException, RemoteException, InterruptedException {
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

    // Regista o jogador no servidor, se este não existir

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
    // Faz o login do jogador e devolve null caso nao se pode auth

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
    // Permite listar os jogos, juntar a um jogo, criar um jogo e fazer log out
    public void Gamesession(GameSessionRI Si) throws IOException, RemoteException, InterruptedException {
if(Si==null){
    return;
}
        int op=-1;
        do{
            System.out.println(  Si.Connect());

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
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
                    System.out.println("Not Valied Input");
            }

        }while(op != 5);


    }

    // Inicia o jogo recebendo a referenci a interface do estado do jogo



    // Verifica se o client ainda eat disponovel apartir do server

    @Override
    public void test()throws RemoteException {
        System.out.println("Start ");
    }
    public void start(State j)throws RemoteException {
        observerRI.setState(j);
        distrated=true;
    }

    // Faz com que o jogador se junte ao jogo (attach)

    public void Join_Game(GameSessionRI h) throws IOException, InterruptedException {
        System.out.println("\n What game would you like to play with\n");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        int Id = Integer.parseInt(reader.readLine());
        FroggerGameRI l = h.join_Game(Id);
        if (l == null) {
            System.out.println("\nGame not Found\n");
            return;
        }

        do {
            System.out.println("\nGame Found\n 0-Join\n 1-Left\n");
            reader = new BufferedReader(
                    new InputStreamReader(System.in));
            Id = Integer.parseInt(reader.readLine());
            if (Id == 1) {
                l.dettach(observerRI, h.getUtil());
                return;
            }

        } while (Id != 0);
        FroggerGameRI s= l.attach(observerRI,h.getUtil());
         if (s==null) return;
         Id=-1;
         while(Id!=0){
             System.out.println("\nGame Found\n 0-Ready\n 1-Left\n");
             reader = new BufferedReader(
                     new InputStreamReader(System.in));
             Id = Integer.parseInt(reader.readLine());
             if (Id == 1) {
                 l.dettach(observerRI,h.getUtil());
                 return;
             }
         }
         observerRI.setReady(true);
while (true){
    TimeUnit.MILLISECONDS.sleep(50);
    if (distrated){
        break;
    }
}

String []strings={"ss","ss"};
/*Main g=new Main(strings,observerRI);
g.run();
*/
        ClienteGame clienteGame=new ClienteGame(strings,observerRI);
        clienteGame.start();

}

    // Permite ao jogador criar um jogo como o servidor

    public void Create_Game(GameSessionRI h) throws IOException,RemoteException {
        System.out.println("\n Creating Game\n");
        int n = 0;
        System.out.println("\nSelect dificulty \n 1-Low\n2-Medium\n 3-High\n");
        int Id=-1;
        do {
           BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
            Id = Integer.parseInt(reader.readLine());

        }while(Id<1 || Id>3);
        h.Create_Game(Id);


    }
    public void Victory(String m) throws RemoteException{
        System.out.println(m+" HAs won");

    }
}
