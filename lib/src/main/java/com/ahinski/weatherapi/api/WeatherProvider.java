package com.ahinski.weatherapi.api;

import com.ahinski.weatherapi.internal.cache.CacheRefresher;
import com.ahinski.weatherapi.internal.cache.WeatherCache;
import com.ahinski.weatherapi.internal.client.WeatherApiClient;
import com.ahinski.weatherapi.internal.service.WeatherService;

/**
 * 
 * Weather provider to ask weather information for particular location
 * 
 * @author Dzmitry Ahinski
 * 
 */
public class WeatherProvider {
    
    private final WeatherService weatherService;
    private CacheRefresher cacheRefresher;

    WeatherProvider(String apiKey, boolean pollingMode) {
        WeatherApiClient weatherApiClient = new WeatherApiClient(apiKey);
        WeatherCache weatherCache = new WeatherCache();
        this.weatherService = new WeatherService(weatherApiClient, weatherCache);
        if (pollingMode) {
            this.cacheRefresher = new CacheRefresher(weatherCache, weatherService);
            this.cacheRefresher.start();
        }
    }

    /**
     * 
     * Retrieves weather information for particular city
     * 
     * @param city to check weather
     * @return stringified json weather information
     */
    public String getWeather(String city) {
        return weatherService.getWeatherByCity(city);
    }

    /**
     * 
     * Disables cyclic cache refresh
     * 
     */
    public void shutdownRefresher() {
        if (cacheRefresher != null) {
            cacheRefresher.stop();
        }
    }
}
