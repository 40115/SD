package edu.ufp.inf.sd.rmi.Project.server;

import edu.ufp.inf.sd.rmi.Project.client.ProjectClientRI;

import java.rmi.Remote;

public interface UtilRI extends Remote {
    public String getEmail();
    public String getPassword();
    public ProjectClientRI getProjectClientRI();
}
