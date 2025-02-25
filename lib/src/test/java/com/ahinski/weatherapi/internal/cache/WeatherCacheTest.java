package com.ahinski.weatherapi.internal.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WeatherCacheTest {

    private WeatherCache weatherCache;

    private String weatherInfoJson;

    @BeforeEach
    void setUp() {
        this.weatherCache = new WeatherCache();
        this.weatherInfoJson = """
                {
                  "weather": {
                    "main": "Clouds",
                    "description": "scattered clouds"
                  },
                  "temperature": {
                    "temp": 269.6,
                    "feels_like": 267.57
                  },
                  "visibility": 10000,
                  "wind": {
                    "speed": 1.38
                  },
                  "datetime": 1675744800,
                  "sys": {
                    "sunrise": 1675751262,
                    "sunset": 1675787560
                  },
                  "timezone": 3600,
                  "name": "Zocca"
                }
        """;
    }

    @Test
    void testPutAndGetData() {
        String city = "Minsk";

        assertTrue(weatherCache.get(city).isEmpty());

        weatherCache.put(city, weatherInfoJson);

        assertEquals(weatherInfoJson, weatherCache.get(city).get());
    }

    @Test
    void testCannotPutMoreThanTenEntries() {
        String city = "Minsk";

        for (int i = 0; i < 15; i++) {
            weatherCache.put(city + i, weatherInfoJson);
        }

        assertEquals(weatherCache.getAllKeys().size(), 10);
    }

    @Test
    void testReplacesEldestOnCacheOverflow() {
        String city = "Minsk";

        weatherCache.put("Gomel", weatherInfoJson);

        for (int i = 0; i < 9; i++) {
            weatherCache.put(city + i, weatherInfoJson);
        }

        weatherCache.put("Grodno", weatherInfoJson);

        assertEquals(weatherCache.getAllKeys().size(), 10);
        assertEquals(weatherCache.get("Grodno").get(), weatherInfoJson);
        assertTrue(weatherCache.get("Gomel").isEmpty());
    }
}
