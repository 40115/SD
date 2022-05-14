package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameState extends UnicastRemoteObject implements GameStateRI{
    private int Refe;
    private boolean isMAster=false;
    public int levelTimer=0;
    private boolean HAsended=false;
    private boolean Ready=false;
    public FroggerGameRI c;
    private int dig;
private  UtilRI util;
    public  int refer=0;
    ArrayList<Vect> Frogposition=new ArrayList<>();
      ArrayList<Road_Line> roads=new ArrayList<>();
    ArrayList<Road_Line> river=new ArrayList<>();
    protected GameState() throws RemoteException {
        super();
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

    public int getLevelTimer() throws RemoteException{
        return levelTimer;
    }

    public void setLevelTimer(int levelTimer)throws RemoteException {
        this.levelTimer = levelTimer;
    }

    public void sync_Frogger(Vect vect, int Refe)throws RemoteException{

c.update_the_Frogger(vect,Refe,util);
Frogposition.set(Refe,vect);
    }
    public void sync_Road_Line(int type, int nriverline)throws RemoteException{
    roads.get(nriverline).types.add(type);
    c.update_the_game(this,type,nriverline);
    }
    public void sync_River_Line(int type, int nriverline)throws RemoteException{
        river.get(nriverline).types.add(type);
        c.update_the_gameloggs(this,type,nriverline);
    }
    public void sync_Timer(int type)throws RemoteException{
     c.Sync_Timer(type,util);
    }
    public int getDig() throws RemoteException{
        return dig;
    }

    public void setDig(int dig) throws RemoteException{
        this.dig = dig;
    }

    public int getRefer() {
        return refer;
    }

    public void setRefer(int refer) {
        this.refer = refer;
    }

    public int getRefe() throws RemoteException {
        return Refe;
    }

    public void setRefe(int refe)throws RemoteException {
        Refe = refe;
    }

    @Override
    public boolean isMAster()throws RemoteException {
        return isMAster;
    }

    @Override
    public void setMAster(boolean MAster) throws RemoteException{
        isMAster = MAster;
    }

    @Override
    public boolean isHAsended() throws RemoteException{
        return HAsended;
    }

    @Override
    public void setHAsended(boolean HAsended)throws RemoteException {
        this.HAsended = HAsended;
    }

    @Override
    public boolean isReady()throws RemoteException {
        return Ready;
    }

    @Override
    public void setReady(boolean ready) throws RemoteException{
        Ready = ready;
    }

    public FroggerGameRI getC()throws RemoteException {
        return c;
    }

    public void setC(FroggerGameRI c) throws RemoteException{
        this.c = c;
    }

    public ArrayList<Vect> getFrogposition()throws RemoteException {
        return Frogposition;
    }

    public void setFrogposition(ArrayList<Vect> frogposition)throws RemoteException {
        Frogposition = frogposition;
    }

    public ArrayList<Road_Line> getRoads() throws RemoteException{
        return roads;
    }

    public void setRoads(ArrayList<Road_Line> roads) throws RemoteException{
        this.roads = roads;
    }

    public UtilRI getUtil() throws RemoteException{
        return util;
    }

    public void setUtil(UtilRI util) throws RemoteException{
        this.util = util;
    }

    public ArrayList<Road_Line> getRiver()throws RemoteException {
        return river;
    }

    public void setRiver(ArrayList<Road_Line> river)throws RemoteException{
        this.river = river;
    }
    public boolean Ready_up() throws  RemoteException{
       return c.check_Ready2();

    }
    /*   private ArrayList<FroggerCollisionDetection> frogCol=new ArrayList<>();

    private ArrayList<Frogger> frog=new ArrayList<>();
    private WindGust wind;
    private HeatWave hwave;
    private GoalManager goalmanager;

    private AbstractBodyLayer<MovingEntity> movingObjectsLayer;
    private AbstractBodyLayer<MovingEntity> particleLayer;

    private MovingEntityFactory roadLine1;
    private MovingEntityFactory roadLine2;
    private MovingEntityFactory roadLine3;
    private MovingEntityFactory roadLine4;
    private MovingEntityFactory roadLine5;

    private MovingEntityFactory riverLine1;
    private MovingEntityFactory riverLine2;
    private MovingEntityFactory riverLine3;
    private MovingEntityFactory riverLine4;
    private MovingEntityFactory riverLine5;


    private int dig;
    static final int FROGGER_LIVES      = 5;
    static final int STARTING_LEVEL     = 1;
    static final int DEFAULT_LEVEL_TIME = 60;
    static final int GAME_INTRO        = 0;
    static final int GAME_PLAY         = 1;
    static final int GAME_FINISH_LEVEL = 2;
    static final int GAME_INSTRUCTIONS = 3;
    static final int GAME_OVER         = 4;

    protected int GameState = GAME_INTRO;
    protected int GameLevel = STARTING_LEVEL;

    public int GameLives    = FROGGER_LIVES;
    public int GameScore    = 0;

    public int levelTimer = DEFAULT_LEVEL_TIME;

    private boolean space_has_been_released = false;
    private boolean keyPressed = false;
    private boolean listenInput = true;




  /*  public boolean isReady() throws RemoteException{
        return Ready;
    }

    public void setReady(boolean ready)throws RemoteException {
        Ready = ready;
    }

    public ArrayList<FroggerCollisionDetection> getFrogCol()throws RemoteException {
        return frogCol;
    }

    public void setFrogCol(ArrayList<FroggerCollisionDetection> frogCol)throws RemoteException {
        this.frogCol = frogCol;
    }

    public boolean isHAsended()throws RemoteException {
        return HAsended;
    }

    public void setHAsended(boolean HAsended)throws RemoteException {
        this.HAsended = HAsended;
    }

    public FroggerGameRI getC()throws RemoteException {
        return c;
    }

    public void setC(FroggerGameRI c)throws RemoteException {
        this.c = c;
    }

    public ArrayList<Frogger> getFrog()throws RemoteException {
        return frog;
    }

    public void setFrog(ArrayList<Frogger> frog)throws RemoteException {
        this.frog = frog;
    }

    public WindGust getWind()throws RemoteException {
        return wind;
    }

    public void setWind(WindGust wind)throws RemoteException {
        this.wind = wind;
    }

    public HeatWave getHwave()throws RemoteException {
        return hwave;
    }

    public void setHwave(HeatWave hwave)throws RemoteException {
        this.hwave = hwave;
    }

    public GoalManager getGoalmanager()throws RemoteException {
        return goalmanager;
    }

    public void setGoalmanager(GoalManager goalmanager)throws RemoteException {
        this.goalmanager = goalmanager;
    }

    public Integer getRefe()throws RemoteException {
        return Refe;
    }

    public void setRefe(Integer refe)throws RemoteException {
        Refe = refe;
    }

    public AbstractBodyLayer<MovingEntity> getMovingObjectsLayer()throws RemoteException {
        return movingObjectsLayer;
    }

    public void setMovingObjectsLayer(AbstractBodyLayer<MovingEntity> movingObjectsLayer)throws RemoteException {
        this.movingObjectsLayer = movingObjectsLayer;
    }

    public AbstractBodyLayer<MovingEntity> getParticleLayer()throws RemoteException {
        return particleLayer;
    }

    public void setParticleLayer(AbstractBodyLayer<MovingEntity> particleLayer)throws RemoteException {
        this.particleLayer = particleLayer;
    }

    public MovingEntityFactory getRoadLine1()throws RemoteException {
        return roadLine1;
    }

    public void setRoadLine1(MovingEntityFactory roadLine1)throws RemoteException {
        this.roadLine1 = roadLine1;
    }

    public MovingEntityFactory getRoadLine2()throws RemoteException {
        return roadLine2;
    }

    public void setRoadLine2(MovingEntityFactory roadLine2)throws RemoteException {
        this.roadLine2 = roadLine2;
    }

    public MovingEntityFactory getRoadLine3()throws RemoteException {
        return roadLine3;
    }

    public void setRoadLine3(MovingEntityFactory roadLine3)throws RemoteException {
        this.roadLine3 = roadLine3;
    }

    public MovingEntityFactory getRoadLine4()throws RemoteException {
        return roadLine4;
    }

    public void setRoadLine4(MovingEntityFactory roadLine4)throws RemoteException {
        this.roadLine4 = roadLine4;
    }

    public MovingEntityFactory getRoadLine5()throws RemoteException {
        return roadLine5;
    }

    public void setRoadLine5(MovingEntityFactory roadLine5)throws RemoteException {
        this.roadLine5 = roadLine5;
    }

    public MovingEntityFactory getRiverLine1()throws RemoteException {
        return riverLine1;
    }

    public void setRiverLine1(MovingEntityFactory riverLine1)throws RemoteException {
        this.riverLine1 = riverLine1;
    }

    public MovingEntityFactory getRiverLine2()throws RemoteException {
        return riverLine2;
    }

    public void setRiverLine2(MovingEntityFactory riverLine2)throws RemoteException {
        this.riverLine2 = riverLine2;
    }

    public MovingEntityFactory getRiverLine3()throws RemoteException {
        return riverLine3;
    }

    public void setRiverLine3(MovingEntityFactory riverLine3)throws RemoteException {
        this.riverLine3 = riverLine3;
    }

    public MovingEntityFactory getRiverLine4()throws RemoteException {
        return riverLine4;
    }

    public void setRiverLine4(MovingEntityFactory riverLine4)throws RemoteException {
        this.riverLine4 = riverLine4;
    }

    public MovingEntityFactory getRiverLine5()throws RemoteException {
        return riverLine5;
    }

    public void setRiverLine5(MovingEntityFactory riverLine5)throws RemoteException {
        this.riverLine5 = riverLine5;
    }

    public int getDig()throws RemoteException {
        return dig;
    }

    public void setDig(int dig)throws RemoteException {
        this.dig = dig;
    }

    public int getGameState()throws RemoteException {
        return GameState;
    }

    public void setGameState(int gameState)throws RemoteException {
        GameState = gameState;
    }

    public int getGameLevel()throws RemoteException {
        return GameLevel;
    }

    public void setGameLevel(int gameLevel)throws RemoteException {
        GameLevel = gameLevel;
    }

    public int getGameLives()throws RemoteException {
        return GameLives;
    }

    public void setGameLives(int gameLives)throws RemoteException {
        GameLives = gameLives;
    }

    public int getGameScore()throws RemoteException {
        return GameScore;
    }

    public void setGameScore(int gameScore)throws RemoteException {
        GameScore = gameScore;
    }

    public int getLevelTimer()throws RemoteException {
        return levelTimer;
    }

    public void setLevelTimer(int levelTimer)throws RemoteException {
        this.levelTimer = levelTimer;
    }

    public boolean isSpace_has_been_released()throws RemoteException {
        return space_has_been_released;
    }

    public void setSpace_has_been_released(boolean space_has_been_released) throws RemoteException{
        this.space_has_been_released = space_has_been_released;
    }

    public boolean isKeyPressed()throws RemoteException {
        return keyPressed;
    }

    public void setKeyPressed(boolean keyPressed)throws RemoteException {
        this.keyPressed = keyPressed;
    }

    public boolean isListenInput()throws RemoteException {
        return listenInput;
    }

    public void setListenInput(boolean listenInput)throws RemoteException {
        this.listenInput = listenInput;
    }

    public boolean isMAster()throws RemoteException {
        return isMAster;
    }

    public void setMAster(boolean MAster)throws RemoteException {
        isMAster = MAster;
    }*/
    /*
	public ArrayList<AudioEfx> getAudiofx()throws RemoteException {
		return audiofx;
	}

	public void setAudiofx(ArrayList<AudioEfx> audiofx) throws RemoteException{
		this.audiofx = audiofx;
	}
	public FroggerUI getUi()throws RemoteException {
		return ui;
	}

	public void setUi(FroggerUI ui)throws RemoteException {
		this.ui = ui;
	}
	public ImageBackgroundLayer getBackgroundLayer()throws RemoteException {
		return backgroundLayer;
	}

	public void setBackgroundLayer(ImageBackgroundLayer backgroundLayer)throws RemoteException {
		this.backgroundLayer = backgroundLayer;
	}*/

}
