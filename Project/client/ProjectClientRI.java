package edu.ufp.inf.sd.rmi.Project.client;

import edu.ufp.inf.sd.rmi.Project.server.FroggerGameImpl;
import edu.ufp.inf.sd.rmi.Project.server.GameState;
import edu.ufp.inf.sd.rmi.Project.server.GameStateRI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProjectClientRI extends Remote {
public void start_Game(GameStateRI j)throws RemoteException;

    void start_Game(GameStateRI j)throws RemoteException;

    public void test() throws RemoteException;
}
