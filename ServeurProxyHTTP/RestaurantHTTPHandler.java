import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;

import com.sun.net.httpserver.*;

public class RestaurantHTTPHandler extends HandlerHTTP {

    public RestaurantHTTPHandler(Serveur serveur) {
        super(serveur);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.getResponseHeaders().set("Content-Type", "application/json");

            byte[] allBytes = exchange.getRequestBody().readAllBytes();
            String content = new String(allBytes, StandardCharsets.UTF_8);
            this.serveur.reserverTable(content.split(","));

            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (RemoteException e) {
            System.out.println("Erreur lors de la récupération du service Restaurant"+e.getMessage());
           exchange.sendResponseHeaders(500, -1);
        }
    }
}