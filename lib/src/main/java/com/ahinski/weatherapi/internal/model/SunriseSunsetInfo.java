package com.ahinski.weatherapi.internal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SunriseSunsetInfo {
    
    @JsonProperty("sunrise")
    private long sunrise;
    @JsonProperty("sunset")
    private long sunset;
    
    public long getSunrise() {
        return sunrise;
    }
    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }
    public long getSunset() {
        return sunset;
    }
    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (sunrise ^ (sunrise >>> 32));
        result = prime * result + (int) (sunset ^ (sunset >>> 32));
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
        SunriseSunsetInfo other = (SunriseSunsetInfo) obj;
        if (sunrise != other.sunrise)
            return false;
        if (sunset != other.sunset)
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Sys [sunrise=" + sunrise + ", sunset=" + sunset + "]";
    }
}
