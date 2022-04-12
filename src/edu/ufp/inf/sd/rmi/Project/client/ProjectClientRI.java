package edu.ufp.inf.sd.rmi.Project.client;

import edu.ufp.inf.sd.rmi.Project.server.FroggerGame;
import edu.ufp.inf.sd.rmi.Project.server.GameState;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProjectClientRI extends Remote {
public void start_Game(GameState j, FroggerGame k)throws RemoteException;
    void update_the_game(GameState j) throws RemoteException;
}
