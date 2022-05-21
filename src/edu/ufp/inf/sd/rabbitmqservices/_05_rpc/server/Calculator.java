package edu.ufp.inf.sd.rabbitmqservices._05_rpc.server;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class Calculator {

    /**
     *
     * @param n
     * @return
     */
    protected static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n - 1) + fib(n - 2);
    }

    /**
     *
     * @param numbers
     * @return
     */
    protected static double add(ArrayList<Double> numbers){
        double res=0.0;
        for (double n:numbers){
            res+=n;
        }
        return res;
    }
    protected static double Sub(ArrayList<Double> numbers){
        double res=0.0;
        for (double n:numbers){
            res-=n;
        }
        return res;
    }

    /**
     *
     * @param message
     * @return
     */
    public static String calculate(String message){
        String response = "";
        JSONObject jsonRequest = new JSONObject(message);
        String op = jsonRequest.getString("operation");
        JSONArray values = jsonRequest.getJSONArray("values");
        ArrayList<Double> ald = new ArrayList<>();
        for(int i=0; i<values.length(); i++){
            ald.add(values.getDouble(i));
        }
        switch (op){
            case "add":
                response += "{\"result\","+ Calculator.add(ald)+"}";
                break;
            case "sub":
                response += "{\"result\","+ Calculator.Sub(ald)+"}";
                break;
            case "mult":
                response += "{\"result\","+ Calculator.mult(ald)+"}";
                break;
            case "div":
                response += "{\"result\","+ Calculator.div(ald)+"}";
                break;
            default:return String.valueOf(0.0);
        }
        return response;
    }

    private static double mult(ArrayList<Double> numbers) {
        double res=1.0;
        for (double n:numbers){
            res*=n;
        }
        return res;

    }
    private static double div(ArrayList<Double> numbers) {
        double res=1.0;
        for (double n:numbers){
            if (n==0) return 0.0;
            res/=n;
        }
        return res;

    }
}
