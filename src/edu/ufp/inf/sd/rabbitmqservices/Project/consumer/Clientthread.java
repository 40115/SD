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
        DB2 database=new DB2();
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
                        channel.basicPublish("", queueName, null, mess.getBytes(StandardCharsets.UTF_8));
                    }else {
                        System.out.println("ERRO NAME NOT ACKInsert Name:\n");
                      BufferedReader  reader=new BufferedReader(
                                new InputStreamReader(System.in));
                        // Reading data using readLine
                       String name1=reader.readLine();
                        //try-with-resources...
                       String messageI="I|" + name1 + "|" + pass;
                        channel.basicPublish("", queueName, null, messageI.getBytes(StandardCharsets.UTF_8));

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
                            String messageI = "J|" + name + "," + Id2 + "|" + pass;
                            channel.basicPublish("", queueName, null, messageI.getBytes(StandardCharsets.UTF_8));
                            break;
                            case 2:
                                 messageI = "C|" + name + "|" + pass;
                                channel.basicPublish("", queueName, null, messageI.getBytes(StandardCharsets.UTF_8));
                                break;
                            case 3:
                                String mess="L|ALL|"+pass;
                                channel.queueDeclare(pass, true, false, false, null);
                                channel.basicPublish("", queueName, null, mess.getBytes(StandardCharsets.UTF_8));
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
                        database.Games.add(d);
                        int id;
                        do {
                            System.out.println("Ready 0;\n ");
                            BufferedReader  reader=new BufferedReader(new InputStreamReader(System.in));
                            id= Integer.parseInt(reader.readLine());
                        }while(id!=0);
                        String mess="R|"+d.Id+","+name+"|FRONT";
                        channel.basicPublish("", queueName, null, mess.getBytes(StandardCharsets.UTF_8));

                    }else {
                        System.out.println("Join Invalied\n Game already exists\n");
                        String mess="L|ALL|"+pass;
                        channel.queueDeclare(pass, true, false, false, null);
                        channel.basicPublish("", queueName, null, mess.getBytes(StandardCharsets.UTF_8));
                    }
                    break;

                case "C1":
                   // "C1|ACK,"+exchangename+","+(database.id_Tot-1) + "|" + decompiler[2];
                    f =decompiler[1].split(",");
                    if (Objects.equals(f[0], "ACK")) {
                        FroggerGame2 d = new FroggerGame2(Integer.parseInt(f[2]), f[1],channel);
                        GameState2 h=new GameState2(0,true,name);
                        d.gameState2s.add(h);
                        database.Games.add(d);
                        int id;
                       do {
                           System.out.println("Ready 0;\n ");
                           BufferedReader  reader=new BufferedReader(
                                   new InputStreamReader(System.in));
                          id= Integer.parseInt(reader.readLine());

                       }while(id!=0);
                      String mess="R|"+d.Id+","+name+"|FRONT";
                        channel.basicPublish("", queueName, null, mess.getBytes(StandardCharsets.UTF_8));

                    }else {
                        System.out.println("Creation Invalied\n Game already exists\n");
                        String mess="L|ALL|"+pass;
                        channel.queueDeclare(pass, true, false, false, null);
                        channel.basicPublish("", queueName, null, mess.getBytes(StandardCharsets.UTF_8));
                    }
                    break;
                case "R1":
                    f =decompiler[1].split(",");
                    int j=Integer.parseInt(f[0]);
                    boolean sa=false;
                    FroggerGame2 s=null;
                    for (FroggerGame2 g:database.Games) {
                        if (g.Id == j) {
                            sa = true;
                            s=g;
                            break;
                        }
                    }
                    if (!sa){
                        s=new FroggerGame2(j,decompiler[2],channel);
                        database.Games.add(s);
                    }
                    for (int i = 1; i <f.length ; i++) {
                        boolean f2=false;
                        for (int k = 0; k <s.gameState2s.size() ; k++) {
                            if (Objects.equals(s.gameState2s.get(k).Name, f[i])) {
                                f2 = true;
                                break;
                            }
                        }
                        if (!f2  && i==1){
                        s.gameState2s.add(new GameState2(0,true,f[i]))    ;
                        }else if (!f2){
                            s.gameState2s.add(new GameState2(i-1,false,f[i]))    ;
                        }

                    }
                    ClientGameTheard s2=new ClientGameTheard();
                    s2.run(s,channel,name);


            }

        };
        Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Register Deliver Callback...");
        //Associate callback with channel queue
        channel.basicConsume(pass, true, deliverCallback, consumerTag -> {
        });

    }

}
