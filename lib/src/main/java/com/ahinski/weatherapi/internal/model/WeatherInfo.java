package com.ahinski.weatherapi.internal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInfo {

    private Weather weather;
    
    private Temperature temperature;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("wind")
    private Wind wind;

    private long datetime;

    @JsonProperty("sys")
    private SunriseSunsetInfo sys;

    @JsonProperty("timezone")
    private int timezone;

    @JsonProperty("name")
    private String name;

    public Weather getWeather() {
        return weather;
    }

    @JsonProperty("weather")
    public void setWeather(List<Weather> weather) {
        this.weather = weather.getFirst();
    }

    @JsonProperty("temperature")
    public Temperature getTemperature() {
        return temperature;
    }

    @JsonProperty("main")
    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @JsonProperty("datetime")
    public long getDatetime() {
        return datetime;
    }

    @JsonProperty("dt")
    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public SunriseSunsetInfo getSys() {
        return sys;
    }

    public void setSys(SunriseSunsetInfo sys) {
        this.sys = sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((weather == null) ? 0 : weather.hashCode());
        result = prime * result + ((temperature == null) ? 0 : temperature.hashCode());
        result = prime * result + visibility;
        result = prime * result + ((wind == null) ? 0 : wind.hashCode());
        result = prime * result + (int) (datetime ^ (datetime >>> 32));
        result = prime * result + ((sys == null) ? 0 : sys.hashCode());
        result = prime * result + timezone;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WeatherInfo other = (WeatherInfo) obj;
        if (weather == null) {
            if (other.weather != null)
                return false;
        } else if (!weather.equals(other.weather))
            return false;
        if (temperature == null) {
            if (other.temperature != null)
                return false;
        } else if (!temperature.equals(other.temperature))
            return false;
        if (visibility != other.visibility)
            return false;
        if (wind == null) {
            if (other.wind != null)
                return false;
        } else if (!wind.equals(other.wind))
            return false;
        if (datetime != other.datetime)
            return false;
        if (sys == null) {
            if (other.sys != null)
                return false;
        } else if (!sys.equals(other.sys))
            return false;
        if (timezone != other.timezone)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "WeatherInfo [weather=" + weather + ", temperature=" + temperature + ", visibility=" + visibility
                + ", wind=" + wind + ", datetime=" + datetime + ", sys=" + sys + ", timezone=" + timezone + ", name="
                + name + "]";
    }
}
