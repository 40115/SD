package edu.ufp.inf.sd.rmi.Project.server;

public class Util {
    private String Email;
    private String Password;


    public Util(String Email, String password) {
        Email = Email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
