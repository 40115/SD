package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class FroggerGameImpl extends UnicastRemoteObject implements FroggerGameRI{
    HashMap<UtilRI,GameStateRI> Utils;
    private String Dific=String.valueOf(Difficulty.MEDIUM);
    int N;
    LocalDate myObj = LocalDate.now();
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


    public void update_the_game(GameStateRI j,int type,int nroad) throws RemoteException {
        if (j.isMAster()) {
            for (GameStateRI l : this.Utils.values()) {

               l.getRoads().get(nroad).getTypes().add(type);



            }
        }

    }
    public void update_the_gameloggs(GameStateRI j,int type,int nriver) throws RemoteException {
        if (j.isMAster()) {
            for (GameStateRI l : this.Utils.values()) {
                    l.getRiver().get(nriver).getTypes().add(type);
            }
        }

    }
    public void update_the_game2(UtilRI g,int i) throws RemoteException {
        for (UtilRI s : this.Utils.keySet()) {
            if (s.hashCode() == g.hashCode()) {
                GameStateRI l = this.Utils.get(s);


                    if (l.getRoads().get(i).getTypes().size() > 0) {
                        l.getRoads().get(i).getTypes().remove(0);



                }

            }
        }

    }
    public void update_the_game2River(UtilRI g,int i) throws RemoteException {
        for (UtilRI s : this.Utils.keySet()) {
            if (s.hashCode() == g.hashCode()) {
                GameStateRI l = this.Utils.get(s);

                    if (l.getRiver().get(i).getTypes().size() > 0) {
                        l.getRiver().get(i).getTypes().remove(0);



                }

            }
        }

    }
    public void update_the_Frogger(Vect d, int h, UtilRI g) throws RemoteException {
        for (UtilRI s : this.Utils.keySet()) {
                GameStateRI l = this.Utils.get(s);
                    l.getFrogposition().get(h).setX(d.x);
                    l.getFrogposition().get(h).setY(d.y);
        }

    }
    public void update_the_Frogger2(UtilRI g,int h) throws RemoteException {
        for (UtilRI s : this.Utils.keySet()) {
            if (s.hashCode() == g.hashCode()) {
                GameStateRI l = this.Utils.get(s);
                    l.getFrogposition().set(h,new Vect(0,0));


            }
        }

    }
    public void Sync_Timer(int j, UtilRI g)throws RemoteException{
        for (UtilRI s : this.Utils.keySet()) {
                GameStateRI l = this.Utils.get(s);
                l.setLevelTimer(j);
        }
    }
    public void leve_the_game(GameSessionRI j) throws RemoteException{
        for (UtilRI l : this.Utils.keySet()) {
            if (l.hashCode()==j.getUtil().hashCode()){
                this.Utils.remove(l);
                if(this.Utils.size()==0){
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
            for (int i = 0; i <this.Utils.size() ; i++) {
                this.Utils.get(f).getFrogposition().add(new Vect(0.0,0.0));
                this.Utils.get(f).setReady(false);
            }


        }
        for (UtilRI f:this.Utils.keySet()) {
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

    public boolean check_Ready2()throws RemoteException {
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
