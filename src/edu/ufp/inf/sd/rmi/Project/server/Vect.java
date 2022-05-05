package edu.ufp.inf.sd.rmi.Project.server;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Vect implements Serializable {
  double x;
    double y;

    public Vect(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() throws RemoteException {
        return x;
    }

    public void setX(double x) throws RemoteException{
        this.x = x;
    }

    public double getY() throws RemoteException{
        return y;
    }

    public void setY(double y)throws RemoteException {
        this.y = y;
    }
}
