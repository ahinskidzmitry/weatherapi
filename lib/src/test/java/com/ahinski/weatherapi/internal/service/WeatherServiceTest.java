package com.ahinski.weatherapi.internal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ahinski.weatherapi.internal.cache.WeatherCache;
import com.ahinski.weatherapi.internal.client.WeatherApiClient;

public class WeatherServiceTest {

    @Mock
    private WeatherApiClient weatherApiClient;

    @Mock
    private WeatherCache weatherCache;

    private WeatherService weatherService;

    private String weatherInfoApiJson;

    private String weatherInfoJson;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherService = new WeatherService(weatherApiClient, weatherCache);
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
        this.weatherInfoApiJson = """
                                {
                    "coord": {
                        "lon": -0.1257,
                        "lat": 51.5085
                    },
                    "weather": [
                        {
                            "id": 802,
                            "main": "Clouds",
                            "description": "scattered clouds",
                            "icon": "03n"
                        }
                    ],
                    "base": "stations",
                    "main": {
                        "temp": 269.6,
                        "feels_like": 267.57,
                        "temp_min": 280.71,
                        "temp_max": 282.18,
                        "pressure": 1012,
                        "humidity": 90,
                        "sea_level": 1012,
                        "grnd_level": 1008
                    },
                    "visibility": 10000,
                    "wind": {
                        "speed": 1.38,
                        "deg": 210
                    },
                    "clouds": {
                        "all": 40
                    },
                    "dt": 1675744800,
                    "sys": {
                        "type": 2,
                        "id": 2091269,
                        "country": "GB",
                        "sunrise": 1675751262,
                        "sunset": 1675787560
                    },
                    "timezone": 3600,
                    "id": 2643743,
                    "name": "Zocca",
                    "cod": 200
                }
        """;
    }

    @Test
    void testGetCachedWeatherByCity() {
        String city = "Minsk";

        when(weatherCache.get(city)).thenReturn(Optional.of(weatherInfoJson));

        String result = weatherService.getWeatherByCity(city);

        assertNotNull(result);
        assertEquals(weatherInfoJson, result);

        verify(weatherApiClient, never()).getWeatherByCity(city);
        verify(weatherCache, times(1)).get(city);
    }

    @Test
    void testGetWeatherByCity() {
        String city = "Minsk";

        when(weatherCache.get(city)).thenReturn(Optional.empty());
        when(weatherApiClient.getWeatherByCity(city)).thenReturn(weatherInfoApiJson);

        String result = weatherService.getWeatherByCity(city);

        assertNotNull(result);
        assertEquals(weatherInfoJson.replaceAll("\\s+", ""), result.replaceAll("\\s+", ""));

        verify(weatherApiClient, times(1)).getWeatherByCity(city);
    }
}
