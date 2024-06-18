import java.io.IOException;
import java.io.OutputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.sun.net.httpserver.*;

public class HandlerHTTPRestaurantCoor implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        String response = "";
        try{
        Registry reg = LocateRegistry.getRegistry("localhost", 2000);
        ServiceRestaurantInterface service = (ServiceRestaurantInterface)reg.lookup("ServiceRestaurant");
        response = service.getCoordRestaurant();
        } catch (RemoteException e){
            e.printStackTrace();
        } catch (NotBoundException e1){
            e1.printStackTrace();
        }

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    
}
