package edu.ufp.inf.sd.rmi.Project.server;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.sun.jmx.snmp.SnmpOidTableSupport;
import edu.ufp.inf.sd.rmi.Project.client.ProjectClientRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ProjectMainImpl extends UnicastRemoteObject implements ProjectMainRI {
   DB Database=new DB();
   HashMap<Util,GameSessionRI> users = new HashMap<>();
   ArrayList<FroggerGame> Game=new ArrayList<>();
    public ProjectMainImpl() throws RemoteException {
        super();
    }


    @Override
    public String Connect()  throws RemoteException {
        return "\nHello, Welcome to City 17 you have chosen or been Chosen to...\n" +"Select An option:\n" + "1-Register\n" + "2-Login In\n" + "3-Leave\n";
    }
    public boolean Register(String Username, String Password,ProjectClientRI projectClientRI) throws RemoteException{
            return Database.Insert_Util(Username, Password,projectClientRI);
    }

    @Override
    public GameSessionRI Login(String Email, String Password,ProjectClientRI projectClientRI) throws RemoteException {
        if (Database.Check_Util(Email)){
            if (users.get(new Util(Email,Password,projectClientRI))==null){
                try {
                    Algorithm algorithm = Algorithm.HMAC256(Email+Password);
                    String token = JWT.create().withIssuer("auth0").sign(algorithm);
                GameSessionRI j=new GameSessionImpl(this,new Util(Email,Password,projectClientRI),token);
                users.put(new Util(Email,Password,projectClientRI),j);

                return j;
                } catch (JWTCreationException exception){
                   return null;
                }
            }
            System.out.println("DO\n");
            return users.get(new Util(Email,Password,projectClientRI));
        }
        return null;

    }
    protected Boolean Valid(String Token,Util j) throws RemoteException{
        for (GameSessionRI k:this.users.values()) {
            if (Objects.equals(j.getPassword(), k.getUtil().getPassword())&& Objects.equals(j.getEmail(), k.getUtil().getEmail()))
            try {
                Algorithm algorithm = Algorithm.HMAC256(j.getEmail()+j.getPassword());
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("auth0")
                        .build(); //Reusable verifier instance
                DecodedJWT jwt = verifier.verify(Token);
                return true;
            } catch (JWTVerificationException exception){
            return false;
            }



        }

        return false;
    }

}
