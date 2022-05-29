package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rabbitmqservices.util.RabbitUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * RabbitMQ speaks multiple protocols, e.g., AMQP, which is an open and
 * general-purpose protocol for messaging.
 * <p>
 * There are a number of clients for RabbitMQ in many different languages.
 * We will use the Java client (amqp-client-5.9.0.jar and dependencies) provided by RabbitMQ.
 * Download most recent libraries:
 * client library (amqp-client-x.y.z.jar) and dependencies (SLF4J API and SLF4J Simple)
 * and copy them into *lib* directory.
 *
 * <p>
 * Jargon terms:
 * - RabbitMQ is a message broker, i.e., a server that accepts and forwards messages.
 * - Producer: program that sends messages (Producing means sending).
 * - Queue: post box which lives inside a RabbitMQ broker (large message buffer).
 * - Consumer: program that waits to receive messages (Consuming means receiving).
 * <p>
 * NB: the producer, consumer and broker do not have to run/reside on the same host.
 *
 * @author rui
 */
public class Backend {

    /*+ name of the queue */
    //public final static String QUEUE_NAME="hello_queue";

    /**
     * Run publisher Send several times from terminal (will send msg "hello world" to Recv):
     * $ ./runproducer
     *
     * Challenge: concatenate several words from command line args (args[3].. args[n]):
     * $ ./runcnsumer hello world again (will concatenate msg "hello world again" to send)
     *
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitUtils.printArgs(args);
        DB2 database=new DB2();
        //Read args passed via shell command
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String Front = "Front";
        String Back="Back";
        String Workqueue="Workqueue";
        /* try-with-resources will close resources automatically in reverse order... avoids finally */
        Connection connection = RabbitUtils.newConnection2Server(host, port, "guest", "guest");
        Channel channel = RabbitUtils.createChannel2Server(connection) ;
        /*
        declarations of queues and exchanges
         */
        channel.exchangeDeclare(Back,BuiltinExchangeType.FANOUT);
        String pass=channel.queueDeclare().getQueue();
        String routingkey="";
        channel.queueBind(pass,Back,routingkey);
        channel.queueDeclare(Front, true, false, false, null);
        channel.queueDeclare(Workqueue, true, false, false, null);
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        System.out.println("Insert Name:\n");
        final BufferedReader[] reader = {new BufferedReader(
                new InputStreamReader(System.in))};

        // Reading data using readLine
        final String[] name = {reader[0].readLine()};

        AtomicReference<String> messageI = new AtomicReference<>("IS|" + name[0] + "|" + pass);
        channel.basicPublish("", Front, null, messageI.get().getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + messageI + "'");
        DeliverCallback deliverCallback=(consumerTag, delivery) -> {
            String message=new String(delivery.getBody(), StandardCharsets.UTF_8);
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Message received " +message);
            System.out.println(" [x] Received '" + message + "'");
            String[] decompiler=message.split("\\|");
            switch (decompiler[0]){
                case "I":
                    //I|RUBEN|que
                    database.Insert_Util(decompiler[1],decompiler[2]);
                    break;
                case "IS":

                    if (Objects.equals(decompiler[1], "ACK"+","+name[0]) ){
                        System.out.println("System acknonalege\n");
                    }
                    break;
                case "C":
                    //mess2 ="C|"+decompiler[1]+","+(database.id_Tot-1)+","+exchangename + "|" + decompiler[2];
                    String[] mesg=decompiler[1].split(",");
                    FroggerGame2 d = new FroggerGame2(Integer.parseInt(mesg[1]), mesg[2],channel);
                    GameState2 h=new GameState2(Integer.parseInt(mesg[1]),true,mesg[0]);
                    d.gameState2s.add(h);
                    database.Games.add(d);
                    System.out.println(message);
                    break;

                case "J":
                    //J|1,Ruben,exchange,1|queuename
                     mesg=decompiler[1].split(",");
                     int h1=Integer.parseInt(mesg[0]);
                     FroggerGame2 d1=null;
                    for (int i = 0; i <database.Games.size() ; i++) {
                        if (database.Games.get(i).Id==h1) {d1=database.Games.get(i);
                        break;
                        }
                    }
                    if (d1==null) break;
                    GameState2 h2=new GameState2(h1,false,mesg[0]);
                    d1.gameState2s.add(h2);
                    break;
                case "R1":
                    mesg=decompiler[1].split(",");
                    FroggerGame2 f=null;
                    for (int i = 0; i <database.Games.size() ; i++) {
                        if (database.Games.get(i).Id==Integer.parseInt(mesg[0])){
                            f=database.Games.get(i);
                        }
                    }
                     if (f==null){
                         f=new FroggerGame2(Integer.parseInt(mesg[0]),decompiler[2],channel );
                     }
                    for (int i = 1; i <mesg.length ; i++) {
                        boolean fa=false;
                        for (int j = 0; j <f.gameState2s.size() ; j++) {
                            if (Objects.equals(f.gameState2s.get(j).Name, mesg[i])) {
                                fa = true;
                                break;
                            }
                        }
                        if (!fa){
                            if (i == 1) {
                                f.gameState2s.add(new GameState2(0, true, mesg[i]));
                            } else {
                                f.gameState2s.add(new GameState2(i, false, mesg[i]));

                            }
                        }
                    }
                    BackendTHread tHread=new BackendTHread();
                    tHread.run(channel,f.exhange_name, name[0],f);
                    break;

            }

        };
        DeliverCallback deliverCallback2=(consumerTag, delivery) -> {
            String message=new String(delivery.getBody(), StandardCharsets.UTF_8);
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Message received " +message);
            System.out.println(" [x] Received '" + message + "'");
            String[] decompiler=message.split("\\|");
            switch (decompiler[0]){

                case "RIVER":
                case "ROADS":
                    //0 section 1 type
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
                        if (Objects.equals(d.gameState2s.get(i).Name, decompiler[2])){
                            if (d.gameState2s.get(i).isMAster()){
                                String msg=decompiler[0]+"|"+mesg[0]+","+mesg[1]+","+ Arrays.toString(name);
                                channel.basicPublish(d.exhange_name, "",null,msg.getBytes(StandardCharsets.UTF_8));
                                break;
                            }

                        }
                    }
                    break;

            }

        };
        Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Register Deliver Callback...");
        //Associate callback with channel queue
        channel.basicConsume(pass, true, deliverCallback, consumerTag -> {
        });

        channel.basicConsume(Workqueue, false, deliverCallback2, consumerTag -> {
        });

    }
}
