import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherAPI {

   public String parseText(String jsonText) {
		ObjectMapper mapper = new ObjectMapper();
		ForecastResponse re = null;
		try {
			re = mapper.readValue(jsonText, ForecastResponse.class);
            return String.valueOf(re.getMain().getTemp());
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}


    public String getWeatherFor(String location) {
        try {
            location = URLEncoder.encode(location, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Error encoding location: " + e.getMessage());
        }
        //TODO create env file for the api key
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=f0fdd3623a9fd1d96f586648ad68ae5d&units=metric", location);
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonText = response.body();
       return this.parseText(jsonText);
    }
}
