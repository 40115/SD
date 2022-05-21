package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import java.util.ArrayList;

public class FroggerGame2 {
    public boolean isrunning=false;
    Integer Id;
    String exhange_name;
    ArrayList<GameState2> gameState2s=new ArrayList<>();

    public FroggerGame2(Integer id, String exhange_name) {
        Id = id;
        this.exhange_name = exhange_name;
    }


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
