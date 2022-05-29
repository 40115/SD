package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackendTHread extends  Thread{
    public BackendTHread() {
        super();
    }


    public void run(Channel channel,String queueName,String pass,String name) throws IOException {
        DB2 database=new DB2();
        String messageI = "IS|"+name+"|" + pass;
        channel.basicPublish("", queueName, null, messageI.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + messageI + "'");
        final String[] pass1 = {pass};
        String queueName3 = "Pushin/";
        DeliverCallback deliverCallback=(consumerTag, delivery) -> {
            String message=new String(delivery.getBody(), StandardCharsets.UTF_8);
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Message received " +message);
            System.out.println(" [x] Received '" + message + "'");
            String[] decompiler=message.split("\\|");

            switch (decompiler[0]){
                case "IS":
                    if (Objects.equals(decompiler[1], "ACK")){
                        channel.queueDeclare(pass1[0], true, false, false, null);
                    }else {
                        System.out.println("ERRO NAME already pro exist :\n");
                        pass1[0] =decompiler[2];

                    }
                    break;
                case "C":
                //C|Ruben,4,exchagename|route
                     String[] mesg=decompiler[1].split(",");
                    FroggerGame2 d = new FroggerGame2(Integer.parseInt(mesg[1]), mesg[2],channel);
                    GameState2 h=new GameState2(Integer.parseInt(mesg[1]),true,mesg[0]);
                    d.gameState2s.add(h);
                    database.Games.add(d);
                    BackThreadGame c=new BackThreadGame();
                    c.run(mesg[2],database.Games.get(Integer.parseInt(mesg[1])));

                    break;

                case "J":
                    //C|Ruben,4,exchagename|route

            }

        };
        DeliverCallback deliverCallback2=(consumerTag, delivery) -> {
            String message=new String(delivery.getBody(), StandardCharsets.UTF_8);
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Message received " +message);
            System.out.println(" [x] Received '" + message + "'");
            String[] decompiler=message.split("\\|");

            switch (decompiler[0]){

            }

        };
        Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Register Deliver Callback...");
        //Associate callback with channel queue
        channel.basicConsume(pass, true, deliverCallback, consumerTag -> {
        });
        String Back="Back";
        channel.basicConsume(Back, true, deliverCallback2, consumerTag -> {
        });
    }
}
