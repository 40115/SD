package edu.ufp.inf.sd.rmi.Project.server;

public class Util {
    private String Username;
    private String Password;


    public Util(String username, String password) {
        Username = username;
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
