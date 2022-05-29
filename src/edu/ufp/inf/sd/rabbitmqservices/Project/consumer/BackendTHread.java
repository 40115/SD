package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
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


    public void run(Channel channel,String exchange,String name,FroggerGame2 s) throws IOException {
        channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT);
        String pass=channel.queueDeclare().getQueue();
        String routingkey="";
        channel.queueBind(pass,exchange,routingkey);
        DeliverCallback deliverCallback=(consumerTag, delivery) -> {
            String message=new String(delivery.getBody(), StandardCharsets.UTF_8);
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Message received " +message);
            System.out.println(" [x] Received '" + message + "'");
            String[] decompiler=message.split("\\|");
            switch (decompiler[0]){
                case "ROADS":
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
                case "RIVER":
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
            }


        };
        channel.basicConsume(pass, true, deliverCallback, consumerTag -> {
        });


    }
}
