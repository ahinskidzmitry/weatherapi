package com.ahinski.weatherapi.internal.service;

import java.util.Optional;

import com.ahinski.weatherapi.exception.WeatherApiException;
import com.ahinski.weatherapi.internal.cache.WeatherCache;
import com.ahinski.weatherapi.internal.client.WeatherApiClient;
import com.ahinski.weatherapi.internal.model.WeatherInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherService {
    
    private final WeatherApiClient weatherApiClient;
    private final WeatherCache weatherCache;
    private final ObjectMapper objectMapper;

    public WeatherService(WeatherApiClient weatherApiClient, WeatherCache weatherCache) {
        this.weatherApiClient = weatherApiClient;
        this.weatherCache = weatherCache;
        objectMapper = new ObjectMapper();
    }

    public String getWeatherByCity(String city) {
        Optional<String> weatherInfoOptional = weatherCache.get(city);
        if (weatherInfoOptional.isPresent()) {
            return weatherInfoOptional.get();
        }
        
        try {
            String weatherInfoJson = weatherApiClient.getWeatherByCity(city);
            WeatherInfo weatherInfo = objectMapper.readValue(weatherInfoJson, WeatherInfo.class);
            String stringifiedWeatherJson = objectMapper.writeValueAsString(weatherInfo);
            weatherCache.put(city, stringifiedWeatherJson);
            return stringifiedWeatherJson;
        } catch (JsonProcessingException e) {
            throw new WeatherApiException(e.getMessage());
        }
    }
}
