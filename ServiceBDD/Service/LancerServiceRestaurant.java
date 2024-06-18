import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerServiceRestaurant {
    public static void main(String[] args) throws RemoteException {
        ServiceRestaurant bdd = new ServiceRestaurant("zott1u", "Ju62lt82&*");

        try {
            ServiceRestaurantInterface service = (ServiceRestaurantInterface)UnicastRemoteObject.exportObject(bdd, 0);
            Registry registry = LocateRegistry.getRegistry("localhost", 2000);
            registry.rebind("ServiceRestaurant", service);
        } catch (RemoteException e) {
            System.out.println("Erreur lors de l'exportation de l'objet" + e.getMessage());
        }
    }
}