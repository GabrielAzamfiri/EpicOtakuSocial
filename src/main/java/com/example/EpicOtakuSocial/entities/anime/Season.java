package com.example.EpicOtakuSocial.entities.anime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.IOException;

public enum Season {
    FALL, SPRING, SUMMER, WINTER;
    @JsonValue
    public String toValue() {
        return switch (this) {
            case FALL -> "fall";
            case SPRING -> "spring";
            case SUMMER -> "summer";
            case WINTER -> "winter";
        };
    }
    @JsonCreator
    public static Season forValue(String value) throws IOException {
        return switch (value) {
            case "fall" -> FALL;
            case "spring" -> SPRING;
            case "summer" -> SUMMER;
            case "winter" -> WINTER;
            default -> throw new IOException("Cannot deserialize Season");
        };
    }
}
