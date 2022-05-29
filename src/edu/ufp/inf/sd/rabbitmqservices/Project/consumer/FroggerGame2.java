package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;
import com.rabbitmq.client.Channel;
import java.util.ArrayList;

public class FroggerGame2 {
    public boolean isrunning=false;
    Integer Id;
    String exhange_name;
    Channel d;
    ArrayList<GameState2> gameState2s=new ArrayList<>();

    public FroggerGame2(Integer id, String exhange_name,Channel d) {
        Id = id;
        this.exhange_name = exhange_name;
        this.d=d;
    }
public boolean check_Ready(){
        if (gameState2s.size()<=1) return false;
    for (GameState2 gameState2 : gameState2s) {
        if (!gameState2.isReady()) return false;
    }
        return true;
}
    public String Construct_Message(){
      StringBuilder aux= new StringBuilder("R1|"+Id+",");
        for (int i = 0; i <gameState2s.size() ; i++) {
            gameState2s.get(i).setRefe(i);
            aux.append(gameState2s.get(i).getName()).append(",");
        }
aux.append("|").append(exhange_name);
        return aux.toString();
    }
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public boolean isIsrunning() {
        return isrunning;
    }

    public void setIsrunning(boolean isrunning) {
        this.isrunning = isrunning;
    }

    public String getExhange_name() {
        return exhange_name;
    }

    public void setExhange_name(String exhange_name) {
        this.exhange_name = exhange_name;
    }

    public ArrayList<GameState2> getGameState2s() {
        return gameState2s;
    }

    public void setGameState2s(ArrayList<GameState2> gameState2s) {
        this.gameState2s = gameState2s;
    }

    public Channel getD() {
        return d;
    }

    public void setD(Channel d) {
        this.d = d;
    }
}
