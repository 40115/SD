package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

/**
 * 1. Run the rabbitmq server as a shell process, by calling:
 * $ rabbitmq-server
 *
 * <p>
 * 2. Run consumer Recv that keeps running, waiting for messages from publisher Send (Use Ctrl-C to stop):
 * $ ./runconsumer
 *
 * <p>
 * 3. Run publisher Send several times from terminal (will send mesg "hello world"):
 * $ ./runproducer
 *
 * <p>
 * 4. Check RabbitMQ Broker runtime info (credentials: guest/guest4rabbitmq) from:
 * http://localhost:15672/
 *
 * @author rui
 */

import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rabbitmqservices.Project.producer.Cliente;
import edu.ufp.inf.sd.rabbitmqservices.util.RabbitUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Frontend {

    /**
     * Run consumer Recv that keeps running, waiting for messages from publisher Send (Use Ctrl-C to stop):
     * $ ./runconsumer
     *
     * @param args
     */

    public static void main(String[] args) throws IOException, TimeoutException {
        DB2 database=new DB2();
        try {
            RabbitUtils.printArgs(args);

            //Read args passed via shell command
            String host=args[0];
            int port=Integer.parseInt(args[1]);
            String queueName=args[2];

            // Open a connection and a channel to rabbitmq broker/server
            Connection connection=RabbitUtils.newConnection2Server(host, port, "guest", "guest");
            Channel channel=RabbitUtils.createChannel2Server(connection);
            queueName="Front";
            //Declare queue from which to consume (declare it also here, because consumer may start before publisher)
            channel.queueDeclare(queueName, true, false, false, null);
            //channel.queueDeclare(Send.QUEUE_NAME, true, false, false, null);
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Will create Deliver Callback...");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            // Publisher pushes messages asynchronously, hence use DefaultConsumer callback
            //   that buffers messages until consumer is ready to handle them.
            /*Consumer client = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message=new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            };
            channel.basicConsume(queueName, true, client);
            */


            boolean run = true;

            //DeliverCallback is an handler callback (lambda method) to consume messages pushed by the sender.
            //Create an handler callback to receive messages from queue
            DeliverCallback deliverCallback=(consumerTag, delivery) -> {
                String message=new String(delivery.getBody(), StandardCharsets.UTF_8);
                Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Message received " +message);
                System.out.println(" [x] Received '" + message + "'");
                String[] decompiler=message.split("\\|");
System.out.println(Arrays.toString(decompiler));
                switch (decompiler[0]){
                    case "I":
                        if (database.Insert_Util(decompiler[1],decompiler[2])){
                            channel.queueDeclare(decompiler[2], true, false, false, null);
                            //channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                            String messageI="I|ACK";
                            // Publish a message to the queue (content is byte array encoded with UTF-8)
                            channel.basicPublish("", decompiler[2], null, messageI.getBytes(StandardCharsets.UTF_8));
                            System.out.println(" [x] Sent '" + messageI + "'");

                        }else{
                            channel.queueDeclare(decompiler[2], true, false, false, null);
                            //channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                            String messageI="I|NACK";
                            // Publish a message to the queue (content is byte array encoded with UTF-8)
                            channel.basicPublish("", decompiler[2], null, messageI.getBytes(StandardCharsets.UTF_8));
                            System.out.println(" [x] Sent '" + messageI + "'");

                        }
                        break;
                    case "IS":
                        if (database.Insert_Server(decompiler[1],decompiler[2])){
                            channel.queueDeclare(decompiler[2], true, false, false, null);
                            //channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                            String messageI="IS|ACK|"+decompiler[2];
                            // Publish a message to the queue (content is byte array encoded with UTF-8)
                            channel.basicPublish("", decompiler[2], null, messageI.getBytes(StandardCharsets.UTF_8));
                            System.out.println(" [x] Sent '" + messageI + "'");
                        }else{
                            channel.queueDeclare(decompiler[2], true, false, false, null);
                            //channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                            String messageI="IS|NACK|"+decompiler[2];
                            // Publish a message to the queue (content is byte array encoded with UTF-8)
                            channel.basicPublish("", decompiler[2], null, messageI.getBytes(StandardCharsets.UTF_8));
                        }
break;
                    case "L":
                        StringBuilder a=new StringBuilder();
                        a.append("List of available games");
                        for (int i = 0; i <database.getGames().size() ; i++) {
                            a.append(i);
                            for (int j = 0; j <database.getGames().get(i).gameState2s.size() ; j++) {
                                a.append(database.getGames().get(i).gameState2s.get(j).Name).append(",");
                            }
                            a.append("\n");

                        }
                        String mess="L|"+ a +"|"+decompiler[2];
                        channel.basicPublish("", decompiler[2], null, mess.getBytes(StandardCharsets.UTF_8));
                        break;
                    case "J":
                        String[] res=decompiler[1].split(",");
                        if (!database.Check_Util(res[0])){
                            String mess2="J1|NACK"+"|"+decompiler[2];
                            channel.basicPublish("", decompiler[2], null, mess2.getBytes(StandardCharsets.UTF_8));

                        }else if (!database.Check_Games(Integer.parseInt(res[1]))){
                            String mess2="J1|NACK"+"|"+decompiler[2];
                            channel.basicPublish("", decompiler[2], null, mess2.getBytes(StandardCharsets.UTF_8));
                        }else {
                            String mess2="J1|ACK"+"|"+decompiler[2];
                            channel.basicPublish("", decompiler[2], null, mess2.getBytes(StandardCharsets.UTF_8));
                        }
break;
                    case "C":
                        //C|Ruben|queuename
                        //"C1|Ruben,routingkey|queuename
                        if (!database.Check_Util(decompiler[1])||database.Check_Games_Players(decompiler[1])||database.Database_SERVERS.size()==0) {
                            String mess2 = "C1|NACK" + "|" + decompiler[2];
                            channel.basicPublish("", decompiler[2], null, mess2.getBytes(StandardCharsets.UTF_8));
                        }else {
                            String exchangename= Cliente.generate_Random_String(14);
                            FroggerGame2 h=new FroggerGame2(database.id_Tot,exchangename);
                            h.gameState2s.add(new GameState2(0,true,decompiler[1]));
                            database.Games.add(h);
                            database.id_Tot++;
                            System.out.println(" [x] Creating '" + exchangename + "'"+ BuiltinExchangeType.FANOUT.toString());
                            channel.exchangeDeclare(exchangename,BuiltinExchangeType.FANOUT);
                            String routingkey="";
                            channel.basicPublish(exchangename,routingkey,null,message.getBytes(StandardCharsets.UTF_8));
                            System.out.println("Sent"+ message+"?00");
                            String mess2="C1|ACK,"+"|"+decompiler[2];
                            channel.basicPublish("", decompiler[2], null, mess2.getBytes(StandardCharsets.UTF_8));

                        }
break;
                        }
                while (!run){
                    try {
                        long sleepMillis = 2000;
                        Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": sleep " +sleepMillis);
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            };
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Register Deliver Callback...");
            //Associate callback with channel queue
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });

        } catch (Exception e) {
            //Logger.getLogger(Recv.class.getName()).log(Level.INFO, e.toString());
            e.printStackTrace();
        }
    }
}
