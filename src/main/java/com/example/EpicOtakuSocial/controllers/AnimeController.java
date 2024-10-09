package com.example.EpicOtakuSocial.controllers;

import com.example.EpicOtakuSocial.entities.anime.Anime;
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

    public Anime getAnimeById(@PathVariable String id) {
        return restTemplate.getForObject(this.url + "/" + id, Anime.class);
    }

    @GetMapping

    public Anime getAnime(@RequestParam(required = false) Map<String, String> param) {

        String urlToFetch = this.url;

        if (param.containsKey("q")) {
            urlToFetch += "?q=" + param.get("q") + "&page=" + param.getOrDefault("nrPage", "1");
        } else if (param.containsKey("start_date")) {
            urlToFetch += "?start_date=" + param.get("start_date") + "&end_date=" + param.get("end_date");
        } else {
            urlToFetch += "?orderBy=popularity&page=" + param.getOrDefault("nrPage", "1");
        }

//
//        ResponseEntity<Anime> resp =

        //Il controller restituisce una String, quindi Spring la serializza come testo,
        // e l'header Content-Type potrebbe essere interpretato come text/plain anziché application/json.
        // Questo potrebbe causare problemi al frontend nella parsificazione della risposta JSON.
        //Per risolvere questo problema, puoi forzare il Content-Type della risposta a application/json,
        // in modo che il frontend possa riconoscerlo come JSON. Modifica il tuo metodo come segue:

        // Forza l'header 'Content-Type' a 'application/json'
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.getForObject(urlToFetch, Anime.class);    }
}

