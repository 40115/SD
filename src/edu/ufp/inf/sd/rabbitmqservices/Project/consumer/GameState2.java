package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import edu.ufp.inf.sd.rmi.Project.server.FroggerGameRI;
import edu.ufp.inf.sd.rmi.Project.server.Road_Line;
import edu.ufp.inf.sd.rmi.Project.server.UtilRI;
import edu.ufp.inf.sd.rmi.Project.server.Vect;

import java.util.ArrayList;

public class GameState2 {
    private static final int FROGGER_LIVES =5 ;
    ArrayList<Vect> Frogposition=new ArrayList<>();
    ArrayList<Road_Line> roads=new ArrayList<>();
    ArrayList<Road_Line> river=new ArrayList<>();
    private ArrayList<Integer> isDead=new ArrayList<>();
    private int Refe;
    private boolean isMAster=false;
    public int levelTimer=0;
    private boolean HAsended=false;
    private boolean Ready=false;
    public int GameLives    = FROGGER_LIVES;
    private int dig;

public String Name;

    public GameState2(int refe, boolean isMAster, String name) {
        Refe = refe;
        this.isMAster = isMAster;
        Name = name;
        river.add(new Road_Line(new ArrayList<>(),0));
        river.add(new Road_Line(new ArrayList<>(),1));
        river.add(new Road_Line(new ArrayList<>(),2));
        river.add(new Road_Line(new ArrayList<>(),3));
        river.add(new Road_Line(new ArrayList<>(),4));
roads.add(new Road_Line(new ArrayList<>(),0));
        roads.add(new Road_Line(new ArrayList<>(),1));
        roads.add(new Road_Line(new ArrayList<>(),2));
        roads.add(new Road_Line(new ArrayList<>(),3));
        roads.add(new Road_Line(new ArrayList<>(),4));

    }

    public ArrayList<Integer> getIsDead() {
        return isDead;
    }

    public void setIsDead(ArrayList<Integer> isDead) {
        this.isDead = isDead;
    }

    public int getRefe() {
        return Refe;
    }

    public void setRefe(int refe) {
        Refe = refe;
    }

    public ArrayList<Vect> getFrogposition() {
        return Frogposition;
    }

    public void setFrogposition(ArrayList<Vect> frogposition) {
        Frogposition = frogposition;
    }

    public ArrayList<Road_Line> getRoads() {
        return roads;
    }

    public void setRoads(ArrayList<Road_Line> roads) {
        this.roads = roads;
    }

    public ArrayList<Road_Line> getRiver() {
        return river;
    }

    public void setRiver(ArrayList<Road_Line> river) {
        this.river = river;
    }

    public boolean isMAster() {
        return isMAster;
    }

    public void setMAster(boolean MAster) {
        isMAster = MAster;
    }

    public int getLevelTimer() {
        return levelTimer;
    }

    public void setLevelTimer(int levelTimer) {
        this.levelTimer = levelTimer;
    }

    public boolean isHAsended() {
        return HAsended;
    }

    public void setHAsended(boolean HAsended) {
        this.HAsended = HAsended;
    }

    public boolean isReady() {
        return Ready;
    }

    public void setReady(boolean ready) {
        Ready = ready;
    }

    public int getGameLives() {
        return GameLives;
    }

    public void setGameLives(int gameLives) {
        GameLives = gameLives;
    }

    public int getDig() {
        return dig;
    }

    public void setDig(int dig) {
        this.dig = dig;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
