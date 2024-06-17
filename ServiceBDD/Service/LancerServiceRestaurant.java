package Service;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LancerServiceRestaurant {
    public static void main(String[] args) {
        ServiceRestaurant bdd = new ServiceRestaurant("zott1u", "Ju62lt82&*");
        try {
            ServiceRestaurantInterface serviceBDD = (ServiceRestaurantInterface) UnicastRemoteObject.exportObject(bdd, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }



    }
}
