import java.io.IOException;
import java.net.InetSocketAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import com.sun.net.httpserver.*;

class LancerServeurHTTP {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/applications/incident", new HandlerHTTPIncident());
            server.createContext("/applications/restaurantCoor", new HandlerHTTPRestaurantCoor());
            server.createContext("/applications/restaurantRes", new HandlerHTTPRestaurantReserver());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            System.out.println("Erreur lors de la création du serveur" + e.getMessage());
        }
    }
}