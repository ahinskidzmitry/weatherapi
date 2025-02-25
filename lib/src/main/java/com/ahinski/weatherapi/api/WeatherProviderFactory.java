package com.ahinski.weatherapi.api;

import java.util.HashMap;
import java.util.Map;

import com.ahinski.weatherapi.exception.InvalidApiKey;

/**
 * 
 * Factory for weather providers creation and storage
 * 
 * @author Dzmitry Ahinski
 * 
 */
public class WeatherProviderFactory {
    
    private static final Map<String, WeatherProvider> providers = new HashMap<>();

    private WeatherProviderFactory() {}

    /**
     * 
     * Creates new weather provider instance
     * 
     * @param apiKey as weather API key
     * @param pollingMode enables cyclic cache refresh every 10 minutes
     * @return WeatherProvider as created provider instance
     */
    public static WeatherProvider getProvider(String apiKey, boolean pollingMode) {
        if (providers.containsKey(apiKey)) {
            throw new InvalidApiKey(String.format("Provider with API key %s already created", apiKey));
        }
        WeatherProvider weatherProvider = new WeatherProvider(apiKey, pollingMode);
        providers.put(apiKey, weatherProvider);
        return weatherProvider;
    }

    /**
     * 
     * Removes provider by API key
     * 
     * @param apiKey as weather API key
     */
    public static void removeProvider(String apiKey) {
        WeatherProvider weatherProvider = providers.remove(apiKey);
        weatherProvider.shutdownRefresher();
    }
}
