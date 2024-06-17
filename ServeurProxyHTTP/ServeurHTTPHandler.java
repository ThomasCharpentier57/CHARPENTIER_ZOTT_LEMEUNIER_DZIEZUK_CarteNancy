/**
 * ServeurHTTPHandler
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.sun.net.httpserver.*;

public class ServeurHTTPHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Registry registryBDD = LocateRegistry.getRegistry();
        
        InputStream is = exchange.getRequestBody();
        String response = "This is the response";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    
}