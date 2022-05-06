package edu.ufp.inf.sd.rmi.Project.client;

import edu.ufp.inf.sd.rmi.Project.server.GameStateRI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProjectClientRI extends Remote {
 void start_Game(GameStateRI j)throws RemoteException;
 void test() throws RemoteException;

}
