package edu.ufp.inf.sd.rmi._03_pingpong.server;

import edu.ufp.inf.sd.rmi._03_pingpong.client.PongImpl;
import edu.ufp.inf.sd.rmi.util.threading.ThreadPool;

import java.util.Random;

public class SampleThread {
    public static void main(String[] args) {
        RandThread p1 = new RandThread();
        p1.start();

        RandRun p2 = new RandRun();
        new Thread(p2).start();

        ThreadPool threadPool = new ThreadPool(10);
        RandRun p3 = new RandRun();
        threadPool.execute(p3);
    }
}


class RandThread extends Thread {
    @Override
    public void run() {
        int randVal;

        randVal = Math.abs(new Random().nextInt(99) + 1);
        System.out.println(randVal);
    }
}


class RandRun implements Runnable {
    public void run() {
        int randVal;

        randVal = Math.abs(new Random().nextInt(99) + 1);
        System.out.println(randVal);
    }
}

