module com.ahinski.weatherapi {
    exports com.ahinski.weatherapi.api;
    exports com.ahinski.weatherapi.internal.model;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires java.net.http;
}
