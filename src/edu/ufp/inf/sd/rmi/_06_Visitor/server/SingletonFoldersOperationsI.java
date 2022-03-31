package edu.ufp.inf.sd.rmi._06_Visitor.server;

import java.rmi.Remote;

public interface SingletonFoldersOperationsI extends Remote {
    Boolean createFile(String fname);

    Boolean deleteFile(String fname);
}
