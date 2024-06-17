import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.*;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/applications/incident", new IncidentHTTPHandler());
        server.createContext("/applications/restaurant", new RestaurantHTTPHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
    
}
