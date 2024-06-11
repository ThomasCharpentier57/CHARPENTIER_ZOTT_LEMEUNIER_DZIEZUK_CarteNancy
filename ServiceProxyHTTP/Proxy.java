import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.*;

public class Proxy implements ServiceHTTP, HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        HttpClient client 
        String resp = "Test";
        exchange.sendResponseHeaders(200, resp.length());
        OutputStream os = exchange.getResponseBody();
        os.write(resp.getBytes());
        os.close();
    }
}
