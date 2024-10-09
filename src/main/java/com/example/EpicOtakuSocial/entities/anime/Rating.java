package com.example.EpicOtakuSocial.entities.anime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.IOException;

public enum Rating {
    PG_13_TEENS_13_OR_OLDER, PG_CHILDREN, R_17_VIOLENCE_PROFANITY;
    @JsonValue
    public String toValue() {
        return switch (this) {
            case PG_13_TEENS_13_OR_OLDER -> "PG-13 - Teens 13 or older";
            case PG_CHILDREN -> "PG - Children";
            case R_17_VIOLENCE_PROFANITY -> "R - 17+ (violence & profanity)";
        };
    }
    @JsonCreator
    public static Rating forValue(String value) throws IOException {
        return switch (value) {
            case "PG-13 - Teens 13 or older" -> PG_13_TEENS_13_OR_OLDER;
            case "PG - Children" -> PG_CHILDREN;
            case "R - 17+ (violence & profanity)" -> R_17_VIOLENCE_PROFANITY;
            default -> throw new IOException("Cannot deserialize Rating");
        };
    }
}
