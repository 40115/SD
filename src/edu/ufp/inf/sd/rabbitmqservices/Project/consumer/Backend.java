package edu.ufp.inf.sd.rabbitmqservices.Project.consumer;

import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rabbitmqservices.util.RabbitUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.ufp.inf.sd.rabbitmqservices.Project.producer.Cliente.generate_Random_String;

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

        //Read args passed via shell command
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String queueName = args[2];
        AtomicReference<String> pass= new AtomicReference<>(generate_Random_String(12));

        queueName="Front";
        boolean run = true;
        /* try-with-resources will close resources automatically in reverse order... avoids finally */
        Connection connection = RabbitUtils.newConnection2Server(host, port, "guest", "guest");
        Channel channel = RabbitUtils.createChannel2Server(connection) ;
        // Declare a queue where to send msg (idempotent, i.e., it will only be created if it doesn't exist);
        channel.queueDeclare(queueName, true, false, false, null);
        //channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueDeclare(pass.get(), true, false, false, null);
        System.out.println("Insert Name:\n");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        final String name = reader.readLine();
        // Publish a message to the queue (content is byte array encoded with UTF-8)
        AtomicReference<String> messageI = new AtomicReference<>("IS|"+name+"|" + pass);
        channel.basicPublish("", queueName, null, messageI.get().getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + messageI + "'");

        DeliverCallback deliverCallback=(consumerTag, delivery) -> {
            String message=new String(delivery.getBody(), "UTF-8");
            Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Message received " +message);
            System.out.println(" [x] Received '" + message + "'");
            String[] decompiler=message.split("\\|");
            switch (decompiler[0]){
                case "IS":
                    if (Objects.equals(decompiler[1], "ACK")){
                        channel.queueDeclare(pass.get(), true, false, false, null);
                    }else {
                        System.out.println("ERRO NAME already pro exist :\n");
pass.set(decompiler[2]);

                    }
                    break;


            }

        };
        Logger.getAnonymousLogger().log(Level.INFO, Thread.currentThread().getName()+": Register Deliver Callback...");
        //Associate callback with channel queue
        channel.basicConsume(pass.get(), true, deliverCallback, consumerTag -> {
        });



    }
}
