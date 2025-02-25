# Weather SDK

## Introduction

Weather SDK is used to retrieve weather information for particular location.
```

## Usage Example

```java
        WeatherProvider weatherProvider = WeatherProviderFactory.getProvider("API_KEY", true);
        String weatherJson = weatherProvider.getWeather("city");
```
