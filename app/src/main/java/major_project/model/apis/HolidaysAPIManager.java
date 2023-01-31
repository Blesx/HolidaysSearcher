package major_project.model.apis;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import major_project.model.Holiday;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HolidaysAPIManager {
    private final String api_key = System.getenv("INPUT_API_KEY");

    public List<Holiday> sendAPIRequest(LocalDate date, String countryAbv) {
        try {
            HttpRequest request = HttpRequest.newBuilder(
                    new URI("https://holidays.abstractapi.com/v1/?api_key=" + api_key +
                            "&country=" + countryAbv +
                            "&year=" + date.getYear() +
                            "&month=" + date.getMonthValue() +
                            "&day=" + date.getDayOfMonth()))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // parse response
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Holiday>>() {}.getType();
            List<Holiday> parsedResponse = gson.fromJson(response.body(), type);

            return parsedResponse;

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with the request");

        } catch (URISyntaxException ignored) {
            System.out.println("A URI syntax exception occured");

        } catch (IllegalStateException | JsonSyntaxException e) {
            System.out.println("Cannot call 2 different api calls at once");

        }

        return null;

    }

}
