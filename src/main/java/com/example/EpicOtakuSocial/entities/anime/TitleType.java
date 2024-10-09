package com.example.EpicOtakuSocial.entities.anime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.IOException;

public enum TitleType {
    DEFAULT, ENGLISH, FRENCH, GERMAN, JAPANESE, SPANISH, SYNONYM;
    @JsonValue
    public String toValue() {
        return switch (this) {
            case DEFAULT -> "Default";
            case ENGLISH -> "English";
            case FRENCH -> "French";
            case GERMAN -> "German";
            case JAPANESE -> "Japanese";
            case SPANISH -> "Spanish";
            case SYNONYM -> "Synonym";
        };
    }
    @JsonCreator
    public static TitleType forValue(String value) throws IOException {
        return switch (value) {
            case "Default" -> DEFAULT;
            case "English" -> ENGLISH;
            case "French" -> FRENCH;
            case "German" -> GERMAN;
            case "Japanese" -> JAPANESE;
            case "Spanish" -> SPANISH;
            case "Synonym" -> SYNONYM;
            default -> throw new IOException("Cannot deserialize TitleType");
        };
    }
}
