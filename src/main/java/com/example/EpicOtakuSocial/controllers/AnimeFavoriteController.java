package com.example.EpicOtakuSocial.controllers;

import com.example.EpicOtakuSocial.entities.AnimeFavorite;
import com.example.EpicOtakuSocial.entities.Utente;
import com.example.EpicOtakuSocial.payloads.AnimeFavoriteDTO;
import com.example.EpicOtakuSocial.services.AnimeFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("animeFavorite")
public class AnimeFavoriteController {
    @Autowired
    private AnimeFavoriteService animeFavoriteService;

    @GetMapping("/{animeId}")
    public AnimeFavorite getById(@PathVariable UUID animeId) {
        return this.animeFavoriteService.findById(animeId);
    }
    @GetMapping
    public List<AnimeFavorite> getAll() {
        return this.animeFavoriteService.getAll();
    }
    @DeleteMapping("/{animeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID animeId) {
        this.animeFavoriteService.delete(animeId);
    }
    @PostMapping("/crea")
    @PreAuthorize("hasAnyAuthority('ADMIN','BASIC_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public AnimeFavorite save(@AuthenticationPrincipal Utente utenteCorrenteAutenticato,@RequestBody @Validated AnimeFavoriteDTO animeFavoriteDTO){
        return this.animeFavoriteService.save( animeFavoriteDTO, utenteCorrenteAutenticato);
    }

}
