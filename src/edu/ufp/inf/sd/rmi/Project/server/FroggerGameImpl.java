package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class FroggerGameImpl extends UnicastRemoteObject implements FroggerGameRI{
    HashMap<UtilRI,GameStateRI> Utils;
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

    public FroggerGameImpl( HashMap<UtilRI,GameStateRI>  utils,  Integer n, ProjectMainImpl pm) throws RemoteException {
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


    public void update_the_game(GameStateRI j,int type) throws RemoteException {
        if (j.isMAster()) {
            for (GameStateRI l : this.Utils.values()) {
                if (!l.isMAster()) {
                    l.getRoads().get(0).types.add(type);
                    l.getRoads().get(1).types.add(type);
                    l.getRoads().get(2).types.add(type);
                    l.getRoads().get(3).types.add(type);

                }

            }
        }

    }
    public void update_the_game2(UtilRI g,int i) throws RemoteException {
        for (UtilRI s : this.Utils.keySet()) {
            if (s.hashCode() == g.hashCode()) {
                GameStateRI l = this.Utils.get(s);

                if (!l.isMAster()) {
                    if (l.getRoads().get(i).getTypes().size() > 2) {
                        l.getRoads().get(i).getTypes().remove(0);

                    }

                }

            }
        }

    }
    public void leve_the_game(GameSessionRI j) throws RemoteException{
        for (UtilRI l : this.Utils.keySet()) {
            if (l.hashCode()==j.getUtil().hashCode()){
                this.Utils.remove(l);
                if(this.Utils.size()==0){
                    j.getPM().Remove_Game();
                    return;
                }
                if ( this.check_Ready()){
                    this.start_Game();
                }
                return;
            }
        }

    }
    public void ready_the_game(UtilRI h) throws RemoteException{
        for (UtilRI f:this.Utils.keySet()) {
            if (f.hashCode()==h.hashCode()){
                this.Utils.get(f).setReady(true);
              if ( this.check_Ready()){
                  this.start_Game();
              }
                return;
            }

        }
    }

    private void start_Game() throws RemoteException{

        for (UtilRI f:this.Utils.keySet()) {
            System.out.println("Here");
       f.getProjectClientRI().start_Game(this.Utils.get(f));
        }
    }

    private boolean check_Ready()throws RemoteException {

        if (this.Utils.size()<2){
            return false;
        }
        for (GameStateRI f:this.Utils.values()) {
            if (!f.isReady()){
             return false;
            }

        }
return true;
    }
    public boolean is_Ended() throws RemoteException{
        return End;
    }

    public HashMap<UtilRI, GameStateRI> getUtils() throws RemoteException{
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
