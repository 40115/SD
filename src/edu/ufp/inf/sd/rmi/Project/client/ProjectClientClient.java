package edu.ufp.inf.sd.rmi.Project.client;


import edu.ufp.inf.sd.rmi.Project.server.DB;
import edu.ufp.inf.sd.rmi.Project.server.GameSessionRI;
import edu.ufp.inf.sd.rmi.Project.server.ProjectMainImpl;
import edu.ufp.inf.sd.rmi.Project.server.ProjectMainRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectClientClient {
    private SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold the Servant proxy
     */
   private ProjectMainRI projectServerMainRI;
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (args != null && args.length < 2) {
            System.err.println("usage: java [options] edu.ufp.sd.inf.rmi._01_helloworld.server.HelloWorldClient <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
            //1. ============ Setup client RMI context ============
           ProjectClientClient hwc=new ProjectClientClient(args);
            //2. ============ Lookup service ============
            hwc.lookupService();
            //3. ============ Play with service ============
            hwc.playService();
        }
    }

    public ProjectClientClient(String[] args) {
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

    private Remote lookupService() {
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

    private void playService() throws IOException, RemoteException{
        //============ Call HelloWorld remote service ============

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR finish, bye. ;)");
         int op;
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
             if( this.projectServerMainRI.Register(name, password)) {
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
return this.projectServerMainRI.Login(name,password);
    }
    public void Gamesession(GameSessionRI Si) throws IOException {
        int op;
        do{
            System.out.println(  Si.Connect());

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            // Reading data using readLine
            String name = reader.readLine();
            op=Integer.parseInt(name);

            switch (op){
                case 1:

                    break;

                case 2:

                    break;

                case 3:

                    break;

                default:
                    System.out.println("Not Valied Input");
            }

        }while(op!=3);


    }
    public void GameList(GameSessionRI Si){


    }
}

