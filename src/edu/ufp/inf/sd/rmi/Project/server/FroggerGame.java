package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;

public class FroggerGame {
    private String Dific;
    private enum Difficulty{
        LOW,
        MEDIUM,
        HIGH;
    };

    public String Difficulty(int difficulty) throws RemoteException {


        switch (difficulty){
            case 1:
                this.Dific= String.valueOf(Difficulty.LOW);
                return "Dificulty has been set at" + Dific;

            case 2:
                this.Dific= String.valueOf(Difficulty.MEDIUM);
                return "Dificulty has been set at" + Dific;

            case 3:
                this.Dific= String.valueOf(Difficulty.HIGH);
                return "Dificulty has been set at" + Dific;

            default:
                this.Dific=String.valueOf(Difficulty.MEDIUM);
                return "Invalied option Default has been selected" + Dific;
        }
    }
}
