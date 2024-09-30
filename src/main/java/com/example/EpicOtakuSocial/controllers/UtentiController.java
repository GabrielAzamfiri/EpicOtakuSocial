package com.example.EpicOtakuSocial.controllers;


import com.example.EpicOtakuSocial.entities.Utente;
import com.example.EpicOtakuSocial.payloads.UtenteDTO;
import com.example.EpicOtakuSocial.payloads.UtenteRespDTO;
import com.example.EpicOtakuSocial.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("/utenti")
public class UtentiController {

    @Autowired
    private UtentiService utentiService;



    @GetMapping("/{utenteId}")
    public Utente getById(@PathVariable UUID utenteId) {
        return this.utentiService.findById(utenteId);
    }

    @GetMapping
    public Page<Utente> getAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sortBy) {
        return this.utentiService.findAll(page, size, sortBy);
    }

    @DeleteMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID utenteId) {
        this.utentiService.findByIdAndDelete(utenteId);
    }

    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UtenteRespDTO findByIdAndUpdate(@PathVariable UUID utenteId, @RequestBody @Validated UtenteDTO body) {
        return this.utentiService.findByIdAndUpdate(utenteId, body);
    }

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        return utenteCorrenteAutenticato;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        this.utentiService.findByIdAndDelete(utenteCorrenteAutenticato.getId());
    }

    @PutMapping("/me")
    public UtenteRespDTO updateProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato, @RequestBody @Validated UtenteDTO body) {
        return this.utentiService.findByIdAndUpdate(utenteCorrenteAutenticato.getId(), body);
    }


    // CLOUDINARY
    // UPLOAD IMMAGINE
    @PostMapping("/me/avatar")
    public Utente uploadImage(@RequestParam("avatar") MultipartFile img, @AuthenticationPrincipal Utente utenteCorrenteAutenticato) throws IOException {
        try {
            return this.utentiService.uploadImagine(img, utenteCorrenteAutenticato.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // CLOUDINARY
    // UPLOAD IMMAGINE
    @PostMapping("/{utenteId}/avatar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente uploadImage(@RequestParam("avatar") MultipartFile img, @PathVariable UUID utenteId) throws IOException {
        try {
            return this.utentiService.uploadImagine(img, utenteId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
