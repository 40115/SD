package edu.ufp.inf.sd.rmi.Project.client;


import edu.ufp.inf.sd.rmi.Project.server.DB;
import edu.ufp.inf.sd.rmi.Project.server.GameSessionRI;
import edu.ufp.inf.sd.rmi.Project.server.ProjectMainImpl;
import edu.ufp.inf.sd.rmi.Project.server.ProjectMainRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import javax.xml.crypto.dsig.spec.HMACParameterSpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectClientClient {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, RemoteException {
        if (args != null && args.length < 2) {
            System.err.println("usage: java [options] edu.ufp.sd.inf.rmi._01_helloworld.server.HelloWorldClient <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
            //1. ============ Setup client RMI context ============
            ProjectClientImpl hwc=new ProjectClientImpl(args);
            //2. ============ Lookup service ============
            hwc.lookupService();
            //3. ============ Play with service ============
            hwc.playService();
        }

    }

}

