package edu.ufp.inf.sd.rmi._02_calculator.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculatorImpl extends UnicastRemoteObject implements CalculatorRI{
    public CalculatorImpl() throws RemoteException {
        super();
    }

    @Override
    public double add(double a, double b) throws RemoteException {
     return a+b;
        }
    public double add(ArrayList<Double> list) throws RemoteException {
double soma=0.0;
        for (double ci:list) {
            soma=ci+soma;
        }
        return soma;
    }
    public double div(double a, double b) throws RemoteException {
        if(b==0){
            System.out.print("Cant div");
            return 0.0;
        }
        return a/b;
    }

    @Override
    public double mult(double a, double b) throws RemoteException {
        System.out.print(a*b );

        return a*b;
    }

    @Override
    public double mult(ArrayList<Double> list) throws RemoteException {
        double soma=1;
        for (double ci:list) {
            soma=ci*soma;
        }

        return soma;
    }

    @Override
    public double sub(double a, double b) throws RemoteException {
        return a-b;
    }
}
