package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FroggerGame {
    ArrayList<Util> Utils;
    private String Dific;
    private enum Difficulty{
        LOW,
        MEDIUM,
        HIGH;
    };

    public FroggerGame(ArrayList<Util> utils, String dific) {
        this.Utils = utils;
        this.Dific = dific;
    }

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
    public boolean check_Util(String User){
        for (Util util : this.Utils) {
            if (Objects.equals(util.getEmail(), User)) {
                return true;
            }
        }
        return false;
    }

}
