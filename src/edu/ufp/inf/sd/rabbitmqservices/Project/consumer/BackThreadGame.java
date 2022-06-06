package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Channel;
import edu.ufp.inf.sd.rmi.Project.server.Vect;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.Remote;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackThreadGame extends  Thread {
    String Workqueu;
    DB2 database;
    Channel channel;
    String name;

    public BackThreadGame(String workqueu, DB2 database, Channel channel, String name) {
        super();
        this.Workqueu = workqueu;
        this.database = database;
        this.channel = channel;
        this.name = name;
    }

    public void run() {
        int prefetchCount = 1;
        System.out.println("Starting threadGame");
        try {
            channel.basicQos(prefetchCount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DeliverCallback deliverCallback2=(consumerTag, delivery) -> {
            String message=new String(delivery.getBody(), StandardCharsets.UTF_8);
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Message received " +message);
            System.out.println(" [x] Received2 '" + message + "'");
            String[] decompiler=message.split("\\|");
            switch (decompiler[0]){
                case "RIVER":
                case "ROADS":
                    //						 msg="ROADS|0,1,"+vd.Name+","+game2.getId();
                    String[] mesg=decompiler[1].split(",");
                    FroggerGame2 d=null;
                    for (int i = 0; i <database.Games.size() ; i++) {
                        if (database.Games.get(i).getId()==Integer.parseInt(mesg[3])){
                            d=database.Games.get(i);
                        }
                    }
                    if (d==null)break;
                    for (int i = 0; i <d.gameState2s.size() ; i++) {
                        if (Objects.equals(d.gameState2s.get(i).Name, mesg[2])){
                            if (d.gameState2s.get(i).isMAster()){
                                String msg=decompiler[0]+"2"+"|"+mesg[0]+","+mesg[1]+","+ name;

                                channel.basicPublish(d.exhange_name, "",null,msg.getBytes(StandardCharsets.UTF_8));

                                break;
                            }

                        }
                    }

                    break;
             case "Frog":
                 //"Frog|0,0,"+vd.Name+","+game2.getId(); errro 'Frog|-1,0,0,Jose'
                mesg=decompiler[1].split(",");
                  d=null;
                 for (int i = 0; i <database.Games.size() ; i++) {
                     if (database.Games.get(i).getId()==Integer.parseInt(mesg[2])){
                         d=database.Games.get(i);
                     }
                 }
                 if (d==null)break;
                 for (int i = 0; i <d.gameState2s.size() ; i++) {
                     if (Objects.equals(d.gameState2s.get(i).Name, mesg[3])){
                         //d.gameState2s.get(i).getRefe()
                             d.gameState2s.get(i).getFrogposition().set(i,new Vect(Double.parseDouble(mesg[0]),Double.parseDouble(mesg[1])));
                         for (int j = 0; j <d.gameState2s.size() ; j++) {
                             d.gameState2s.get(j).getFrogposition().set(i,new Vect(Double.parseDouble(mesg[0]),Double.parseDouble(mesg[1])));

                         }

                             String msg=decompiler[0]+"2"+"|"+mesg[0]+","+mesg[1]+","+mesg[3]+","+ name+","+i;
                             channel.basicPublish(d.exhange_name, "",null,msg.getBytes(StandardCharsets.UTF_8));

                             break;


                     }


                 }
                 break;
                 //    //"Die|"+vd.getRefe()+vd.Name+","+game.game2.getId();
                case "Die":
                    mesg=decompiler[1].split(",");
                    d=null;
                    for (int i = 0; i <database.Games.size() ; i++) {
                        if (database.Games.get(i).getId()==Integer.parseInt(mesg[2])){
                            d=database.Games.get(i);
                        }
                    }
                    if (d==null)break;
                    for (int i = 0; i <d.gameState2s.size() ; i++) {
                        if (Objects.equals(d.gameState2s.get(i).Name, mesg[1])){

                            String msg=decompiler[0]+"2"+"|"+mesg[0]+","+mesg[1]+","+mesg[2]+","+ name+","+i;
                            for (int j = 0; j <d.gameState2s.size() ; j++) {

                                    d.gameState2s.get(j).getIsDead().set(i,1);

                            }
                            channel.basicPublish(d.exhange_name, "",null,msg.getBytes(StandardCharsets.UTF_8));

                            break;


                        }


                    }
                    break;
                case "Timer":
                    mesg=decompiler[1].split(",");
                    d=null;
                    for (int i = 0; i <database.Games.size() ; i++) {
                        if (database.Games.get(i).getId()==Integer.parseInt(mesg[1])){
                            d=database.Games.get(i);
                        }
                    }
                    if (d==null)break;
                    for (int i = 0; i <d.gameState2s.size() ; i++) {
                        d.gameState2s.get(i).setLevelTimer(Integer.parseInt(mesg[0]));
                    }
                    String msg=decompiler[0]+"2"+"|"+mesg[0]+","+mesg[1]+","+mesg[2]+","+ name;
                    channel.basicPublish(d.exhange_name, "",null,msg.getBytes(StandardCharsets.UTF_8));

                    break;
                //"Finish"|game.game2.getId()+","+Name
                case "Finish":
                    mesg=decompiler[1].split(",");
                    d=null;
                    for (int i = 0; i <database.Games.size() ; i++) {
                        if (database.Games.get(i).getId()==Integer.parseInt(mesg[0])){
                            d=database.Games.get(i);
                        }
                    }
                    if (d==null)break;
                    for (int i = 0; i <d.gameState2s.size() ; i++) {
                        d.gameState2s.get(i).setHAsended(true);
                    }
                   msg=decompiler[0]+"2"+"|"+mesg[0]+","+mesg[1]+","+ name;
                    channel.basicPublish(d.exhange_name, "",null,msg.getBytes(StandardCharsets.UTF_8));
break;
            }


        };
        try {
            channel.basicConsume(Workqueu, true, deliverCallback2, consumerTag -> {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
