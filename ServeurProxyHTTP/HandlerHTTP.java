import com.sun.net.httpserver.HttpHandler;

public abstract class HandlerHTTP implements HttpHandler {
    protected String response;
    protected Serveur serveur;

    public HandlerHTTP(Serveur serveur) {
        this.serveur = serveur;
    }
}
