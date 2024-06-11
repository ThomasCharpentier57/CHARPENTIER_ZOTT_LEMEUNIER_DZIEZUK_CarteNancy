import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.sun.net.httpserver.HttpServer;

public class LancerProxy {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
            server.createContext("", new Proxy());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
