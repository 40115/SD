package edu.ufp.inf.sd.rmi.Project.server;

import java.io.Serializable;
import java.util.ArrayList;

public class Road_Line implements Serializable {

ArrayList<Integer> types=new ArrayList<>();
    int nriverline;

    public Road_Line(ArrayList<Integer> type,   int nriverline) {
        this.types = type;
        this.nriverline = nriverline;
    }

    public ArrayList<Integer> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Integer> types) {
        this.types = types;
    }




    public int getNriverline() {
        return nriverline;
    }

    public void setNriverline(int nriverline) {
        this.nriverline = nriverline;
    }
}
