package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import edu.ufp.inf.sd.rabbitmqservices.Project.producer.FroggerGame.src.frogger.Main;

import java.io.IOException;

public class ClientGame extends Thread{
    GameState2 gameState2;
    FroggerGame2 s;

    public ClientGame(GameState2 gameState2, FroggerGame2 s) {
        super();
        this.gameState2 = gameState2;
        this.s = s;
    }

    public void run() {
        String[] g ={"apple", "banana", "mango", "orange"};
        Main g1;
        try {
            g1 = new Main(g,gameState2,s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g1.run();
    }

}
