package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientGameTheard extends Thread{
    public ClientGameTheard() {
        super();
    }


    public void run(String exchange, Channel channel,String Name,GameState2 j) throws IOException {
        channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT);
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

        String queuename=channel.queueDeclare().getQueue();
        String routingkey="";
        channel.queueBind(queuename,exchange,routingkey);
        Logger.getAnonymousLogger().log(Level.INFO,Thread.currentThread().getName()+":will create callback");
        System.out.println("waiting msg");
        //DeliverCallback is an handler callback (lambda method) to consume messages pushed by the sender.
        //Create an handler callback to receive messages from queue
        DeliverCallback deliverCallback=(consumerTag, delivery) -> {
            String message=new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +consumerTag+ "Message"+ message + "'");
            String[] decompiler=message.split("\\|");
            switch (decompiler[0]) {
                case "B":
                    int id;
                    do{
                        BufferedReader reader=new BufferedReader(  new InputStreamReader(System.in));
                        id= reader.read();


                    }while(id!=1);
                      break;

                case "ST":



            }

        };
        CancelCallback cancelCallback=(consumerTag)->{
            System.out.println(" [x] Received '" +consumerTag+ "Cancel" );
        };
        channel.basicConsume(queuename,true,deliverCallback,cancelCallback);


    }
}
