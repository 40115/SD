package edu.ufp.inf.sd.rmi.Project.client;

import edu.ufp.inf.sd.rmi.Project.server.FroggerGameRI;
import edu.ufp.inf.sd.rmi.Project.server.Road_Line;
import edu.ufp.inf.sd.rmi.Project.server.UtilRI;
import edu.ufp.inf.sd.rmi.Project.server.Vect;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class State implements Serializable {
    private static final int FROGGER_LIVES =5 ;
    private ArrayList<Integer> isDead=new ArrayList<>();
    public int levelTimer=60;
    private boolean HAsended=false;
    private boolean Ready=false;

    public ArrayList<Integer> GameLives =new ArrayList<>();
    public ArrayList<Integer> IsReached=new ArrayList<>();
    private int dig;
    private UtilRI util;
    private int GameStage=0;
    ArrayList<Vect> Frogposition=new ArrayList<>();
    ArrayList<Road_Line> roads=new ArrayList<>();
    ArrayList<Road_Line> river=new ArrayList<>();

    public State( int dig) {
        this.dig = dig;
        roads.add(new Road_Line(new ArrayList<>(),0));
        roads.add(new Road_Line(new ArrayList<>(),1));
        roads.add(new Road_Line(new ArrayList<>(),2));
        roads.add(new Road_Line(new ArrayList<>(),3));
        roads.add(new Road_Line(new ArrayList<>(),4));
        river.add(new Road_Line(new ArrayList<>(),0));
        river.add(new Road_Line(new ArrayList<>(),1));
        river.add(new Road_Line(new ArrayList<>(),2));
        river.add(new Road_Line(new ArrayList<>(),3));
        river.add(new Road_Line(new ArrayList<>(),4));

    }

    public ArrayList<Integer> getIsDead() throws RemoteException {
        return isDead;
    }

    public void setIsDead(ArrayList<Integer> isDead) throws RemoteException{
        this.isDead = isDead;
    }

    public int getLevelTimer() {
        return levelTimer;
    }

    public void setLevelTimer(int levelTimer)throws RemoteException {
        this.levelTimer = levelTimer;
    }



    public boolean isHAsended()throws RemoteException {
        return HAsended;
    }

    public void setHAsended(boolean HAsended)throws RemoteException {
        this.HAsended = HAsended;
    }

    public boolean isReady()throws RemoteException {
        return Ready;
    }

    public void setReady(boolean ready)throws RemoteException {
        Ready = ready;
    }


    public ArrayList<Integer> getGameLives()throws RemoteException  {
        return GameLives;
    }

    public void setGameLives(ArrayList<Integer> gameLives) throws RemoteException {
        GameLives = gameLives;
    }

    public int getDig() throws RemoteException{
        return dig;
    }

    public void setDig(int dig)throws RemoteException {
        this.dig = dig;
    }

    public UtilRI getUtil() throws RemoteException{
        return util;
    }

    public void setUtil(UtilRI util)throws RemoteException {
        this.util = util;
    }

    public ArrayList<Vect> getFrogposition()throws RemoteException {
        return Frogposition;
    }

    public void setFrogposition(ArrayList<Vect> frogposition) throws RemoteException{
        Frogposition = frogposition;
    }

    public ArrayList<Road_Line> getRoads() throws RemoteException{
        return roads;
    }

    public void setRoads(ArrayList<Road_Line> roads)throws RemoteException {
        this.roads = roads;
    }

    public ArrayList<Road_Line> getRiver()throws RemoteException {
        return river;
    }

    public void setRiver(ArrayList<Road_Line> river)throws RemoteException {
        this.river = river;
    }

    public ArrayList<Integer> getIsReached() throws RemoteException{
        return IsReached;
    }

    public void setIsReached(ArrayList<Integer> isReached)throws RemoteException {
        IsReached = isReached;
    }

    public int getGameStage() throws RemoteException{
        return GameStage;
    }

    public void setGameStage(int gameStage)throws RemoteException {
        GameStage = gameStage;
    }
}
