package com.example.EpicOtakuSocial.controllers;

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
    @PreAuthorize("hasAnyAuthority('ADMIN','BASIC_USER')")
    public ResponseEntity<String> getAnimeById(@PathVariable String id) {
        return restTemplate.getForEntity(this.url + "/" + id, String.class);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','BASIC_USER')")
    public ResponseEntity<Object> getAnime(@RequestParam(required = false) Map<String, String> param) {

        String urlToFetch = this.url;

        if (param.containsKey("q")) {
            urlToFetch += "?q=" + param.get("q");
        } else if (param.containsKey("start_date")) {
            urlToFetch += "?start_date=" + param.get("start_date");
        } else {
            urlToFetch += "?orderBy=popularity&page=" + param.getOrDefault("nrPage", "1");
        }


        ResponseEntity<Object> resp = restTemplate.getForEntity(urlToFetch, Object.class);

        //Il controller restituisce una String, quindi Spring la serializza come testo,
        // e l'header Content-Type potrebbe essere interpretato come text/plain anziché application/json.
        // Questo potrebbe causare problemi al frontend nella parsificazione della risposta JSON.
        //Per risolvere questo problema, puoi forzare il Content-Type della risposta a application/json,
        // in modo che il frontend possa riconoscerlo come JSON. Modifica il tuo metodo come segue:

        // Forza l'header 'Content-Type' a 'application/json'
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(resp.getBody(), headers, resp.getStatusCode());    }
}

