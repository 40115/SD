package edu.ufp.inf.sd.rabbitmqservices.Project.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import edu.ufp.inf.sd.rabbitmqservices.Project.consumer.*;
import edu.ufp.inf.sd.rabbitmqservices.util.RabbitUtils;
import edu.ufp.inf.sd.rmi.Project.server.Vect;

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
DB2 database=new DB2();
        //Read args passed via shell command
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String queueName;
        System.out.println("Insert Name:\n");
        BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine
        String name = reader.readLine();
        //try-with-resources...
        String pass=generate_Random_String(12);
        AtomicReference<String> messageI = new AtomicReference<>("I|" + name + "|" + pass);
        queueName="Front";

        /* try-with-resources will close resources automatically in reverse order... avoids finally */
       Connection connection = RabbitUtils.newConnection2Server(host, port, "guest", "guest");
             Channel channel = RabbitUtils.createChannel2Server(connection) ;
            // Declare a queue where to send msg (idempotent, i.e., it will only be created if it doesn't exist);
            channel.queueDeclare(queueName, false, false, false, null);
            //channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueDeclare(pass, false, false, false, null);



        // Publish a message to the queue (content is byte array encoded with UTF-8)
            channel.basicPublish("", queueName, null, messageI.get().getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + messageI + "'");

        String finalQueueName = queueName;
        DeliverCallback deliverCallback=(consumerTag, delivery) -> {
            String message=new String(delivery.getBody(), StandardCharsets.UTF_8);
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Message received " +message);
            System.out.println(" [x] Received '" + message + "'");
            String[] decompiler=message.split("\\|");
            switch (decompiler[0]){
                case "I":
                    if (Objects.equals(decompiler[1], "ACK")){
                        String mess="L|ALL|"+pass;
                        channel.queueDeclare(pass, false, false, false, null);
                        channel.basicPublish("", finalQueueName, null, mess.getBytes(StandardCharsets.UTF_8));
                    }else {
                        System.out.println("ERRO NAME NOT ACKInsert Name:\n");
                       BufferedReader reader2=new BufferedReader(
                                new InputStreamReader(System.in));
                        // Reading data using readLine
                        String name1=reader2.readLine();
                        //try-with-resources...
                        String messageII="I|" + name1 + "|" + pass;
                        channel.basicPublish("", finalQueueName, null, messageII.getBytes(StandardCharsets.UTF_8));

                    }
                    break;
                case "L":
                    int Id;
                    do {
                        System.out.println(decompiler[1]);
                        System.out.println("1-To join a game\n 2-to create a game\n 3-To list again\n How it works it is that if you create or join in you get asssign a server to comunicate with and start the game");
                        BufferedReader reader1 = new BufferedReader(
                                new InputStreamReader(System.in));
                        Id = Integer.parseInt(reader1.readLine());
                        switch (Id) {
                            case 1:
                                reader1 = new BufferedReader(
                                        new InputStreamReader(System.in));
                                int Id2 = Integer.parseInt(reader1.readLine());
                                String messageII = "J|" + name + "," + Id2 + "|" + pass;
                                channel.basicPublish("", finalQueueName, null, messageII.getBytes(StandardCharsets.UTF_8));
                                break;
                            case 2:
                                messageII = "C|" + name + "|" + pass;
                                channel.basicPublish("", finalQueueName, null, messageII.getBytes(StandardCharsets.UTF_8));
                                break;
                            case 3:
                                String mess="L|ALL|"+pass;
                                channel.queueDeclare(pass, true, false, false, null);
                                channel.basicPublish("", finalQueueName, null, mess.getBytes(StandardCharsets.UTF_8));
                                break;
                            default:
                                System.out.println("Invalied");

                        }
                    }while (Id!=2 && Id!=1);
                    break;
                case "J1":
                    //J1|ACK,id,name,1|dec
                    String []f =decompiler[1].split(",");
                    if (Objects.equals(f[0], "ACK")) {
                        FroggerGame2 d = new FroggerGame2(Integer.parseInt(f[1]), f[2],channel);
                        database.getGames().add(d);
                        int id;
                        do {
                            System.out.println("Ready 0;\n ");
                            BufferedReader  reader3=new BufferedReader(new InputStreamReader(System.in));
                            id= Integer.parseInt(reader3.readLine());
                        }while(id!=0);
                        String mess="R|"+d.getId()+","+name+"|FRONT";
                        channel.basicPublish("", finalQueueName, null, mess.getBytes(StandardCharsets.UTF_8));

                    }else {
                        System.out.println("Join Invalied\n Game already exists\n");
                        String mess="L|ALL|"+pass;
                        channel.queueDeclare(pass, true, false, false, null);
                        channel.basicPublish("", finalQueueName, null, mess.getBytes(StandardCharsets.UTF_8));
                    }
                    break;

                case "C1":
                    // "C1|ACK,"+exchangename+","+(database.id_Tot-1) + "|" + decompiler[2];
                    f =decompiler[1].split(",");
                    if (Objects.equals(f[0], "ACK")) {
                        FroggerGame2 d = new FroggerGame2(Integer.parseInt(f[2]), f[1],channel);
                        GameState2 h1=new GameState2(0,true,name);
                        d.getGameState2s().add(h1);
                        database.getGames().add(d);
                        int id;
                        do {
                            System.out.println("Ready 0;\n ");
                            BufferedReader  reader4=new BufferedReader(
                                    new InputStreamReader(System.in));
                            id= Integer.parseInt(reader4.readLine());

                        }while(id!=0);
                        String mess="R|"+d.getId()+","+name+"|FRONT";
                        channel.basicPublish("", finalQueueName, null, mess.getBytes(StandardCharsets.UTF_8));

                    }else {
                        System.out.println("Creation Invalid\n Game already exists\n");
                        String mess="L|ALL|"+pass;
                        channel.queueDeclare(pass, true, false, false, null);
                        channel.basicPublish("", finalQueueName, null, mess.getBytes(StandardCharsets.UTF_8));
                    }
                    break;
                case "R1":
                    f =decompiler[1].split(",");
                    int j=Integer.parseInt(f[0]);
                    boolean sa=false;
                    FroggerGame2 s=null;
                    for (FroggerGame2 g:database.getGames()) {
                        if (g.getId() == j) {
                            sa = true;
                            s=g;
                            break;
                        }
                    }
                    if (!sa){
                        s=new FroggerGame2(j,decompiler[2],channel);
                        database.getGames().add(s);
                    }else {
                        s.setExhange_name(decompiler[2]);
                    }
                    for (int i = 1; i <f.length ; i++) {
                        boolean f2=false;
                        for (int k = 0; k <s.getGameState2s().size() ; k++) {
                            if (Objects.equals(s.getGameState2s().get(k).Name, f[i])) {
                                f2 = true;
                                break;
                            }
                        }
                        if (!f2  && i==1){

                            s.getGameState2s().add(new GameState2(0,true,f[i]))    ;
                        }else if (!f2){

                            s.getGameState2s().add(new GameState2(i-1,false,f[i]))    ;
                        }
                    }

                    for (int i = 0; i <s.getGameState2s().size() ; i++) {
                        for (int k = 0; k <s.getGameState2s().size() ; k++) {
                            s.getGameState2s().get(i).getFrogposition().add(new Vect(0,0));
                            s.getGameState2s().get(i).getIsDead().add(0);
                        }

                    }





                    GameState2 fq = null;
                    for (int i = 0; i <s.getGameState2s().size() ; i++) {
                        if (Objects.equals(s.getGameState2s().get(i).Name, name)) {fq=s.getGameState2s().get(i);break;}

                    }

                    ClientGameTheard s2=new ClientGameTheard(s,channel,name);

                    s2.start();
                    ClientGame h1=new ClientGame(fq,s);
                    h1.start();
            }

        };
        Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Register Deliver Callback...");
        //Associate callback with channel queue
        channel.basicConsume(pass, true, deliverCallback, consumerTag -> {
        });

    }

    public static String generate_Random_String(int r){
        StringBuilder string= new StringBuilder();
        for (int i = 0; i <r ; i++) {
            Random rnd = new Random();
            char c = (char) ('a' + rnd.nextInt(26));
            string.append(c);
        }
        System.out.println(string);
        return string.toString();
    }
}
