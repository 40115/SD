package edu.ufp.inf.sd.rmi.Project.server;
import java.util.ArrayList;
import java.sql.*;
public class DB {
    private final ArrayList<Util> users;// = new ArrayList();
    Connection con = null;
    public DB(ArrayList<Util> users) {
        this.users = users;

    }
     public Statement connection()  {
 try {
     Class.forName("com.mysql.jdbc.Driver");
     Connection con = DriverManager.getConnection(
             "jdbc:mysql://localhost:3307/marta", "root", "JaneMarta123_");
     return con.createStatement();
 } catch (SQLException | ClassNotFoundException e) {
     e.printStackTrace();
 }
         return null;
     }
    public ArrayList GetQuery(String Quer) {
     Statement k= this.connection();
     if(k!=null) {
         System.out.println(k.getClass());
         return null;
     }
     System.out.println("null");
        return null;
    }

}
