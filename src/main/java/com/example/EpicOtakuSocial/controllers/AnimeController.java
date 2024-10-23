package com.example.EpicOtakuSocial.controllers;

import com.example.EpicOtakuSocial.entities.anime.Anime;
import com.example.EpicOtakuSocial.entities.anime.Datum;
import com.example.EpicOtakuSocial.entities.anime.OneAnime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/anime")
public class AnimeController {
    private final String url = "https://api.jikan.moe/v4/anime";
    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/{id}")

    public OneAnime getAnimeById(@PathVariable String id) {
        return restTemplate.getForObject(this.url + "/" + id, OneAnime.class);
    }

    @GetMapping

    public Anime getAnime(@RequestParam(required = false) Map<String, String> param) {

        String urlToFetch = this.url;

        if (param.containsKey("q")) {
            urlToFetch += "?q=" + param.get("q") + "&page=" + param.getOrDefault("nrPage", "1");
        } else if (param.containsKey("start_date")) {
            urlToFetch += "?start_date=" + param.get("start_date") + "&end_date=" + param.get("end_date");
        } else if (param.containsKey("genres")) {
            urlToFetch += "?orderBy=favorites&sort=desc&genres=" + param.get("genres") + "&page=" + param.getOrDefault("nrPage", "1");
        } else {
            urlToFetch += "?orderBy=favorites&sort=desc&page=" + param.getOrDefault("nrPage", "1");
        }

        // Forza l'header 'Content-Type' a 'application/json'
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.getForObject(urlToFetch, Anime.class);    }



}

