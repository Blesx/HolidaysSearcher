package holidays_searcher.model.apis;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TwilioAPIManager {
    private final String twilio_api_key = System.getenv("TWILIO_API_KEY");
    private final String twilio_api_sid = System.getenv("TWILIO_API_SID");
    private final String twilio_api_from = System.getenv("TWILIO_API_FROM");
    private final String twilio_api_to = System.getenv("TWILIO_API_TO");

    String plainCredentials = twilio_api_sid + ":" + twilio_api_key;
    String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
    String authorizationHeader = "Basic " + base64Credentials;

    public boolean sendAPIRequest(String reportToSend) {
        String urlParameters = "Body=" + reportToSend + "&From=" + twilio_api_from + "&To=+" + twilio_api_to;
        byte[] byteParameters = urlParameters.getBytes(StandardCharsets.UTF_8);

        try {
            // build and send request
            HttpRequest request = HttpRequest.newBuilder(
                            new URI("https://api.twilio.com/2010-04-01/Accounts/" + twilio_api_sid +
                                    "/Messages.json"))
                    .setHeader("Authorization", authorizationHeader)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(byteParameters))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return true;

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with the request.");

        } catch (URISyntaxException ignored) {
            System.out.println("There was a URISyntaxException in the request.");

        }

        return false;

    }

}
