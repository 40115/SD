package edu.ufp.inf.sd.rmi._03_pingpong.server;

import edu.ufp.inf.sd.rmi._03_pingpong.client.PongRI;
import edu.ufp.inf.sd.rmi._03_pingpong.server.PingRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PingImpl extends UnicastRemoteObject implements PingRI {

    private SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold reference MAIL_TO_ADDR the Servant impl
     */
    private edu.ufp.inf.sd.rmi._03_pingpong.server.PingRI PingRI;

    public PingImpl() throws RemoteException {
        super();
    }

    @Override
    public void ping(Ball bola, PongRI clientPongRI) throws RemoteException {
         run thread = new run();
        thread.run(bola, clientPongRI);
    }

    @Override
    public void receive() throws RemoteException {

    }

    @Override
    public void threw() throws RemoteException {

    }

}
