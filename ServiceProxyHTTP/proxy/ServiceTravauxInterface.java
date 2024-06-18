import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceTravauxInterface extends Remote {
    void lancer(String url) throws RemoteException;
}