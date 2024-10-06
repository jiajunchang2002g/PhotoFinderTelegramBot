package tutorial;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoFetcher {

    private static final Logger logger = Logger.getLogger(PhotoFetcher.class.getName());
    private static final String DOG_API_URL = "https://dog.ceo/api/breeds/image/random";

    public static String fetchDogPhoto() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(DOG_API_URL))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            return jsonResponse.getString("message");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching dog photo", e);
        }
        return null;
    }
}

