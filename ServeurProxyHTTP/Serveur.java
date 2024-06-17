import java.io.IOException;
import java.net.InetSocketAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import Service.ServiceRestaurantInterface;
import com.sun.net.httpserver.*;
import proxy.ServiceTravauxInterface;

public class Serveur {

    private final ServiceRestaurantInterface sr;
    private final ServiceTravauxInterface spb;

    public Serveur(String adresseRestaurant, int portRestaurant, String adresseProxy, int portProxy) {
        try {
            this.sr = (ServiceRestaurantInterface) LocateRegistry.getRegistry(adresseRestaurant, portRestaurant).lookup("ServiceRestaurant");
            this.spb = (ServiceTravauxInterface) LocateRegistry.getRegistry(adresseProxy, portProxy).lookup("ServiceProxyBlocage");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void reserverTable(String[] val) {
        try {
            sr.reserverTable(val[0], val[1], val[2], val[3], val[4]);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCoordRestaurant() {
        try {
            return sr.getCoordRestaurant();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void lancer(String url) {
        try {
            spb.lancer(url);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        HttpsServer server;
        try {
            server = HttpsServer.create(new InetSocketAddress(8000), 0);

            Serveur serveur = new Serveur("localhost", 10002, "localhost", 10001);
            server.createContext("/applications", new IncidentHTTPHandler(serveur));
            server.createContext("/applications", new RestaurantHTTPHandler(serveur));
            server.createContext("/applications", new RestaurantCoordHTTPHandler(serveur));
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            System.out.println("Erreur lors de la création du serveur" + e.getMessage());
        }
    }
}