package edu.ufp.inf.sd.rmi._03_pingpong.client;

import edu.ufp.inf.sd.rmi._03_pingpong.server.Ball;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface PongRI extends Remote {
    public void pong(Ball bola) throws RemoteException;

}
