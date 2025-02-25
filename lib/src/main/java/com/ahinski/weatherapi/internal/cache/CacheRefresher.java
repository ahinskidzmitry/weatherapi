package com.ahinski.weatherapi.internal.cache;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ahinski.weatherapi.internal.service.WeatherService;

public class CacheRefresher {
    
    private static final long CACHE_REFRESH_DELAY = 10;
    private final WeatherCache weatherCache;
    private final WeatherService weatherService;
    private final ScheduledExecutorService refreshExecutor;

    public CacheRefresher(WeatherCache weatherCache, WeatherService weatherService) {
        this.weatherCache = weatherCache;
        this.weatherService = weatherService;
        refreshExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        refreshExecutor.scheduleAtFixedRate(this::refreshActiveEntries, CACHE_REFRESH_DELAY, CACHE_REFRESH_DELAY, TimeUnit.MINUTES);
    }

    public void stop() {
        refreshExecutor.shutdown();
    }

    private void refreshActiveEntries() {
        Set<String> keys = Set.copyOf(weatherCache.getAllKeys());
        for (String key : keys) {
            String stringifiedWeatherJson = weatherService.getWeatherByCity(key);
            weatherCache.put(key, stringifiedWeatherJson);
        }
    }
}
