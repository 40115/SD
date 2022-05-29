package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GameStateRI extends Remote {
    boolean isReady() throws RemoteException;
    boolean isTerminated() throws RemoteException;
    public void setTerminated(boolean terminated)throws RemoteException;
    public void setReady(boolean ready)throws RemoteException;
    public boolean isHAsended()throws RemoteException;
    void setHAsended(boolean HAsended)throws RemoteException;
    boolean Ready_up() throws  RemoteException;
    public int getLevelTimer() throws RemoteException;
    public void setLevelTimer(int levelTimer)throws RemoteException;
    void sync_Timer(int type)throws RemoteException;
    public int getGameLives() throws RemoteException;
    public void setGameLives(int gameLives)throws RemoteException ;
    /*public FroggerGameRI getC()throws RemoteException;
    public void setC(FroggerGameRI c)throws RemoteException;*/

    /*public ArrayList<AudioEfx> getAudiofx()throws RemoteException;
    public void setAudiofx(ArrayList<AudioEfx> audiofx)throws RemoteException;*/
    /*public FroggerUI getUi()throws RemoteException;
    public void setUi(FroggerUI ui)throws RemoteException;*/
  /*  public WindGust getWind()throws RemoteException;
    public void setWind(WindGust wind)throws RemoteException;
    public HeatWave getHwave()throws RemoteException;
    public void setHwave(HeatWave hwave)throws RemoteException;
    public GoalManager getGoalmanager()throws RemoteException;
    public void setGoalmanager(GoalManager goalmanager)throws RemoteException;
    public Integer getRefe()throws RemoteException;
    public void setRefe(Integer refe)throws RemoteException;
    public AbstractBodyLayer<MovingEntity> getMovingObjectsLayer()throws RemoteException;
    public void setMovingObjectsLayer(AbstractBodyLayer<MovingEntity> movingObjectsLayer)throws RemoteException;
    public AbstractBodyLayer<MovingEntity> getParticleLayer()throws RemoteException;
    public void setParticleLayer(AbstractBodyLayer<MovingEntity> particleLayer)throws RemoteException;
    public MovingEntityFactory getRoadLine1()throws RemoteException;
    public void setRoadLine1(MovingEntityFactory roadLine1)throws RemoteException;
    public MovingEntityFactory getRoadLine2()throws RemoteException;
    public void setRoadLine2(MovingEntityFactory roadLine2)throws RemoteException;
    public MovingEntityFactory getRoadLine3()throws RemoteException;
    public void setRoadLine3(MovingEntityFactory roadLine3)throws RemoteException;
    public MovingEntityFactory getRoadLine4()throws RemoteException;
    public void setRoadLine4(MovingEntityFactory roadLine4)throws RemoteException;
    public MovingEntityFactory getRoadLine5()throws RemoteException;
    public void setRoadLine5(MovingEntityFactory roadLine5)throws RemoteException;
    public MovingEntityFactory getRiverLine1()throws RemoteException;
    public void setRiverLine1(MovingEntityFactory riverLine1)throws RemoteException;
    public MovingEntityFactory getRiverLine2()throws RemoteException;
    public void setRiverLine2(MovingEntityFactory riverLine2)throws RemoteException;
    public MovingEntityFactory getRiverLine3()throws RemoteException;
    public void setRiverLine3(MovingEntityFactory riverLine3)throws RemoteException;
    public MovingEntityFactory getRiverLine4()throws RemoteException;
    public void setRiverLine4(MovingEntityFactory riverLine4)throws RemoteException;
    public MovingEntityFactory getRiverLine5()throws RemoteException;
    public void setRiverLine5(MovingEntityFactory riverLine5)throws RemoteException;
    public FroggerGameRI getC()throws RemoteException;
    public void setC(FroggerGameRI c)throws RemoteException;
    public int getDig()throws RemoteException;
    public void setDig(int dig)throws RemoteException;
    public int getGameState()throws RemoteException;
    public void setGameState(int gameState)throws RemoteException;
    public int getGameLevel()throws RemoteException;
    public void setGameLevel(int gameLevel)throws RemoteException;
    public int getGameLives()throws RemoteException;
    public void setGameLives(int gameLives)throws RemoteException;
    public int getGameScore()throws RemoteException;
    public void setGameScore(int gameScore)throws RemoteException;
    public int getLevelTimer()throws RemoteException;
    public void setLevelTimer(int levelTimer)throws RemoteException;
    public boolean isSpace_has_been_released()throws RemoteException;
    public void setSpace_has_been_released(boolean space_has_been_released)throws RemoteException;
    public boolean isKeyPressed()throws RemoteException;
    public void setKeyPressed(boolean keyPressed)throws RemoteException;
    public boolean isListenInput()throws RemoteException;
    public void setListenInput(boolean listenInput)throws RemoteException;*/
    FroggerGameRI getC()throws RemoteException;
    void setC(FroggerGameRI c) throws RemoteException;

    boolean isMAster()throws RemoteException;
    void setMAster(boolean MAster) throws RemoteException;
    int getRefe() throws RemoteException;
    int getDig()throws RemoteException;
    void setDig(int dig) throws RemoteException;
    void sync_Frogger(Vect vect,int Refe)throws RemoteException;
    void sync_Road_Line(int type, int nriverline)throws RemoteException;
    public void setIsDead(ArrayList<Integer> isDead) throws RemoteException;
    public ArrayList<Integer> getIsDead()throws RemoteException;
    void setUtil(UtilRI util) throws RemoteException;
    UtilRI getUtil() throws RemoteException;
    void setRefe(int refe)throws RemoteException;
    ArrayList<Vect> getFrogposition()throws RemoteException;
    void setFrogposition(ArrayList<Vect> frogposition)throws RemoteException;
    ArrayList<Road_Line> getRoads() throws RemoteException;
    void setRoads(ArrayList<Road_Line> roads) throws RemoteException;
    void setRiver(ArrayList<Road_Line> river)throws RemoteException;
    ArrayList<Road_Line> getRiver()throws RemoteException;
    void sync_River_Line(int type, int nriverline)throws RemoteException;

    public void FroggerSDie(int Ref,int Re) throws RemoteException;


}
