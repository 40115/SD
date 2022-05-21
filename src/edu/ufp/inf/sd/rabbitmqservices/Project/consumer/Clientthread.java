package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Clientthread extends Thread{
    public Clientthread() {
        super();
    }
    public void run(Channel channel,String queueName,String pass,String name) throws IOException {
        DeliverCallback deliverCallback=(consumerTag, delivery) -> {
            String message=new String(delivery.getBody(), StandardCharsets.UTF_8);
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Message received " +message);
            System.out.println(" [x] Received '" + message + "'");
            String[] decompiler=message.split("\\|");
            switch (decompiler[0]){
                case "I":
                    if (Objects.equals(decompiler[1], "ACK")){
                        String mess="L|ALL|"+pass;
                        channel.queueDeclare(pass, true, false, false, null);

                        channel.basicPublish("", queueName, null, mess.getBytes("UTF-8"));


                    }else {
                        System.out.println("ERRO NAME NOT ACKInsert Name:\n");
                      BufferedReader  reader=new BufferedReader(
                                new InputStreamReader(System.in));
                        // Reading data using readLine
                       String name1=reader.readLine();
                        //try-with-resources...
                       String messageI="I|" + name1 + "|" + pass;
                        channel.basicPublish("", queueName, null, messageI.getBytes("UTF-8"));

                    }
                    break;
                case "L":
                    System.out.println(decompiler[1]);
                    System.out.println("1-To join a game\n 2-to create a game\n How it works it is that if you create or join in you get asssign a server to comunicate with and start the game");
                    BufferedReader   reader1 = new BufferedReader(
                            new InputStreamReader(System.in));
                    int Id = Integer.parseInt(reader1.readLine());

                   String messageI="J|" + name+","+Id + "|" + pass;
                    channel.basicPublish("", queueName, null, messageI.getBytes("UTF-8"));
                case "J1":


                    break;

                case "C1":


                    break;


            }

        };
        Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Register Deliver Callback...");
        //Associate callback with channel queue
        channel.basicConsume(pass, true, deliverCallback, consumerTag -> {
        });

    }

}
