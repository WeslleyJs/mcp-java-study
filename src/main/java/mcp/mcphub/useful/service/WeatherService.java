package mcp.mcphub.useful.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import mcp.mcphub.useful.model.bo.WeatherResponse;

@Service
public class WeatherService {

    private static final String BASE_URL = "https://wttr.in";

    private final RestClient restClient;

    public WeatherService() {

        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/geo+json")
                .defaultHeader("User-Agent", "WeatherApiClient/1.0 (your@email.com)")
                .build();
    }

    @Tool(description = "Get current weather information for a Brazil city.")
    public WeatherResponse getWeather(String cityName) {
        try {
            return restClient.get()
                    .uri("/{city_name}?format=j1", cityName)
                    .retrieve()
                    .body(WeatherResponse.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    *  Executar o método pelo main
    *  permite que o código seja executado via JVM trazendo o resultado direto, sem uso de controller
    *
    * */
    public static void main(String[] args) {
        try {
            WeatherService weatherService = new WeatherService();
            WeatherResponse weatherResponse = weatherService.getWeather("Curitiba");
            System.out.println("Temperatura da cidade é: " + weatherResponse.getCurrentCondition().get(0).getTemp_C());
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }

}