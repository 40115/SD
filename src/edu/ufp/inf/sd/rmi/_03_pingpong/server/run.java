package edu.ufp.inf.sd.rmi._03_pingpong.server;

import edu.ufp.inf.sd.rmi._03_pingpong.client.PongClient;
import edu.ufp.inf.sd.rmi._03_pingpong.client.PongImpl;
import edu.ufp.inf.sd.rmi._03_pingpong.client.PongRI;

import java.rmi.RemoteException;

public class run extends Thread{
    public void run(Ball ball, PongRI pong) throws RemoteException {
        System.out.println("Pong ID do Player: " + ball.getPlayerID());
        pong.pong(ball);
    }
}
