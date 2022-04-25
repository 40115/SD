package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Objects;

public class FroggerGameImpl extends UnicastRemoteObject implements FroggerGameRI{
    HashMap<Util,GameState> Utils;
    private String Dific=String.valueOf(Difficulty.MEDIUM);
    int N;

    protected boolean End=false;
    protected boolean Run=false;
    private enum Difficulty{
        LOW,
        MEDIUM,
        HIGH
    }
    private final ProjectMainImpl PM;

    public FroggerGameImpl(HashMap<Util,GameState> utils,  Integer n, ProjectMainImpl pm) throws RemoteException {
        super();
        this.Utils = utils;
        this.N=n;
        this.PM = pm;
    }

    public String Difficulty(int difficulty) throws RemoteException {
        switch (difficulty){
            case 1:
                this.Dific= String.valueOf(Difficulty.LOW);
                return "Dificulty has been set at " + Dific;

            case 2:
                this.Dific= String.valueOf(Difficulty.MEDIUM);
                return "Dificulty has been set at " + Dific;

            case 3:
                this.Dific= String.valueOf(Difficulty.HIGH);
                return "Dificulty has been set at " + Dific;

            default:
                this.Dific= String.valueOf(Difficulty.MEDIUM);
                return "Invalied option Default has been selected " + Dific;
        }
    }


    public void update_the_game(GameState j) throws RemoteException {
        for (GameState l : this.Utils.values()) {
            if (j.isMAster()) {
                l.setRoadLine1(j.getRoadLine1());
                l.setRoadLine2(j.getRoadLine2());
                l.setRoadLine3(j.getRoadLine3());
                l.setRoadLine4(j.getRoadLine4());
                l.setRoadLine5(j.getRoadLine5());
                l.setRiverLine1(j.getRiverLine1());
                l.setRiverLine2(j.getRiverLine2());
                l.setRiverLine3(j.getRiverLine3());
                l.setRiverLine4(j.getRiverLine4());
                l.setRiverLine5(j.getRiverLine5());
            }

                l.getFrog().set(j.getRefe(),j.getFrog().get(j.getRefe()));
        }

    }
    public void leve_the_game(GameSessionRI j) throws RemoteException{
        for (Util l : this.Utils.keySet()) {
            if (l==j.getUtil()){
                this.Utils.remove(l);
                if(this.Utils.size()==0){
                    j.getPM().Remove_Game(this);
                }
                return;
            }
        }

    }
    public void ready_the_game(Util h) throws RemoteException{
        for (Util f:this.Utils.keySet()) {
            if (f==h){
                System.out.println("G");
                this.Utils.get(f).setReady(true);
              if ( this.check_Ready()){
                  this.start_Game();
              }
                return;
            }

        }
    }

    private void start_Game() throws RemoteException{

        for (Util f:this.Utils.keySet()) {
       f.getProjectClientRI().start_Game(this.Utils.get(f));

        }
    }

    private boolean check_Ready()throws RemoteException {

        if (this.Utils.size()<2){
            return false;
        }
        for (GameState f:this.Utils.values()) {
            if (!f.isReady()){
             return false;
            }

        }
return true;
    }
    public boolean is_Ended() throws RemoteException{
        return End;
    }

    public HashMap<Util, GameState> getUtils() throws RemoteException{
        return Utils;
    }



    public String getDific()throws RemoteException {
        return Dific;
    }



    public int getN()throws RemoteException {
        return N;
    }


    public boolean isRun() throws RemoteException{
        return Run;
    }


}