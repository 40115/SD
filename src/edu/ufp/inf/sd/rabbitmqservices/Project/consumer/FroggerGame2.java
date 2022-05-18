package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import java.util.ArrayList;

public class FroggerGame2 {
    Integer Id;
    ArrayList<GameState2> gameState2s=new ArrayList<>();

    public FroggerGame2(Integer id) {
        Id = id;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
