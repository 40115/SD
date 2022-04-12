package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Objects;

public class FroggerGame {
    HashMap<Util,GameState> Utils;
    private String Dific;
    int N;

    protected boolean Run=false;
    private enum Difficulty{
        LOW,
        MEDIUM,
        HIGH;
    };
    private final ProjectMainImpl PM;

    public FroggerGame(HashMap<Util,GameState> utils, String dific, Integer n, ProjectMainImpl pm) {
        this.Utils = utils;
        this.Dific = dific;
        this.N=n;
       this.PM = pm;
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
                this.Dific= String.valueOf(Difficulty.MEDIUM);
                return "Invalied option Default has been selected" + Dific;
        }
    }
    public boolean check_Util(String User) {
        for (Util l : this.Utils.keySet()) {
            if (Objects.equals(l.getEmail(), User)) {
                return true;
            }
        }
        return false;
    }

    public void update_the_game(GameState j) throws RemoteException {
        for (Util l : this.Utils.keySet()) {
          l.getProjectClientRI().update_the_game(j);

        }

    }

    public HashMap<Util, GameState> getUtils() {
        return Utils;
    }



    public String getDific() {
        return Dific;
    }



    public int getN() {
        return N;
    }


    public boolean isRun() {
        return Run;
    }


}
