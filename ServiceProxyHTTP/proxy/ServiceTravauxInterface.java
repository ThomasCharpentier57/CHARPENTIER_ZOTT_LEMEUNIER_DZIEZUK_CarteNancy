package proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceTravauxInterface extends Remote {
    String lancer(String url) throws RemoteException;
}