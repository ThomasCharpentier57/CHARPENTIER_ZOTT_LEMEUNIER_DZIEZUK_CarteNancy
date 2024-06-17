package Service;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceRestaurantInterface extends Remote {
        public String getCoordRestaurant() throws RemoteException;
        public void reserverTable(int idRestau, String nom, String prenom, int nbPersonnes, int nTelephone) throws RemoteException;
}