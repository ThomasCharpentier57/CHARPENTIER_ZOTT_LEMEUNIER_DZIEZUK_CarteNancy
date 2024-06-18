import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.*;


public class IncidentHTTPHandler extends HandlerHTTP {

    public IncidentHTTPHandler(Serveur serveur) {
        super(serveur);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        this.response = this.serveur.lancer("https://carto.g-ny.org/data/cifs/cifs_waze_v2.json");

        exchange.sendResponseHeaders(200, this.response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(this.response.getBytes());
        os.close();
    }
}