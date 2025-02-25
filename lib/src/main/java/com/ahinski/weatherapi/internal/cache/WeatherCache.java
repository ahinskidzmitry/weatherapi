package com.ahinski.weatherapi.internal.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WeatherCache {
    
    private static final int MAX_ELEMENTS_NUMBER = 10;
    private static final long CACHE_ENTRY_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(10);
    private final Map<String, CacheEntry> cache;

    public WeatherCache() {
        cache = new LinkedHashMap<String, CacheEntry>(MAX_ELEMENTS_NUMBER, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(java.util.Map.Entry<String, CacheEntry> eldest) {
                return this.size() > MAX_ELEMENTS_NUMBER;
            }
        };
    }

    public void put(String key, String entry) {
        CacheEntry cacheEntry = new CacheEntry(entry, System.currentTimeMillis());
        cache.put(key, cacheEntry);
    }

    public Set<String> getAllKeys() {
        return cache.keySet();
    }

    public Optional<String> get(String key) {
        CacheEntry entry = cache.get(key);
        if (entry != null && !isExpired(entry)) {
            return Optional.of(entry.getWeatherInfo());
        }
        return Optional.empty();
    }

    private boolean isExpired(CacheEntry cacheEntry) {
        return System.currentTimeMillis() - cacheEntry.getCreationTime() > CACHE_ENTRY_EXPIRATION_TIME;
    }
    
    private static class CacheEntry {
        private final String weatherInfo;
        private final long creationTime;

        public CacheEntry(String weatherInfo, long creationTime) {
            this.weatherInfo = weatherInfo;
            this.creationTime = creationTime;
        }

        public String getWeatherInfo() {
            return weatherInfo;
        }

        public long getCreationTime() {
            return creationTime;
        }
    }
}
