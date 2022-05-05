/**
 * Copyright (c) 2009 Vitaliy Pavlenko
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;


import edu.ufp.inf.sd.rmi.Project.server.FroggerGameRI;
import edu.ufp.inf.sd.rmi.Project.server.GameStateRI;
import edu.ufp.inf.sd.rmi.Project.server.Vect;
import jig.engine.ImageResource;
import jig.engine.PaintableCanvas;
import jig.engine.RenderingContext;
import jig.engine.ResourceFactory;
import jig.engine.PaintableCanvas.JIGSHAPE;
import jig.engine.hli.ImageBackgroundLayer;
import jig.engine.hli.StaticScreenGame;
import jig.engine.physics.AbstractBodyLayer;
import jig.engine.util.Vector2D;

public class Main extends StaticScreenGame {
	static final int WORLD_WIDTH = (13*32);
	static final int WORLD_HEIGHT = (14*32);
	static final Vector2D FROGGER_START = new Vector2D(6*32,WORLD_HEIGHT-32);

	static final String RSC_PATH = "edu/ufp/inf/sd/rmi/Project/client/FroggerGame/src/resources/";

	static final int FROGGER_LIVES      = 5;
	static final int STARTING_LEVEL     = 1;
	static final int DEFAULT_LEVEL_TIME = 60;
	static final int GAME_PLAY         = 1;
	static final int GAME_FINISH_LEVEL = 2;
	static final int GAME_INSTRUCTIONS = 3;
	static final int GAME_OVER         = 4;
	static final int GAME_INTRO        = 0;
	public GameStateRI vd;
	ArrayList<Frogger>Frog=new ArrayList<>();
	ArrayList<FroggerCollisionDetection> frogCol=new ArrayList<>();
	private ArrayList<AudioEfx> audiofx=new ArrayList<>();
	private FroggerUI ui;
	static  ArrayList<String> SPRITE_SHEET =new ArrayList<>() ;
	private ImageBackgroundLayer backgroundLayer;

	private WindGust wind;
	private HeatWave hwave;
	private GoalManager goalmanager;
	private int Refe;
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

	protected int GameState = GAME_INTRO;
	protected int GameLevel = STARTING_LEVEL;

	public int GameLives    = FROGGER_LIVES;
	public int GameScore    = 0;

	public int levelTimer = DEFAULT_LEVEL_TIME;

	private boolean space_has_been_released = false;
	private boolean keyPressed = false;
	private boolean listenInput = true;


	/**
	 * Initialize game objects
	 */
	public Main (String[] args,GameStateRI j) throws RemoteException {

		super(WORLD_WIDTH, WORLD_HEIGHT, false);
		vd=j;

Refe=vd.getRefe();
		gameframe.setTitle("Frogger");
		ResourceFactory.getFactory().loadResources(RSC_PATH, "resources.xml");
		for (int i = 1; i <=vd.getC().getUtils().size() ; i++) {
			SPRITE_SHEET.add(RSC_PATH+"frogger_sprites"+(i)+".png");
		}
		ImageResource bkg = ResourceFactory.getFactory().getFrames(SPRITE_SHEET.get(0) + "#background").get(0);
		backgroundLayer=(new ImageBackgroundLayer(bkg, WORLD_WIDTH, WORLD_HEIGHT, ImageBackgroundLayer.TILE_IMAGE));

		// Used in CollisionObject, basically 2 different collision spheres
		// 30x30 is a large sphere (sphere that fits inside a 30x30 pixel rectangle)
		//  4x4 is a tiny sphere
		PaintableCanvas.loadDefaultFrames("col", 30, 30, 2, JIGSHAPE.RECTANGLE, null);
		PaintableCanvas.loadDefaultFrames("colSmall", 4, 4, 2, JIGSHAPE.RECTANGLE, null);
      dig=vd.getDig();

		try {
			for (int i = 0; i < vd.getC().getUtils().size(); i++) {
				Frog.add(new Frogger(this,i));
				frogCol.add(i,new FroggerCollisionDetection(Frog.get(i)));
				audiofx.add(new AudioEfx(frogCol.get(i),Frog.get(i)));
			}
		}catch (RemoteException e){
			System.out.println(e);
		}

		ui=new FroggerUI(this);
		wind=new WindGust();
		hwave=new HeatWave();
		goalmanager=new GoalManager();
		movingObjectsLayer= new AbstractBodyLayer.IterativeUpdate<>();
		particleLayer= new AbstractBodyLayer.IterativeUpdate<>();

		initializeLevel(1);
	}


	public void initializeLevel(int level) throws RemoteException {

		/* dV is the velocity multiplier for all moving objects at the current game level */
		double dV = level*0.05 + 1+dig;
		movingObjectsLayer.clear();

		/* River Traffic */
riverLine1= new MovingEntityFactory(new Vector2D(-(32*3),2*32),
				new Vector2D(0.06*dV,0));
riverLine2=new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH,3*32),
		new Vector2D(-0.04*dV,0));

		riverLine3=new MovingEntityFactory(new Vector2D(-(32*3),4*32),
				new Vector2D(0.09*dV,0));

		riverLine4=new MovingEntityFactory(new Vector2D(-(32*4),5*32),
				new Vector2D(0.045*dV,0));

		riverLine5=new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH,6*32),
				new Vector2D(-0.045*dV,0));

		/* Road Traffic */
		roadLine1=new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH, 8*32),
				new Vector2D(-0.1*dV, 0));

		roadLine2=new MovingEntityFactory(new Vector2D(-(32*4), 9*32),
				new Vector2D(0.08*dV, 0));

		roadLine3=new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH, 10*32),
				new Vector2D(-0.12*dV, 0));

		roadLine4=new MovingEntityFactory(new Vector2D(-(32*4), 11*32),
				new Vector2D(0.075*dV, 0));

		roadLine5=new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH, 12*32),
				new Vector2D(-0.05*dV, 0)) ;

		goalmanager.init(level);
		for (Goal g : goalmanager.get()) {
			movingObjectsLayer.add(g);
		}

		/* Build some traffic before game starts buy running MovingEntityFactories for fews cycles */
		for (int i=0; i<500; i++)
			cycleTraffic(10);
	}


	/**
	 * Populate movingObjectLayer with a cycle of cars/trucks, moving tree logs, etc
	 *
	 */
	public void cycleTraffic(long deltaMs) throws RemoteException {

		MovingEntity m;
		/* Road traffic updates */

		roadLine1.update(deltaMs);
		if (this.vd.isMAster() ) {
			if ((m = roadLine1.buildVehicle()) != null){
				movingObjectsLayer.add(m);
System.out.println(m.getClass().getName());
				switch (m.getClass().getName()) {
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.Car":
						vd.sync_Road_Line(0, 0);
						vd.getC().update_the_game(vd,0);
						break;
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.Truck":

						vd.sync_Road_Line(1, 0);
						vd.getC().update_the_game(vd,1);
						break;
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.CopCar":

						vd.sync_Road_Line(2, 0);
						vd.getC().update_the_game(vd,2);
						break;
					default:
						vd.sync_Road_Line(-1, 0);
						vd.getC().update_the_game(vd,-1);
						break;
				}

			}
		}else {


	m = roadLine1.GetVehicle(vd.getRoads().get(0));

vd.getC().update_the_game2(vd.getUtil(),1);
	if (m != null) {
		movingObjectsLayer.add(m);

}
		}

		roadLine2.update(deltaMs);
		if (this.vd.isMAster() ) {
			if ((m = roadLine2.buildVehicle()) != null){
				movingObjectsLayer.add(m);
				switch (m.getClass().getName()) {
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.Car":
						vd.sync_Road_Line(0, 1);
						vd.getC().update_the_game(vd, 0);
						break;
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.Truck":

						vd.sync_Road_Line(1, 1);

						vd.getC().update_the_game(vd, 1);
						break;
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.CopCar":
						vd.sync_Road_Line(2, 1);
						vd.getC().update_the_game(vd, 2);

						break;
					default:
						vd.sync_Road_Line(-1, 1);
						vd.getC().update_the_game(vd,-1);
						break;

				}
			}
		}else {

				m = roadLine2.GetVehicle(vd.getRoads().get(1));
				vd.getC().update_the_game2(vd.getUtil(),1);
				if (m != null) {

					movingObjectsLayer.add(m);
				}

		}



		roadLine3.update(deltaMs);
		if (this.vd.isMAster() ) {
			if ((m = roadLine3.buildVehicle()) != null){
				movingObjectsLayer.add(m);
				switch (m.getClass().getName()) {
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.Car":
						vd.sync_Road_Line(0, 2);
						vd.getC().update_the_game(vd, 0);
						break;
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.Truck":

						vd.sync_Road_Line(1, 2);

						vd.getC().update_the_game(vd, 1);
						break;
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.CopCar":
						vd.sync_Road_Line(2, 2);
						vd.getC().update_the_game(vd, 2);

						break;
					default:
						vd.sync_Road_Line(-1, 2);
						vd.getC().update_the_game(vd,-1);
						break;

				}
			}
		}else {

				m = roadLine3.GetVehicle(vd.getRoads().get(2));
				vd.getC().update_the_game2(vd.getUtil(),2);
				if (m != null) {

					movingObjectsLayer.add(m);
				}

		}
		roadLine4.update(deltaMs);
		if (this.vd.isMAster() ) {
			if ((m = roadLine4.buildVehicle()) != null){
				movingObjectsLayer.add(m);
				switch (m.getClass().getName()) {
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.Car":
						vd.sync_Road_Line(0, 3);
						vd.getC().update_the_game(vd, 0);
						break;
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.Truck":

						vd.sync_Road_Line(1, 3);

						vd.getC().update_the_game(vd, 1);
						break;
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.CopCar":
						vd.sync_Road_Line(2, 3);
						vd.getC().update_the_game(vd, 2);

						break;
					default:
						vd.sync_Road_Line(-1, 3);
						vd.getC().update_the_game(vd,-1);
						break;

				}
			}
		}else {

			m = roadLine4.GetVehicle(vd.getRoads().get(3));
			vd.getC().update_the_game2(vd.getUtil(),3);
			if (m != null) {

				movingObjectsLayer.add(m);
			}

		}
		roadLine5.update(deltaMs);
		if (this.vd.isMAster() ) {
			if ((m = roadLine5.buildVehicle()) != null){
				movingObjectsLayer.add(m);
				switch (m.getClass().getName()) {
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.Car":
						vd.sync_Road_Line(0, 4);
						vd.getC().update_the_game(vd, 0);
						break;
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.Truck":

						vd.sync_Road_Line(1, 4);

						vd.getC().update_the_game(vd, 1);
						break;
					case "edu.ufp.inf.sd.rmi.Project.client.FroggerGame.src.frogger.CopCar":
						vd.sync_Road_Line(2, 4);
						vd.getC().update_the_game(vd, 2);

						break;
					default:
						vd.sync_Road_Line(-1, 4);
						vd.getC().update_the_game(vd,-1);
						break;

				}
			}
		}else {

			m = roadLine5.GetVehicle(vd.getRoads().get(4));
			vd.getC().update_the_game2(vd.getUtil(),4);
			if (m != null) {

				movingObjectsLayer.add(m);
			}

		}

		/* River traffic updates */
		riverLine1.update(deltaMs);
		if (this.vd.isMAster() ){
			if ((m = riverLine1.buildShortLogWithTurtles(40)) != null) movingObjectsLayer.add(m);
		}
		riverLine2.update(deltaMs);
		if (this.vd.isMAster()) {
			if ((m = riverLine2.buildLongLogWithCrocodile(30)) != null)movingObjectsLayer.add(m);
		}
		riverLine3.update(deltaMs);
		if (this.vd.isMAster() ) {
			if ((m = riverLine3.buildShortLogWithTurtles(50)) != null) movingObjectsLayer.add(m);
		}

		riverLine4.update(deltaMs);
		if (this.vd.isMAster() ) {
			if ((m = riverLine4.buildLongLogWithCrocodile(20)) != null) movingObjectsLayer.add(m);
		}
		riverLine5.update(deltaMs);
		if (this.vd.isMAster() ) {
			if ((m =riverLine5.buildShortLogWithTurtles(10)) != null) movingObjectsLayer.add(m);
		}
		// Do Wind
		if ((m = wind.genParticles(GameLevel)) != null) particleLayer.add(m);

		// HeatWave
		for (int i = 0; i < Frog.size(); i++) {
			if ((m = hwave.genParticles(Frog.get(i).getCenterPosition())) != null)
				particleLayer.add(m);
		}
		movingObjectsLayer.update(deltaMs);
		particleLayer.update(deltaMs);



	}

	/**
	 * Handling Frogger movement from keyboard input
	 */
	public void froggerKeyboardHandler() throws RemoteException {
		keyboard.poll();
		boolean keyReleased = false;
		boolean downPressed = keyboard.isPressed(KeyEvent.VK_DOWN);
		boolean upPressed = keyboard.isPressed(KeyEvent.VK_UP);
		boolean leftPressed = keyboard.isPressed(KeyEvent.VK_LEFT);
		boolean rightPressed = keyboard.isPressed(KeyEvent.VK_RIGHT);

		// Enable/Disable cheating
		if (keyboard.isPressed(KeyEvent.VK_C))

			for (int i = 0; i <Frog.size() ; i++) {
				Frog.get(i).cheating = true;
			}
		if (keyboard.isPressed(KeyEvent.VK_V))
			for (int i = 0; i <Frog.size() ; i++) {
				Frog.get(i).cheating = false;
			}
		if (keyboard.isPressed(KeyEvent.VK_0)) {
			GameLevel=10;
			initializeLevel(GameLevel);
		}


		/*
		 * This logic checks for key strokes.
		 * It registers a key press, and ignores all other key strokes
		 * until the first key has been released
		 */
		if (downPressed || upPressed || leftPressed || rightPressed)
			keyPressed=true;
		else
			keyReleased = true;

		if (listenInput) {
			if (downPressed) Frog.get(vd.getRefe()).moveDown();
			if (upPressed) Frog.get(vd.getRefe()).moveUp();
			if (leftPressed) Frog.get(vd.getRefe()).moveLeft();
			if (rightPressed) Frog.get(vd.getRefe()).moveRight();

			if (keyPressed)
				listenInput=false;
		}

		if (keyReleased) {
			listenInput=true;
			keyPressed=false;
		}

		if (keyboard.isPressed(KeyEvent.VK_ESCAPE))
			GameState=GAME_INTRO;
	}

	/**
	 * Handle keyboard events while at the game intro menu
	 */
	public void menuKeyboardHandler() throws RemoteException {
		keyboard.poll();

		// Following 2 if statements allow capture space bar key strokes
		if (!keyboard.isPressed(KeyEvent.VK_SPACE)) {
			space_has_been_released = true;

		}

		if (!space_has_been_released)
			return;

		if (keyboard.isPressed(KeyEvent.VK_SPACE)) {
			switch (GameState) {
				case GAME_INSTRUCTIONS:
				case GAME_OVER:
					GameState = GAME_INTRO;
					space_has_been_released = false;
					break;
				default:
					GameLives = FROGGER_LIVES;
					GameScore = 0;
					GameLevel = STARTING_LEVEL;
					levelTimer = DEFAULT_LEVEL_TIME;
					for (int i = 0; i <Frog.size() ; i++) {
						Frog.get(i).setPosition(FROGGER_START);
						audiofx.get(i).playGameMusic();
					}

					GameState = GAME_PLAY;

					initializeLevel(GameLevel);
			}
		}
		if (keyboard.isPressed(KeyEvent.VK_H))
			GameState = GAME_INSTRUCTIONS;
	}

	/**
	 * Handle keyboard when finished a level
	 */
	public void finishLevelKeyboardHandler() throws RemoteException {
		keyboard.poll();
		if (keyboard.isPressed(KeyEvent.VK_SPACE)) {
		GameState=GAME_PLAY;

			audiofx.get(vd.getRefe()).playGameMusic();
			initializeLevel(GameLevel+vd.getDig());
		}
	}


	/**
	 * w00t
	 */
	public void update(long deltaMs) {

		switch (GameState) {
			case GAME_PLAY:
				try {
					froggerKeyboardHandler();
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
				wind.update(deltaMs);
				hwave.update(deltaMs);
				try {
					Frog.get(vd.getRefe()).update(deltaMs);
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
				try {
					audiofx.get(vd.getRefe()).update(deltaMs);
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}

					ui.update(deltaMs);


				try {
					cycleTraffic(deltaMs);
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
				try {
					frogCol.get(vd.getRefe()).testCollision(movingObjectsLayer);
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}

				// Wind gusts work only when Frogger is on the river
				for (int i = 0; i <Frog.size() ; i++) {
					if (frogCol.get(i).isInRiver()) {
						wind.start(GameLevel);
					}
					wind.perform(Frog.get(i),GameLevel, deltaMs);
					if (frogCol.get(i).isOnRoad())
						hwave.start(Frog.get(i), GameLevel);

					hwave.perform(Frog.get(i), deltaMs, GameLevel);
					if (!Frog.get(i).isAlive)
						particleLayer.clear();
					goalmanager.update(deltaMs);

					if (goalmanager.getUnreached().size() == 0) {
						GameState=GAME_FINISH_LEVEL;
						audiofx.get(i).playCompleteLevel();
					particleLayer.clear();
					}
					if (GameLives < 1) {
						GameState=GAME_OVER;
					}
				}



				// Do the heat wave only when Frogger is on hot pavement


break;
			case GAME_OVER:
			case GAME_INSTRUCTIONS:
			case GAME_INTRO:
				goalmanager.update(deltaMs);
				try {
					menuKeyboardHandler();
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
				try {
					cycleTraffic(deltaMs);
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
				break;

			case GAME_FINISH_LEVEL:
				try {
					finishLevelKeyboardHandler();
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
				break;
		}

	}
	/**
	 * Rendering game objects
	 */
	public void render(RenderingContext rc) {
		switch (GameState) {
			case GAME_FINISH_LEVEL:
			case GAME_PLAY:

backgroundLayer.render(rc);

				for (int i = 0; i < Frog.size(); i++) {
					if (Frog.get(i).isAlive) {
					movingObjectsLayer.render(rc);
						//frog.collisionObjects.get(0).render(rc);
						Frog.get(i).render(rc);
					} else {
						Frog.get(i).render(rc);
						movingObjectsLayer.render(rc);
					}
				}
				particleLayer.render(rc);

				ui.render(rc);
break;

			case GAME_OVER:
			case GAME_INSTRUCTIONS:
			case GAME_INTRO:
					backgroundLayer.render(rc);
			    	movingObjectsLayer.render(rc);
					ui.render(rc);
				break;
		}
	}

	public static void main (String[] args, GameStateRI j ) throws RemoteException {
		Main f = new Main(args,j);
		f.run();
	}

}