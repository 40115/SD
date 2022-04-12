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
import java.util.ArrayList;

import edu.ufp.inf.sd.rmi.Project.client.ProjectClientRI;
import edu.ufp.inf.sd.rmi.Project.server.FroggerGame;
import edu.ufp.inf.sd.rmi.Project.server.GameState;
import jig.engine.ImageResource;
import jig.engine.PaintableCanvas;
import jig.engine.RenderingContext;
import jig.engine.ResourceFactory;
import jig.engine.PaintableCanvas.JIGSHAPE;
import jig.engine.hli.ImageBackgroundLayer;
import jig.engine.hli.StaticScreenGame;
import jig.engine.physics.AbstractBodyLayer;
import jig.engine.util.Vector2D;

import static edu.ufp.inf.sd.rmi.Project.server.GameState.*;

public class Main extends StaticScreenGame {
	static final int WORLD_WIDTH = (13*32);
	static final int WORLD_HEIGHT = (14*32);
	static final Vector2D FROGGER_START = new Vector2D(6*32,WORLD_HEIGHT-32);

	static final String RSC_PATH = "edu/ufp/inf/sd/rmi/Project/client/FroggerGame/src/resources/";
	static final String SPRITE_SHEET = RSC_PATH + "frogger_sprites.png";
	static final int FROGGER_LIVES      = 5;
	static final int STARTING_LEVEL     = 1;
	static final int DEFAULT_LEVEL_TIME = 60;
	static final int GAME_PLAY         = 1;
	static final int GAME_FINISH_LEVEL = 2;
	static final int GAME_INSTRUCTIONS = 3;
	static final int GAME_OVER         = 4;
	static final int GAME_INTRO        = 0;
   public GameState vd;
    /**
	 * Initialize game objects
	 */
	public Main (FroggerGame game,GameState k ) {
		
		super(WORLD_WIDTH, WORLD_HEIGHT, false);
		
		gameframe.setTitle("Frogger");
		
		ResourceFactory.getFactory().loadResources(RSC_PATH, "resources.xml");

		ImageResource bkg = ResourceFactory.getFactory().getFrames(
				SPRITE_SHEET + "#background").get(0);
		vd.setBackgroundLayer(new ImageBackgroundLayer(bkg, WORLD_WIDTH,
				WORLD_HEIGHT, ImageBackgroundLayer.TILE_IMAGE));
		
		// Used in CollisionObject, basically 2 different collision spheres
		// 30x30 is a large sphere (sphere that fits inside a 30x30 pixel rectangle)
		//  4x4 is a tiny sphere
		PaintableCanvas.loadDefaultFrames("col", 30, 30, 2, JIGSHAPE.RECTANGLE, null);
		PaintableCanvas.loadDefaultFrames("colSmall", 4, 4, 2, JIGSHAPE.RECTANGLE, null);
		int n= game.getUtils().size();
		for (int i = 0; i <n ; i++) {
			vd.getFrog().add(new Frogger(this));
			vd.getFrogCol().add(new FroggerCollisionDetection(vd.getFrog().get(i)));
			vd.getAudiofx().add(new AudioEfx(vd.getFrogCol().get(i),vd.getFrog().get(i)));
		}

vd.setRefe(game.getN());
		vd.setUi(new FroggerUI(this));
		vd.setWind(new WindGust());
		vd.setHwave(new HeatWave());
		vd.setGoalmanager(new GoalManager());
		vd.setMovingObjectsLayer(new AbstractBodyLayer.IterativeUpdate<MovingEntity>());
	vd.setParticleLayer(new AbstractBodyLayer.IterativeUpdate<MovingEntity>());
	vd.setDig(Integer.parseInt(game.getDific()));
		initializeLevel(1+vd.getDig());
	}


