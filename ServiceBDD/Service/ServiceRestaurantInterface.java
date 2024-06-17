package Service;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceRestaurantInterface extends Remote {
        public String getCoordRestaurant() throws RemoteException;
        public void reserverTable(String idRestau, String nom, String prenom, String nbPersonnes, String nTelephone) throws RemoteException;
}