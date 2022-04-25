package edu.ufp.inf.sd.rmi.Project.server;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import edu.ufp.inf.sd.rmi.Project.client.ProjectClientRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ProjectMainImpl extends UnicastRemoteObject implements ProjectMainRI {
   DB Database=new DB();
   HashMap<Util,GameSessionRI> users = new HashMap<>();
   ArrayList<FroggerGameRI> Game=new ArrayList<>();
    public ProjectMainImpl() throws RemoteException {
        super();
    }


    @Override
    public String Connect()  throws RemoteException {
        return "\nHello, Welcome to City 17 you have chosen or been Chosen to...\n" +"Select An option:\n" + "1-Register\n" + "2-Login In\n" + "3-Leave\n";
    }
    public boolean Register(String Username, String Password,ProjectClientRI projectClientRI) throws RemoteException{
            return Database.Insert_Util(Username, Password);
    }

    @Override
    public GameSessionRI Login(String Email, String Password,ProjectClientRI projectClientRI) throws RemoteException {

        if (Database.Check_Util(Email,Password)){

            for (Util l:this.users.keySet()) {
                if (Objects.equals(l.getEmail(), Email) && Objects.equals(l.getPassword(), Password)){
                    return this.users.get(l);
                }

            }

            try {
                Algorithm algorithm = Algorithm.HMAC256(Email+Password);
                String token = JWT.create().withIssuer("auth0").sign(algorithm);
                Util aj=new Util(Email,Password,projectClientRI);
                GameSessionRI j=new GameSessionImpl(this,aj,token);
                this.users.put(aj,j);
              aj.getProjectClientRI().test();
                return j;
            } catch (JWTCreationException exception){
                return null;
            }
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
    protected void Remove_Game(FroggerGameRI h) throws RemoteException{
        for (int i = 0; i <this.Game.size() ; i++) {
            if (h==this.Game.get(i)){
                this.Game.remove(i);
                return;
            }

        }
    }

}
