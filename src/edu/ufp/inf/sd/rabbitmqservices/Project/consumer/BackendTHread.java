package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import edu.ufp.inf.sd.rmi.Project.server.Vect;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackendTHread extends  Thread{
    Channel channel;String exchange;String name;FroggerGame2 s;

    public BackendTHread(Channel channel, String exchange, String name, FroggerGame2 s) {
        super();
        this.channel = channel;
        this.exchange = exchange;
        this.name = name;
        this.s = s;
    }

    public void run() {
        String pass;
        try {


            channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT);
             pass = channel.queueDeclare().getQueue();
            String routingkey = "";
            channel.queueBind(pass, exchange, routingkey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DeliverCallback deliverCallback=(consumerTag, delivery) -> {
            String message=new String(delivery.getBody(), StandardCharsets.UTF_8);
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Message received " +message);
            System.out.println(" [x] Received '" + message + "'");
            String[] decompiler=message.split("\\|");
            switch (decompiler[0]){
                case "ROADS2":
                    String[] mesf =decompiler[1].split(",");
                    if (!Objects.equals(mesf[2], name)){
                        for (int i = 0; i <s.gameState2s.size() ; i++) {
                            if (s.gameState2s.get(i).getRoads().get(Integer.parseInt(mesf[0])).getTypes().size()==0){
                                s.gameState2s.get(i).getRoads().get(Integer.parseInt(mesf[0])).getTypes().add(Integer.valueOf(mesf[1]));
                            }else {
                                s.gameState2s.get(i).getRoads().get(Integer.parseInt(mesf[0])).getTypes().remove(0);
                                s.gameState2s.get(i).getRoads().get(Integer.parseInt(mesf[0])).getTypes().add(Integer.valueOf(mesf[1]));
                            }
                        }
                    }
            break;
                case "RIVER2":
                    //String msg=decompiler[0]+"|"+mesg[0]+","+mesg[1]+","+ Arrays.toString(name);
                     mesf=decompiler[1].split(",");
                    if (!Objects.equals(mesf[2], name)){
                        for (int i = 0; i <s.gameState2s.size() ; i++) {
                            if (s.gameState2s.get(i).getRiver().get(Integer.parseInt(mesf[0])).getTypes().size()==0){
                                s.gameState2s.get(i).getRiver().get(Integer.parseInt(mesf[0])).getTypes().add(Integer.valueOf(mesf[1]));
                            }else {
                                s.gameState2s.get(i).getRiver().get(Integer.parseInt(mesf[0])).getTypes().remove(0);
                                s.gameState2s.get(i).getRiver().get(Integer.parseInt(mesf[0])).getTypes().add(Integer.valueOf(mesf[1]));
                            }
                        }
                    }
                    break;
                case "Frog2":
                    //	"Frog|-1,0,"+game.game2.getId()+","+game.vd.Name;
                    mesf=decompiler[1].split(",");
                    // String msg=decompiler[0]+"2"+"|"+mesg[0]+","+mesg[1]+","+mesg[2]+","+ name;
                    //String msg=decompiler[0]+"2"+"|"+mesg[0]+","+mesg[1]+","+mesg[2]+","+ name;
                    if (!Objects.equals(mesf[3], name)){
                        for (int i = 0; i <s.gameState2s.size() ; i++) {
                            if (Objects.equals(s.gameState2s.get(i).Name, mesf[2])){
                                if (s.gameState2s.get(i).getFrogposition().size()==0){
                                    s.gameState2s.get(i).getFrogposition().add(new Vect(Double.parseDouble(mesf[0]),Double.parseDouble(mesf[1])));
                                }else {
                                    s.gameState2s.get(i).getFrogposition().set(0,new Vect(Double.parseDouble(mesf[0]),Double.parseDouble(mesf[1])));
                                }
                            }

                        }

                    }
                    break;
                //'Die|0,Ruben,0'
                    //String msg=decompiler[0]+"2"+"|"+mesg[0]+","+mesg[1]+","+mesg[2]+","+ name+","+i;
                case "Die2":
                    mesf=decompiler[1].split(",");
                    if (!Objects.equals(mesf[3], name)){
                        for (int i = 0; i <s.gameState2s.size() ; i++) {
                       s.gameState2s.get(i).getIsDead().set(Integer.parseInt(mesf[4]),1);
                        }
                    }
break;
                //"Timer|"+timer+","+game.game2.getId()+","+Name;
                case "Timer2":
                    mesf=decompiler[1].split(",");
                    if (!Objects.equals(mesf[3], name)){
                        for (int i = 0; i <s.gameState2s.size() ; i++) {
                            s.gameState2s.get(i).setLevelTimer(Integer.parseInt(mesf[0]));
                        }
                    }
                break;
                case "Finish2":
                    mesf=decompiler[1].split(",");
                    if (!Objects.equals(mesf[2], name)){
                        for (int i = 0; i <s.gameState2s.size() ; i++) {
                            s.gameState2s.get(i).setHAsended(true);
                        }
                    }
                    break;
            }


        };
        try {
            channel.basicConsume(pass, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}
