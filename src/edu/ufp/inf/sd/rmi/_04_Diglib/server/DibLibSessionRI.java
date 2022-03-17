package edu.ufp.inf.sd.rmi._04_Diglib.server;

import java.rmi.Remote;

public interface DibLibSessionRI extends Remote {
    public Book[] Search(String Author, String Title);
    public void Logout();


}
