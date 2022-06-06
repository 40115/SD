package edu.ufp.inf.sd.rmi.Project.client;



import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProjectClientRI extends Remote {
 void start(State j)throws RemoteException;
 void test() throws RemoteException;
 void Victory(String m) throws RemoteException;
}
