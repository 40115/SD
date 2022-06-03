package edu.ufp.inf.sd.rmi.Project.client;

import edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.Main;

import java.rmi.RemoteException;

public class ClienteGame extends  Thread{
    String[] strings;
    ObserverRI observerRI;

    public ClienteGame(String[] strings, ObserverRI observerRI) {
        this.strings = strings;
        this.observerRI = observerRI;
    }
    public void run(){
        String []strings={"ss","ss"};
        Main g= null;
        try {
            g = new Main(strings,observerRI);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        g.run();

    }

}
