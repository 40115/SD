package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import edu.ufp.inf.sd.rabbitmqservices.Project.producer.Cliente;
import edu.ufp.inf.sd.rabbitmqservices.util.RabbitUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Frontend {

    /**
     * Run consumer Recv that keeps running, waiting for messages from publisher Send (Use Ctrl-C to stop):
     * $ ./runconsumer
     *
     */

    public static void main(String[] args) throws IOException, TimeoutException {
        DB2 database=new DB2();
        try {
            RabbitUtils.printArgs(args);

            //Read args passed via shell command
            String host=args[0];
            int port=Integer.parseInt(args[1]);
            String Front = "Front";
            String Back="Back";
            String Workqueue="Workqueue";
            // Open a connection and a channel to rabbitmq broker/server
            Connection connection=RabbitUtils.newConnection2Server(host, port, "guest", "guest");
            Channel channel=RabbitUtils.createChannel2Server(connection);
            //Declare queue from which to consume (declare it also here, because consumer may start before publisher)
            channel.exchangeDeclare(Back,BuiltinExchangeType.FANOUT);
            channel.queueDeclare(Front, true, false, false, null);
            channel.queueDeclare(Workqueue, true, false, false, null);
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
                            String messageII="I|"+decompiler[1]+"|"+decompiler[2];
                            // Publish a message to the queue (content is byte array encoded with UTF-8)
                            String routingkey="";
                            channel.basicPublish(Back,routingkey, null, messageII.getBytes(StandardCharsets.UTF_8));

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
                            //channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                            String messageI="IS|ACK"+","+decompiler[1]+"|"+Back;
                            // Publish a message to the queue (content is byte array encoded with UTF-8)
                            String routingkey="";
                            channel.basicPublish(Back,routingkey, null, messageI.getBytes(StandardCharsets.UTF_8));
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
                        //J|Ruben,1|queuename
                        String[] res=decompiler[1].split(",");
                        if (!database.Check_Util(res[0])){
                            System.out.println("Sente Nack");
                            String mess2="J1|NACK"+"|"+decompiler[2];
                            channel.basicPublish("", decompiler[2], null, mess2.getBytes(StandardCharsets.UTF_8));

                        }else if (!database.Check_Games(Integer.parseInt(res[1]))){
                            System.out.println("Sente Nack2");
                            String mess2="J1|NACK"+"|"+decompiler[2];
                            channel.basicPublish("", decompiler[2], null, mess2.getBytes(StandardCharsets.UTF_8));
                        }else {
                            //J1|ACK,id,name,1|dec
                            FroggerGame2 f1=database.Games.get(Integer.parseInt(res[1]));
                            String mess2="J1|ACK,"+Integer.parseInt(res[1])+","+res[0]+","+f1.exhange_name+",1"+"|"+decompiler[2];
                            f1.gameState2s.add(new GameState2(f1.Id,false,res[0]));
                            channel.basicPublish("",decompiler[2] , null, mess2.getBytes(StandardCharsets.UTF_8));
                            String mess3="J1|"+f1.Id+","+f1.exhange_name+",1"+"|"+decompiler[2];
                            channel.basicPublish(Back,"" , null, mess3.getBytes(StandardCharsets.UTF_8));
                        }
break;
                    case "C":
                        //C|Ruben|queuename
                        //"C1|Ruben,routingkey|queuename
                    //C|Ruben,4,exchagename|route
                        if (!database.Check_Util(decompiler[1])||database.Check_Games_Players(decompiler[1])||database.Database_SERVERS.size()==0) {
                            String mess2 = "C1|NACK" + "|" + decompiler[2];
                            channel.basicPublish("", decompiler[2], null, mess2.getBytes(StandardCharsets.UTF_8));
                        }else {
                            String exchangename= Cliente.generate_Random_String(14);
                            FroggerGame2 h=new FroggerGame2(database.id_Tot,exchangename,channel);
                            h.gameState2s.add(new GameState2(0,true,decompiler[1]));
                            database.Games.add(h);
                            database.id_Tot++;
                            System.out.println(" [x] Creating '" + exchangename + "'"+ BuiltinExchangeType.FANOUT);
                            channel.exchangeDeclare(exchangename,BuiltinExchangeType.FANOUT);
                            String mess2 = "C1|ACK,"+exchangename+","+h.Id + "|" + decompiler[2];
                            channel.basicPublish("", decompiler[2], null, mess2.getBytes(StandardCharsets.UTF_8));
                            mess2 = "C|"+decompiler[1]+","+h.Id+","+exchangename + "|" + decompiler[2];
                            channel.basicPublish(Back, "", null, mess2.getBytes(StandardCharsets.UTF_8));
                            System.out.println("Sent"+ mess2);
                        }
break;
                    case "R":
                        //R|1,Ruben|queuename
                       String[] mesg=decompiler[1].split(",");
                        FroggerGame2 d2=null;

                        for (int i = 0; i <database.Games.size() ; i++) {
                            System.out.println(database.Games.get(i).Id);
                            if (database.Games.get(i).Id==Integer.parseInt(mesg[0])){
                                d2=database.Games.get(i);
                            }
                        }
                        if (d2==null) break;
                        for (int i = 0; i <d2.gameState2s.size() ; i++) {
                            if (Objects.equals(d2.gameState2s.get(i).Name, mesg[1])){
                                d2.gameState2s.get(i).setReady(true);
                                break;
                            }
                        }

                        if (d2.check_Ready()){
                         String a1=d2.Construct_Message();
                            for(String routes:database.Database.keySet()){
                                for (int i = 0; i <d2.gameState2s.size() ; i++) {
                                    if (Objects.equals(d2.gameState2s.get(i).Name, routes)) channel.basicPublish("", database.Database.get(routes), null, a1.getBytes(StandardCharsets.UTF_8));

                                }
                            }
                          channel.basicPublish(Back,"",null,message.getBytes(StandardCharsets.UTF_8));
                        }
                        break;
                    case "RIVER":
                    case "ROADS":
                        //0 section 1 type
                        //						 msg="ROADS|0,1,"+vd.Name+","+game2.getId();
                         mesg=decompiler[1].split(",");
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
                                    channel.basicPublish("",Workqueue,null,message.getBytes(StandardCharsets.UTF_8));
break;
                                }

                            }
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
            channel.basicConsume(Front, true, deliverCallback, consumerTag -> {
            });
// MessageProperties.PERSISTENT_TEXT_PLAIN

        } catch (Exception e) {
            //Logger.getLogger(Recv.class.getName()).log(Level.INFO, e.toString());
            e.printStackTrace();
        }


    }

}
