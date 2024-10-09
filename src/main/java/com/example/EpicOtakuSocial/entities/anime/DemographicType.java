package com.example.EpicOtakuSocial.entities.anime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.IOException;

public enum DemographicType {
    ANIME;
    @JsonValue
    public String toValue() {
        if (this == DemographicType.ANIME) {
            return "anime";
        }
        return null;
    }
    @JsonCreator
    public static DemographicType forValue(String value) throws IOException {
        if (value.equals("anime")) return ANIME;
        throw new IOException("Cannot deserialize DemographicType");
    }
}
