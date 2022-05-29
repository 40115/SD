package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientGameTheard extends Thread{
    public ClientGameTheard() {
        super();
    }


    public void run(FroggerGame2 g, Channel channel,String Name) throws IOException {
        channel.exchangeDeclare(g.exhange_name, BuiltinExchangeType.FANOUT);


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
        channel.queueBind(queuename,g.exhange_name,routingkey);
        Logger.getAnonymousLogger().log(Level.INFO,Thread.currentThread().getName()+":will create callback");
        System.out.println("waiting msg");
        //DeliverCallback is an handler callback (lambda method) to consume messages pushed by the sender.
        //Create an handler callback to receive messages from queue
ClientGame h1=new ClientGame();
GameState2 fq = null;
        for (int i = 0; i <g.gameState2s.size() ; i++) {
            if (Objects.equals(g.gameState2s.get(i).Name, Name)) {fq=g.gameState2s.get(i);break;}

        }
h1.run(fq,g);
        DeliverCallback deliverCallback=(consumerTag, delivery) -> {
            String message=new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +consumerTag+ "Message"+ message + "'");
            String[] decompiler=message.split("\\|");
            switch (decompiler[0]) {
                case "B":

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
