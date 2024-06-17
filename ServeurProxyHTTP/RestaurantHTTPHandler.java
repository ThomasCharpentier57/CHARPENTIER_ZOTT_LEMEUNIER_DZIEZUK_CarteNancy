/**
 * ServeurHTTPHandler
 */

import java.io.IOException;
import java.io.OutputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.sun.net.httpserver.*;

public class RestaurantHTTPHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            Registry regRestaurant = LocateRegistry.getRegistry();
            regRestaurant.lookup("ServiceRestaurant");
            String response = "This is the response";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (RemoteException | NotBoundException e) {
           e.printStackTrace();
           exchange.sendResponseHeaders(500, -1);
        }
       
    }

    
}