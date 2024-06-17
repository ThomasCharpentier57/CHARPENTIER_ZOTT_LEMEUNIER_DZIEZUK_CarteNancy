package proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.RemoteException;
import java.time.Duration;

public class ServiceTravaux implements ServiceTravauxInterface {
    public void lancer(String url) throws RemoteException {
        String proxyHost = "www-cache.iutnc.univ-lorraine.fr"; // Faut pas oublier de changer le proxy
        int proxyPort = 3128;

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .proxy(ProxySelector.of(new InetSocketAddress(proxyHost, proxyPort)))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url)) //"https://carto.g-ny.org/data/cifs/cifs_waze_v2.json"
                .timeout(Duration.ofMinutes(2))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Erreur lors de la requête HTTP: \n" + e.getMessage());
        }
    }
}
