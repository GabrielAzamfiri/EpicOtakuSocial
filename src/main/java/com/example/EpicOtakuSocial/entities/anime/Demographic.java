package com.example.EpicOtakuSocial.entities.anime;

@lombok.Data
public class Demographic {
    private long mal_id;
    private DemographicType type;
    private String name;
    private String url;
}
