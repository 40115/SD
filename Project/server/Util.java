package edu.ufp.inf.sd.rmi.Project.server;

import edu.ufp.inf.sd.rmi.Project.client.ProjectClientClient;
import edu.ufp.inf.sd.rmi.Project.client.ProjectClientRI;

public class Util {
    private String Email;
    private String Password;
    private ProjectClientRI ProjectClientRI;

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
