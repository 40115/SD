package edu.ufp.inf.sd.rmi.Project.server;

import edu.ufp.inf.sd.rmi.Project.client.ProjectClientRI;

import java.io.Serializable;

public class Util implements Serializable {
    private final String Email;
    private final String Password;
    private final ProjectClientRI ProjectClientRI;

    public Util(String Email, String password,ProjectClientRI projectClientRI) {
        this.Email = Email;
        this.Password = password;
        this.ProjectClientRI=projectClientRI;
    }

    public String getEmail() {
        return Email;
    }


    public String getPassword() {
        return Password;
    }

    public ProjectClientRI getProjectClientRI() {
        return ProjectClientRI;
    }


}
