package edu.ufp.inf.sd.rabbitmqservices.Project.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import edu.ufp.inf.sd.rabbitmqservices.Project.consumer.Frontend;
import edu.ufp.inf.sd.rabbitmqservices._01_hello.producer.Send;
import edu.ufp.inf.sd.rabbitmqservices._03_pubsub.producer.EmitLog;
import edu.ufp.inf.sd.rabbitmqservices._05_rpc.client.RPCClient;
import edu.ufp.inf.sd.rabbitmqservices.util.RabbitUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) throws IOException {
        RabbitUtils.printArgs(args);

        //Read args passed via shell command
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String queueName = args[2];
        System.out.println("Insert Name:\n");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        String name = reader.readLine();
        //try-with-resources...
        String messageI = "I|" + name;
        queueName="Back";
        /* try-with-resources will close resources automatically in reverse order... avoids finally */
        try (Connection connection = RabbitUtils.newConnection2Server(host, port, "guest", "guest");
             Channel channel = RabbitUtils.createChannel2Server(connection)) {
            // Declare a queue where to send msg (idempotent, i.e., it will only be created if it doesn't exist);
            channel.queueDeclare(queueName, true, false, false, null);
            //channel.queueDeclare(QUEUE_NAME, true, false, false, null);


            // Publish a message to the queue (content is byte array encoded with UTF-8)
            channel.basicPublish("", queueName, null, messageI.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + messageI + "'");


        } catch (IOException | TimeoutException e) {
            Logger.getLogger(Send.class.getName()).log(Level.INFO, e.toString());
        }
    }
}
