package edu.ufp.inf.sd.rabbitmqservices.Project.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import edu.ufp.inf.sd.rabbitmqservices.Project.consumer.Clientthread;
import edu.ufp.inf.sd.rabbitmqservices.util.RabbitUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        RabbitUtils.printArgs(args);

        //Read args passed via shell command
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String queueName = args[2];
        System.out.println("Insert Name:\n");
        final AtomicReference<BufferedReader>[] reader = new AtomicReference[]{new AtomicReference<>(new BufferedReader(
                new InputStreamReader(System.in)))};

        // Reading data using readLine
        AtomicReference<String> name = new AtomicReference<>(reader[0].get().readLine());
        //try-with-resources...
        String pass=generate_Random_String(12);
        AtomicReference<String> messageI = new AtomicReference<>("I|" + name + "|" + pass);
        queueName="Front";
        boolean run = true;
        /* try-with-resources will close resources automatically in reverse order... avoids finally */
       Connection connection = RabbitUtils.newConnection2Server(host, port, "guest", "guest");
             Channel channel = RabbitUtils.createChannel2Server(connection) ;
            // Declare a queue where to send msg (idempotent, i.e., it will only be created if it doesn't exist);
            channel.queueDeclare(queueName, true, false, false, null);
            //channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueDeclare(pass, true, false, false, null);



        // Publish a message to the queue (content is byte array encoded with UTF-8)
            channel.basicPublish("", queueName, null, messageI.get().getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + messageI + "'");
        Clientthread h=new Clientthread();
        h.run(channel,queueName,pass,name.toString());
            /*
            String finalQueueName = queueName;
            String finalQueueName1 = queueName;
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

                             channel.basicPublish("", finalQueueName, null, mess.getBytes("UTF-8"));


                         }else {
                             System.out.println("ERRO NAME NOT ACKInsert Name:\n");
                              reader[0].set(new BufferedReader(
                                      new InputStreamReader(System.in)));
                             // Reading data using readLine
                             name.set(reader[0].get().readLine());
                             //try-with-resources...
                              messageI.set("I|" + name + "|" + pass);
                             channel.basicPublish("", finalQueueName1, null, messageI.get().getBytes("UTF-8"));

                         }
                         break;
                    case "L":
                        System.out.println(decompiler[1]);
                        System.out.println("1-To join a game\n 2-to create a game\n How it works it is that if you create or join in you get asssign a server to comunicate with and start the game");
                        BufferedReader   reader1 = new BufferedReader(
                                new InputStreamReader(System.in));
                     int Id = Integer.parseInt(reader1.readLine());
                        messageI.set("J|" + name+","+Id + "|" + pass);
                        channel.basicPublish("", finalQueueName1, null, messageI.get().getBytes("UTF-8"));
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

*/

        while (true){
            Thread.sleep(4000);

        }

    }

    public static String generate_Random_String(int r){
        StringBuilder string= new StringBuilder();
        for (int i = 0; i <r ; i++) {
            Random rnd = new Random();
            char c = (char) ('a' + rnd.nextInt(26));
            string.append(c);
        }
        System.out.println(string.toString());
        return string.toString();
    }
}
