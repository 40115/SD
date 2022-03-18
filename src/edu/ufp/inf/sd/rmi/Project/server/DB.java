package edu.ufp.inf.sd.rmi.Project.server;
import java.util.ArrayList;
import java.sql.*;
public class DB {
    private final ArrayList<Util> users;// = new ArrayList();
    Connection con = null;
    public DB(ArrayList<Util> users) {
        this.users = users;

    }
     public Statement connection() throws SQLException {
         Connection con = DriverManager.getConnection (
                 "jdbc:mysql://localhost:3306/database","root", " ");
         Statement stmt = con.createStatement();
return stmt;
     }
    public ArrayList GetQuery(String Quer) throws SQLException {
     Statement k= this.connection();
     if(k!=null) {
         System.out.println(k.getClass());
     }
     System.out.println("null");
        return null;
    }

}
