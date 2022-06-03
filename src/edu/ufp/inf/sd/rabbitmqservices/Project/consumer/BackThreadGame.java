package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Channel;

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
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    break;
             case "Frog":
                 //"Frog|0,0,"+vd.Name+","+game2.getId();
                mesg=decompiler[1].split(",");
                  d=null;
                 for (int i = 0; i <database.Games.size() ; i++) {
                     if (database.Games.get(i).getId()==Integer.parseInt(mesg[3])){
                         d=database.Games.get(i);
                     }
                 }
                 if (d==null)break;
                 for (int i = 0; i <d.gameState2s.size() ; i++) {
                     if (Objects.equals(d.gameState2s.get(i).Name, mesg[2])){
                         if (d.gameState2s.get(i).isMAster()){
                             String msg=decompiler[0]+"2"+"|"+mesg[0]+","+mesg[1]+","+mesg[3]+","+ name;
                             channel.basicPublish(d.exhange_name, "",null,msg.getBytes(StandardCharsets.UTF_8));
                             break;
                         }

                     }
                 }
                 channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }

        };
        try {
            channel.basicConsume(Workqueu, false, deliverCallback2, consumerTag -> {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
