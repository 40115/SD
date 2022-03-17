package edu.ufp.inf.sd.rmi._03_pingpong.server;

import edu.ufp.inf.sd.rmi._03_pingpong.client.PongRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface PingRI extends Remote {
    public void ping(Ball bola, PongRI clientPongRI) throws RemoteException;
    public void receive() throws RemoteException;
    public void threw() throws RemoteException;


}
