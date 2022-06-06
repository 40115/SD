package edu.ufp.inf.sd.rmi.Project.server;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.ufp.inf.sd.rmi.Project.client.ObserverRI;
import edu.ufp.inf.sd.rmi.Project.client.State;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class FroggerGameImpl extends UnicastRemoteObject implements FroggerGameRI{
    ArrayList<UtilRI> Util=new ArrayList<>();
   ArrayList<ObserverRI> Obs=new ArrayList<>();
   State state_Last;
    private Integer Dific;


    protected boolean End=false;
    protected boolean Run=false;
    private final ProjectMainImpl PM;

    public FroggerGameImpl(ProjectMainImpl pm, int Id) throws RemoteException {
        super();
this.Dific=Id;
        this.PM = pm;
this.state_Last=new State(Id);
    }
    // Seleção da dificuldade do jogo




    // Atualização do jogo entre todos os jogadores, fazendo a adição das ruas na ArrayList

public void NotifyAll()throws RemoteException{

    for (ObserverRI ob : this.Obs) {
        try {
            ob.setState(state_Last);
        }catch (RemoteException exception ){
Obs.remove(ob);
        }
    }

}

    @Override
    public FroggerGameRI attach(ObserverRI h,UtilRI d)throws RemoteException {
        for (ObserverRI ob : Obs) {
            if (ob.hashCode() == h.hashCode()) return this;
        }

Util.add(d);
        h.setFroggerGameRI(this);

        Obs.add(h);
        return this;
    }

    @Override
    public boolean dettach(ObserverRI h,UtilRI d) throws RemoteException{
        for (ObserverRI ob : Obs) {
            if (ob.hashCode() == h.hashCode()){

                Obs.remove(ob);
                for (int i = 0; i <Util.size() ; i++) {
                    if (d.hashCode()==Util.get(i).hashCode()){
                        Util.remove(i);
                        i--;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void setState(State state,int stat)throws RemoteException {


        switch (stat){
    case 0:
        this.state_Last.setRoads(state.getRoads());
        this.state_Last.setRiver(state.getRiver());
        this.state_Last.setLevelTimer(state.getLevelTimer());
        this.state_Last.setGameStage(state.getGameStage());
        this.state_Last.getFrogposition().set(0,state.getFrogposition().get(0));
        this.state_Last.getIsDead().set(0,state.getIsDead().get(0));
        this.state_Last.getGameLives().set(0,state.GameLives.get(0));
        break;
    case 1:
    case 2:
    case 3:
    case 4:
        this.state_Last.setGameStage(state.getGameStage());
        this.state_Last.getFrogposition().set(stat-1,state.getFrogposition().get(stat-1));
        this.state_Last.getIsDead().set(stat-1,state.getIsDead().get(stat-1));
        this.state_Last.getGameLives().set(stat-1,state.GameLives.get(stat-1));
        break;

}

this.NotifyAll();

    }
public void finnished(ObserverRI y)throws RemoteException{
    for (ObserverRI ob : this.Obs) {
        if (y.hashCode()==ob.hashCode()) {
            ob.setIsfinnsihed(true);
            finnished2();
            this.state_Last.setHAsended(true);
        }
    }


}
    public void finnished2()throws RemoteException{
        for (ObserverRI ob : this.Obs) {
            if (!ob.isIsfinnsihed()) {
                return;
            }
        }
String name="";
        int points=0;
        for (int i = 0; i <Obs.size() ; i++) {
            if (points<Obs.get(i).getPoints()){
               name= Util.get(i).getEmail();
               points=Obs.get(i).getPoints();
            }
        }
        for (UtilRI utilRI : Util) {
            utilRI.getProjectClientRI().Victory(name);
        }

    }
    @Override
    public State get_State() throws RemoteException {
return this.state_Last;
    }

    // Obtêm o estado atual do jogo


    public boolean isRun() throws RemoteException{
        return Run;
    }

    public ArrayList<UtilRI> getUtil()throws RemoteException {
        return Util;
    }

    @Override
    public void Ready_UP_CHECK() throws RemoteException {

        if (Obs.size()<2) return;
        for (ObserverRI ob : Obs) {
            if (!ob.isReady()){
                return;}

        }

        state_Last.setDig(Dific);
        state_Last.setLevelTimer(60);
        state_Last.getRoads().get(0).getTypes().add(-1);
        state_Last.getRoads().get(1).getTypes().add(-1);
        state_Last.getRoads().get(2).getTypes().add(-1);
        state_Last.getRoads().get(3).getTypes().add(-1);
        state_Last.getRoads().get(4).getTypes().add(-1);
state_Last.getRiver().get(0).getTypes().add(-1);
        state_Last.getRiver().get(1).getTypes().add(-1);
        state_Last.getRiver().get(2).getTypes().add(-1);
        state_Last.getRiver().get(3).getTypes().add(-1);
        state_Last.getRiver().get(4).getTypes().add(-1);
        for (int i = 0; i <Obs.size() ; i++) {

            Obs.get(i).setMAster(i == 0);
            Obs.get(i).setRefference(i);
            state_Last.getFrogposition().add(new Vect(0,0));
            state_Last.getGameLives().add(5);
            state_Last.getIsDead().add(0);
            state_Last.getIsReached().add(0);
            Obs.get(i).setState(state_Last);
            Obs.get(i).setReady3(false);
        }
        for (UtilRI utilRI: this.Util){
            utilRI.getProjectClientRI().start(state_Last);

        }
    }

    @Override
    public void Ready_UP_CHECK2() throws RemoteException {
        for (ObserverRI ob : Obs) {
            if (!ob.isReady()) return;
        }
        for (ObserverRI ob : Obs) {
            ob.setStarted(true);
        }
    }

    public void setUtil(ArrayList<UtilRI> util)throws RemoteException {
        Util = util;
    }
}