	public void initializeLevel(int level) {

		/* dV is the velocity multiplier for all moving objects at the current game level */
		double dV = level*0.05 + 1;
		vd.getMovingObjectsLayer().clear();

		/* River Traffic */
		vd.setRiverLine1(new MovingEntityFactory(new Vector2D(-(32*3),2*32),
				new Vector2D(0.06*dV,0)));

		vd.setRiverLine2(new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH,3*32),
				new Vector2D(-0.04*dV,0)));

		vd.setRiverLine3(new MovingEntityFactory(new Vector2D(-(32*3),4*32),
				new Vector2D(0.09*dV,0)));

		vd.setRiverLine4(new MovingEntityFactory(new Vector2D(-(32*4),5*32),
				new Vector2D(0.045*dV,0))) ;

		vd.setRiverLine5(new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH,6*32),
				new Vector2D(-0.045*dV,0)));
		
		/* Road Traffic */
		vd.setRoadLine1(new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH, 8*32),
				new Vector2D(-0.1*dV, 0)));

		vd.setRoadLine2(new MovingEntityFactory(new Vector2D(-(32*4), 9*32),
				new Vector2D(0.08*dV, 0)));
		
		vd.setRiverLine3(new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH, 10*32),
				new Vector2D(-0.12*dV, 0)));

		vd.setRiverLine4(new MovingEntityFactory(new Vector2D(-(32*4), 11*32),
				new Vector2D(0.075*dV, 0)));

		vd.setRiverLine5(new MovingEntityFactory(new Vector2D(Main.WORLD_WIDTH, 12*32),
				new Vector2D(-0.05*dV, 0))) ;
		
		vd.getGoalmanager().init(level);
		for (Goal g : vd.getGoalmanager().get()) {
			vd.getMovingObjectsLayer().add(g);
		}
			
		/* Build some traffic before game starts buy running MovingEntityFactories for fews cycles */
		for (int i=0; i<500; i++)
			cycleTraffic(10);
	}
	
	
	/**
	 * Populate movingObjectLayer with a cycle of cars/trucks, moving tree logs, etc
	 * 
	 * @param deltaMs
	 */
	public void cycleTraffic(long deltaMs) {
		MovingEntity m;
		/* Road traffic updates */
		vd.getRoadLine1().update(deltaMs);
	    if ((m = vd.getRoadLine1().buildVehicle()) != null) vd.getMovingObjectsLayer().add(m);

		vd.getRoadLine2().update(deltaMs);
	    if ((m = vd.getRoadLine2().buildVehicle()) != null) vd.getMovingObjectsLayer().add(m);

		vd.getRoadLine3().update(deltaMs);
	    if ((m = vd.getRoadLine3().buildVehicle()) != null) vd.getMovingObjectsLayer().add(m);

		vd.getRoadLine4().update(deltaMs);
	    if ((m = vd.getRoadLine4().buildVehicle()) != null) vd.getMovingObjectsLayer().add(m);

		vd.getRoadLine5().update(deltaMs);
	    if ((m = vd.getRoadLine5().buildVehicle()) != null) vd.getMovingObjectsLayer().add(m);
	    
		
		/* River traffic updates */
		vd.getRiverLine1().update(deltaMs);
	    if ((m = vd.getRiverLine1().buildShortLogWithTurtles(40)) != null) vd.getMovingObjectsLayer().add(m);

		vd.getRiverLine2().update(deltaMs);
	    if ((m = vd.getRiverLine2().buildLongLogWithCrocodile(30)) != null) vd.getMovingObjectsLayer().add(m);

		vd.getRiverLine3().update(deltaMs);
	    if ((m = vd.getRiverLine3().buildShortLogWithTurtles(50)) != null) vd.getMovingObjectsLayer().add(m);

		vd.getRiverLine4().update(deltaMs);
	    if ((m = vd.getRiverLine4().buildLongLogWithCrocodile(20)) != null) vd.getMovingObjectsLayer().add(m);

		vd.getRiverLine5().update(deltaMs);
	    if ((m = vd.getRiverLine5().buildShortLogWithTurtles(10)) != null) vd.getMovingObjectsLayer().add(m);
	    
	    // Do Wind
	    if ((m = vd.getWind().genParticles(vd.getGameLevel())) != null) vd.getParticleLayer().add(m);
	    
	    // HeatWave
		for (int i = 0; i <vd.getFrog().size() ; i++) {
			if ((m = vd.getHwave().genParticles(vd.getFrog().get(i).getCenterPosition())) != null) vd.getParticleLayer().add(m);
		}

	    vd.getMovingObjectsLayer().update(deltaMs);
	    vd.getParticleLayer().update(deltaMs);
	}
	
	/**
	 * Handling Frogger movement from keyboard input
	 */
	public void froggerKeyboardHandler() {
 		keyboard.poll();
		
 		boolean keyReleased = false;
        boolean downPressed = keyboard.isPressed(KeyEvent.VK_DOWN);
        boolean upPressed = keyboard.isPressed(KeyEvent.VK_UP);
		boolean leftPressed = keyboard.isPressed(KeyEvent.VK_LEFT);
		boolean rightPressed = keyboard.isPressed(KeyEvent.VK_RIGHT);
		
		// Enable/Disable cheating
		if (keyboard.isPressed(KeyEvent.VK_C))

			for (int i = 0; i <vd.getFrog().size() ; i++) {
				vd.getFrog().get(i).cheating = true;
			}
		if (keyboard.isPressed(KeyEvent.VK_V))
			for (int i = 0; i <vd.getFrog().size() ; i++) {
				vd.getFrog().get(i).cheating = false;
			}
		if (keyboard.isPressed(KeyEvent.VK_0)) {
			vd.setGameLevel(10);
			initializeLevel(vd.getGameLevel());
		}
		
		
		/*
		 * This logic checks for key strokes.
		 * It registers a key press, and ignores all other key strokes
		 * until the first key has been released
		 */
		if (downPressed || upPressed || leftPressed || rightPressed)
			vd.setKeyPressed(true);
		else
			keyReleased = true;
		
		if (vd.isListenInput()) {
		    if (downPressed) vd.getFrog().get(vd.getRefe()).moveDown();
		    if (upPressed) vd.getFrog().get(vd.getRefe()).moveUp();
		    if (leftPressed) vd.getFrog().get(vd.getRefe()).moveLeft();
	 	    if (rightPressed) vd.getFrog().get(vd.getRefe()).moveRight();

	 	    if (vd.isKeyPressed())
				vd.setListenInput(false);
		}
		
		if (keyReleased) {
			vd.setListenInput(true);
			vd.setKeyPressed(false);
		}
		
		if (keyboard.isPressed(KeyEvent.VK_ESCAPE))
			vd.setGameState(GAME_INTRO);
	}
	
	/**
	 * Handle keyboard events while at the game intro menu
	 */
	public void menuKeyboardHandler() {
		keyboard.poll();
		
		// Following 2 if statements allow capture space bar key strokes
		if (!keyboard.isPressed(KeyEvent.VK_SPACE)) {
			vd.setSpace_has_been_released(true);
		}
		
		if (vd.isSpace_has_been_released())
			return;
		
		if (keyboard.isPressed(KeyEvent.VK_SPACE)) {
			switch (vd.getGameState()) {
			case GAME_INSTRUCTIONS:
			case GAME_OVER:
				vd.setGameState(GAME_INTRO);
				vd.setSpace_has_been_released(false);
				break;
			default:
				vd.GameLives = FROGGER_LIVES;
				vd.GameScore = 0;
				vd.setGameLevel(STARTING_LEVEL);
				vd.levelTimer = DEFAULT_LEVEL_TIME;
				for (int i = 0; i <vd.getFrog().size() ; i++) {
					vd.getFrog().get(i).setPosition(FROGGER_START);
					vd.getAudiofx().get(i).playGameMusic();
				}

				vd.setGameState(GAME_PLAY);
				initializeLevel(vd.getGameLevel());
			}
		}
		if (keyboard.isPressed(KeyEvent.VK_H))
			vd.setGameState(GAME_INSTRUCTIONS);
	}
	
	/**
	 * Handle keyboard when finished a level
	 */
	public void finishLevelKeyboardHandler() {
		keyboard.poll();
		if (keyboard.isPressed(KeyEvent.VK_SPACE)) {
			vd.setGameState(GAME_PLAY);

			vd.getAudiofx().get(vd.getRefe()).playGameMusic();
			initializeLevel(vd.getGameLevel()+vd.getDig());
		}
	}
	
	
	/**
	 * w00t
	 */
	public void update(long deltaMs) {
		switch(vd.getGameState()) {
		case GAME_PLAY:
			froggerKeyboardHandler();
			vd.getWind().update(deltaMs);
			vd.getHwave().update(deltaMs);
			vd.getFrog().get(vd.getRefe()).update(deltaMs);
			vd.getAudiofx().get(vd.getRefe()).update(deltaMs);
			vd.getUi().update(deltaMs);
			cycleTraffic(deltaMs);
			vd.getFrogCol().get(vd.getRefe()).testCollision(vd.getMovingObjectsLayer());
			
			// Wind gusts work only when Frogger is on the river
			if (vd.getFrogCol().get(vd.getRefe()).isInRiver())
				vd.getWind().start(vd.getGameLevel());
			vd.getWind().perform(vd.getFrog().get(vd.getRefe()), vd.getGameLevel(), deltaMs);
			
			// Do the heat wave only when Frogger is on hot pavement
			if (vd.getFrogCol().get(vd.getRefe()).isOnRoad())
				vd.getHwave().start(vd.getFrog().get(vd.getRefe()), vd.getGameLevel());
			vd.getHwave().perform(vd.getFrog().get(vd.getRefe()), deltaMs, vd.getGameLevel());
			
	
			if (!vd.getFrog().get(vd.getRefe()).isAlive)
				vd.getParticleLayer().clear();
			
			vd.getGoalmanager().update(deltaMs);
			
			if (vd.getGoalmanager().getUnreached().size() == 0) {
				vd.setGameState(GAME_FINISH_LEVEL);
				vd.getAudiofx().get(vd.getRefe()).playCompleteLevel();
				vd.getParticleLayer().clear();
			}
			
			if (vd.GameLives < 1) {
				vd.setGameState(GAME_OVER);
				}
			
			break;
		
		case GAME_OVER:		
		case GAME_INSTRUCTIONS:
		case GAME_INTRO:
			vd.getGoalmanager().update(deltaMs);
			menuKeyboardHandler();
			cycleTraffic(deltaMs);
			break;
			
		case GAME_FINISH_LEVEL:
			finishLevelKeyboardHandler();
			break;		
		}

	}
	public void update_Others(long deltaMs,int n) {
		switch(vd.getGameState()) {
			case GAME_PLAY:
				froggerKeyboardHandler();

				vd.getFrog().get(n).update(deltaMs);
				vd.getAudiofx().get(n).update(deltaMs);
				vd.getFrogCol().get(n).testCollision(vd.getMovingObjectsLayer());

				// Wind gusts work only when Frogger is on the river
				if (vd.getFrogCol().get(n).isInRiver())
					vd.getWind().start(vd.getGameLevel());
				vd.getWind().perform(vd.getFrog().get(n), vd.getGameLevel(), deltaMs);

				// Do the heat wave only when Frogger is on hot pavement
				if (vd.getFrogCol().get(n).isOnRoad())
					vd.getHwave().start(vd.getFrog().get(n), vd.getGameLevel());
				vd.getHwave().perform(vd.getFrog().get(n), deltaMs, vd.getGameLevel());


				if (!vd.getFrog().get(n).isAlive)
					vd.getParticleLayer().clear();

				vd.getGoalmanager().update(deltaMs);

				if (vd.getGoalmanager().getUnreached().size() == 0) {
					vd.setGameState(GAME_FINISH_LEVEL);
					vd.getAudiofx().get(n).playCompleteLevel();
					vd.getParticleLayer().clear();
				}

				if (vd.GameLives < 1) {
					vd.setGameState(GAME_OVER);
				}

				break;

			case GAME_OVER:
			case GAME_INSTRUCTIONS:
			case GAME_INTRO:
				vd.getGoalmanager().update(deltaMs);
				menuKeyboardHandler();
				cycleTraffic(deltaMs);
				break;

			case GAME_FINISH_LEVEL:
				finishLevelKeyboardHandler();
				break;
		}
	}
	
	/**
	 * Rendering game objects
	 */
	public void render(RenderingContext rc) {
		switch(vd.getGameState()) {
		case GAME_FINISH_LEVEL:
		case GAME_PLAY:
			vd.getBackgroundLayer().render(rc);
			
			if (vd.getFrog().get(vd.getRefe()).isAlive) {
				vd.getMovingObjectsLayer().render(rc);
				//frog.collisionObjects.get(0).render(rc);
				vd.getFrog().get(vd.getRefe()).render(rc);
			} else {
				vd.getFrog().get(vd.getRefe()).render(rc);
				vd.getMovingObjectsLayer().render(rc);
			}
			
			vd.getParticleLayer().render(rc);
			vd.getUi().render(rc);
			break;
			
		case GAME_OVER:
		case GAME_INSTRUCTIONS:
		case GAME_INTRO:
			vd.getBackgroundLayer().render(rc);
			vd.getMovingObjectsLayer().render(rc);
			vd.getUi().render(rc);
			break;		
		}
	}
	
	public static void main (FroggerGame game,GameState k ) {

		Main f = new Main(game,k);
		f.run();
	}

}