import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class RestaurantCoordHTTPHandler extends HandlerHTTP {

    public RestaurantCoordHTTPHandler(Serveur serveur) {
        super(serveur);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "https://webetu.iutnc.univ-lorraine.fr");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        this.serveur.getCoordRestaurant();

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(this.response.getBytes());
        os.close();
    }
}
