package com.example.EpicOtakuSocial.entities.anime;

import jakarta.persistence.Entity;

import java.util.List;
import java.util.Map;

@lombok.Data
public class Datum {
    private long mal_id;
    private String url;
    private Map<String, Image> images;
    private Trailer trailer;
    private List<Title> titles;
    private String title;
    private String title_english;
    private String title_japanese;
    private String type;
    private String source;
    private Long episodes;
    private String status;
    private boolean airing;
    private Aired aired;
    private String duration;
    private String rating;
    private Double score;
    private Long scored_by;
    private long rank;
    private long popularity;
    private long members;
    private long favorites;
    private String synopsis;
    private String background;
    private Season season;
    private Long year;
    private List<Demographic> genres;
}
