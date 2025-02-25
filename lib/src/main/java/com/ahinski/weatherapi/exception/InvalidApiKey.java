package com.ahinski.weatherapi.exception;

public class InvalidApiKey extends RuntimeException {
    
    public InvalidApiKey(String message) {
        super(message);
    }
}
