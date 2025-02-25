# Weather SDK

## Introduction

Weather SDK is used for accessing a weather API (https://openweathermap.org/api).

## Usage Example
Use your personal API Key to get access to SDK (replace API_KEY with your personal key)

Use the city name to retrieve current weather information as stringified Json.

```java
WeatherProvider weatherProvider = WeatherProviderFactory.getProvider("API_KEY", true);
String weatherJson = weatherProvider.getWeather("city");
```
