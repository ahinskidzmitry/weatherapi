package com.ahinski.weatherapi.internal.client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.ahinski.weatherapi.exception.WeatherApiException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherApiClient {

    private static final String MESSAGE_FIELD_NAME = "message";
    private final String BASE_API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String apiKey;
    
    public WeatherApiClient(String apiKey) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.apiKey = apiKey;
    }

    public String getWeatherByCity(String city) {
        try {
            String url = String.format("%s?q=%s&appid=%s", BASE_API_URL, city, apiKey);
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() != 200) {
                JsonNode jsonNode = objectMapper.readTree(httpResponse.body());
                String message = jsonNode.get(MESSAGE_FIELD_NAME).asText();
                throw new WeatherApiException(message);
            }

            return httpResponse.body();
        } catch (ConnectException connectException) {
            throw new WeatherApiException("Network connection error");
        } catch (IOException e) {
            throw new WeatherApiException(String.format("IOException: %s", e.getMessage()));
        } catch (InterruptedException e) {
            throw new WeatherApiException(String.format("Request interrupted: %s", e.getMessage()));
        }
    }
}
