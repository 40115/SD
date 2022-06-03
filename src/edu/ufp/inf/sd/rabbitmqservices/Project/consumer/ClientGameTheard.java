package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import edu.ufp.inf.sd.rmi.Project.server.Vect;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientGameTheard extends Thread{
    FroggerGame2 g;
    Channel channel;String Name;
    public ClientGameTheard(FroggerGame2 g, Channel channel,String Name) {
        super();
        this.channel=channel;
        this.Name=Name;
        this.g=g;
    }


    public void run() {
        try {
            channel.exchangeDeclare(g.exhange_name, BuiltinExchangeType.FANOUT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String queuename;
        try {
            queuename = channel.queueDeclare().getQueue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String routingkey="";
        try {
            channel.queueBind(queuename,g.getExhange_name(),routingkey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
System.out.println(queuename);
        // Publisher pushes messages asynchronously, hence use DefaultConsumer callback
        //   that buffers messages until consumer is ready to handle them.

        Logger.getAnonymousLogger().log(Level.INFO,Thread.currentThread().getName()+":will create callback");
        System.out.println("waiting msg");
        //DeliverCallback is an handler callback (lambda method) to consume messages pushed by the sender.
        //Create an handler callback to receive messages from queue


        DeliverCallback deliverCallback=(consumerTag, delivery) -> {
            String message=new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" +consumerTag+ "Message"+ message + "'");
            String[] decompiler=message.split("\\|");
            switch (decompiler[0]) {
                //String msg=decompiler[0]+"|"+mesg[0]+","+mesg[1]+","+ Arrays.toString(name);
                case "ROADS2":
String [] mes=decompiler[1].split(",");
GameState2 ga;
                    for (int i = 0; i <g.gameState2s.size() ; i++) {
                        if (Objects.equals(g.gameState2s.get(i).Name, Name)) {
                            ga=g.gameState2s.get(i);
                                ga.roads.get(Integer.parseInt(mes[0])).getTypes().add(Integer.valueOf(mes[1]));
break;

                        }
                    }
                      break;

                case "RIVER2":
                   mes=decompiler[1].split(",");
                    for (int i = 0; i <g.gameState2s.size() ; i++) {
                        if (Objects.equals(g.gameState2s.get(i).Name, Name)) {
                            ga=g.gameState2s.get(i);

                                ga.river.get(Integer.parseInt(mes[0])).getTypes().add(Integer.valueOf(mes[1]));
                                break;

                        }
                    }
break;
                case "Frog2":
                    //"Frog|0,0,"+vd.Name+","+game2.getId()+","+ref;
                    mes=decompiler[1].split(",");
                    for (int i = 0; i <g.gameState2s.size() ; i++) {
if (Objects.equals(g.gameState2s.get(i).Name, Name)){
    g.gameState2s.get(i).getFrogposition().set(Integer.parseInt(mes[4]),new Vect(Double.parseDouble(mes[0]),Double.parseDouble(mes[1])));
}
                    }


            }

        };

        try {
            channel.basicConsume(queuename, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            System.out.println("here");
        }




}
