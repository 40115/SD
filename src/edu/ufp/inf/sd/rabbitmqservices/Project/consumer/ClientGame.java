package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import edu.ufp.inf.sd.rabbitmqservices.Project.producer.FroggerGame.src.frogger.Main;

import java.io.IOException;

public class ClientGame extends Thread{
    public void run(GameState2 gameState2,FroggerGame2 s) throws IOException {
        String[] g ={"apple", "banana", "mango", "orange"};
        Main g1=new Main(g,gameState2,s);
        g1.run();
    }
}
