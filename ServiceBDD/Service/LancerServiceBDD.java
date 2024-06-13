package Service;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Client.GestionBDD;

public class LancerServiceBDD {
    public static void main(String[] args) {
        GestionBDD bdd = new GestionBDD("zott1u", "Ju62lt82&*");
        try {
            ServiceBDD serviceBDD = (ServiceBDD) UnicastRemoteObject.exportObject(bdd, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }



    }
}
