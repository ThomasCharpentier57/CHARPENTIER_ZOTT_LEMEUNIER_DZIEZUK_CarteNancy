import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        String proxyHost = "proxy.example.com"; // Faut pas oublier de changer le proxy
        int proxyPort = 80;

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .proxy(ProxySelector.of(new InetSocketAddress(proxyHost, proxyPort)))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://carto.g-ny.org/data/cifs/cifs_waze_v2.json"))
                .timeout(Duration.ofMinutes(2))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                JSONObject jsonObject = new JSONObject(new JSONTokener(responseBody));
                JSONArray incidents = jsonObject.getJSONArray("incidents");

                for (int i = 0; i < incidents.length(); i++) {
                    JSONObject incident = incidents.getJSONObject(i);
                    System.out.println("Incident ID: " + incident.getString("id"));
                    System.out.println("Description: " + incident.getString("description"));
                }
            } else {
                System.out.println("Failed to fetch data. HTTP status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
