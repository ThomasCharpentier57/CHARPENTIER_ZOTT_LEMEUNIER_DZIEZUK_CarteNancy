import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LancerServiceBDD {
    public static void main(String[] args) {
        GestionBDD bdd = new GestionBDD(null, null);
        try {
            ServiceBDD serviceBDD = (ServiceBDD) UnicastRemoteObject.exportObject(bdd, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }



    }
}
