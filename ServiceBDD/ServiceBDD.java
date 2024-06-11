import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceBDD extends Remote {
        public String getCoordRestaurant() throws RemoteException;
        public void reserverTable(String nom, String prenom, int nbPersonnes, String nTelephone) throws RemoteException;
}