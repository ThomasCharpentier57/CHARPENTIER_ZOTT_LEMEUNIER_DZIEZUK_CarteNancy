import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerServiceRestaurant {
    public static void main(String[] args) throws RemoteException {
        ServiceRestaurant bdd = new ServiceRestaurant("zott1u", "Ju62lt82&*");

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("ServiceRestaurant", bdd);

        try {
            UnicastRemoteObject.exportObject(bdd, 0);
        } catch (RemoteException e) {
            System.out.println("Erreur lors de l'exportation de l'objet" + e.getMessage());
        }
    }
}