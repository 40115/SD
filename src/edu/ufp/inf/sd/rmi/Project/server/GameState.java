package edu.ufp.inf.sd.rmi.Project.server;

import edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.*;
import edu.ufp.inf.sd.rmi.Project.client.ProjectClientRI;
import jig.engine.hli.ImageBackgroundLayer;
import jig.engine.physics.AbstractBodyLayer;
import jig.engine.util.Vector2D;

import java.util.ArrayList;

public class GameState {
    static final int WORLD_WIDTH = (13*32);
    static final int WORLD_HEIGHT = (14*32);
    static final Vector2D FROGGER_START = new Vector2D(6*32,WORLD_HEIGHT-32);

    static final String RSC_PATH = "edu/ufp/inf/sd/rmi/Project/client/FroggerGame/src/resources/";
    static final String SPRITE_SHEET = RSC_PATH + "frogger_sprites.png";

    static final int FROGGER_LIVES      = 5;
    static final int STARTING_LEVEL     = 1;
    static final int DEFAULT_LEVEL_TIME = 60;

    private ArrayList<FroggerCollisionDetection> frogCol=new ArrayList<>();
    private ProjectClientRI CLientReference;
    private ArrayList<Frogger> frog=new ArrayList<>();
    private ArrayList<AudioEfx> audiofx=new ArrayList<>();
    private FroggerUI ui;
    private WindGust wind;
    private HeatWave hwave;
    private GoalManager goalmanager;
    private Integer Refe;
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

    private ImageBackgroundLayer backgroundLayer;
    private int dig;
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

    public ArrayList<FroggerCollisionDetection> getFrogCol() {
        return frogCol;
    }

    public void setFrogCol(ArrayList<FroggerCollisionDetection> frogCol) {
        this.frogCol = frogCol;
    }

    public ProjectClientRI getCLientReference() {
        return CLientReference;
    }

    public void setCLientReference(ProjectClientRI CLientReference) {
        this.CLientReference = CLientReference;
    }

    public ArrayList<Frogger> getFrog() {
        return frog;
    }

    public void setFrog(ArrayList<Frogger> frog) {
        this.frog = frog;
    }

    public ArrayList<AudioEfx> getAudiofx() {
        return audiofx;
    }

    public void setAudiofx(ArrayList<AudioEfx> audiofx) {
        this.audiofx = audiofx;
    }

    public FroggerUI getUi() {
        return ui;
    }

    public void setUi(FroggerUI ui) {
        this.ui = ui;
    }

    public WindGust getWind() {
        return wind;
    }

    public void setWind(WindGust wind) {
        this.wind = wind;
    }

    public HeatWave getHwave() {
        return hwave;
    }

    public void setHwave(HeatWave hwave) {
        this.hwave = hwave;
    }

    public GoalManager getGoalmanager() {
        return goalmanager;
    }

    public void setGoalmanager(GoalManager goalmanager) {
        this.goalmanager = goalmanager;
    }

    public Integer getRefe() {
        return Refe;
    }

    public void setRefe(Integer refe) {
        Refe = refe;
    }

    public AbstractBodyLayer<MovingEntity> getMovingObjectsLayer() {
        return movingObjectsLayer;
    }

    public void setMovingObjectsLayer(AbstractBodyLayer<MovingEntity> movingObjectsLayer) {
        this.movingObjectsLayer = movingObjectsLayer;
    }

    public AbstractBodyLayer<MovingEntity> getParticleLayer() {
        return particleLayer;
    }

    public void setParticleLayer(AbstractBodyLayer<MovingEntity> particleLayer) {
        this.particleLayer = particleLayer;
    }

    public MovingEntityFactory getRoadLine1() {
        return roadLine1;
    }

    public void setRoadLine1(MovingEntityFactory roadLine1) {
        this.roadLine1 = roadLine1;
    }

    public MovingEntityFactory getRoadLine2() {
        return roadLine2;
    }

    public void setRoadLine2(MovingEntityFactory roadLine2) {
        this.roadLine2 = roadLine2;
    }

    public MovingEntityFactory getRoadLine3() {
        return roadLine3;
    }

    public void setRoadLine3(MovingEntityFactory roadLine3) {
        this.roadLine3 = roadLine3;
    }

    public MovingEntityFactory getRoadLine4() {
        return roadLine4;
    }

    public void setRoadLine4(MovingEntityFactory roadLine4) {
        this.roadLine4 = roadLine4;
    }

    public MovingEntityFactory getRoadLine5() {
        return roadLine5;
    }

    public void setRoadLine5(MovingEntityFactory roadLine5) {
        this.roadLine5 = roadLine5;
    }

    public MovingEntityFactory getRiverLine1() {
        return riverLine1;
    }

    public void setRiverLine1(MovingEntityFactory riverLine1) {
        this.riverLine1 = riverLine1;
    }

    public MovingEntityFactory getRiverLine2() {
        return riverLine2;
    }

    public void setRiverLine2(MovingEntityFactory riverLine2) {
        this.riverLine2 = riverLine2;
    }

    public MovingEntityFactory getRiverLine3() {
        return riverLine3;
    }

    public void setRiverLine3(MovingEntityFactory riverLine3) {
        this.riverLine3 = riverLine3;
    }

    public MovingEntityFactory getRiverLine4() {
        return riverLine4;
    }

    public void setRiverLine4(MovingEntityFactory riverLine4) {
        this.riverLine4 = riverLine4;
    }

    public MovingEntityFactory getRiverLine5() {
        return riverLine5;
    }

    public void setRiverLine5(MovingEntityFactory riverLine5) {
        this.riverLine5 = riverLine5;
    }

    public ImageBackgroundLayer getBackgroundLayer() {
        return backgroundLayer;
    }

    public void setBackgroundLayer(ImageBackgroundLayer backgroundLayer) {
        this.backgroundLayer = backgroundLayer;
    }

    public int getDig() {
        return dig;
    }

    public void setDig(int dig) {
        this.dig = dig;
    }

    public int getGameState() {
        return GameState;
    }

    public void setGameState(int gameState) {
        GameState = gameState;
    }

    public int getGameLevel() {
        return GameLevel;
    }

    public void setGameLevel(int gameLevel) {
        GameLevel = gameLevel;
    }

    public int getGameLives() {
        return GameLives;
    }

    public void setGameLives(int gameLives) {
        GameLives = gameLives;
    }

    public int getGameScore() {
        return GameScore;
    }

    public void setGameScore(int gameScore) {
        GameScore = gameScore;
    }

    public int getLevelTimer() {
        return levelTimer;
    }

    public void setLevelTimer(int levelTimer) {
        this.levelTimer = levelTimer;
    }

    public boolean isSpace_has_been_released() {
        return space_has_been_released;
    }

    public void setSpace_has_been_released(boolean space_has_been_released) {
        this.space_has_been_released = space_has_been_released;
    }

    public boolean isKeyPressed() {
        return keyPressed;
    }

    public void setKeyPressed(boolean keyPressed) {
        this.keyPressed = keyPressed;
    }

    public boolean isListenInput() {
        return listenInput;
    }

    public void setListenInput(boolean listenInput) {
        this.listenInput = listenInput;
    }
}
