package edu.ufp.inf.sd.rmi.Project.server;

import java.io.Serializable;
import java.util.ArrayList;

public class Road_Line implements Serializable {
    ArrayList<Integer> types;
    int nriverline;

    public Road_Line(ArrayList<Integer> type, int nriverline) {
        this.types = type;
        this.nriverline = nriverline;
    }

    public ArrayList<Integer> getTypes() {
        return types;
    }
}
