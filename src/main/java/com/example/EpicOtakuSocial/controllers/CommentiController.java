package com.example.EpicOtakuSocial.controllers;

import com.example.EpicOtakuSocial.entities.Commento;
import com.example.EpicOtakuSocial.entities.Utente;
import com.example.EpicOtakuSocial.payloads.CommentoDTO;
import com.example.EpicOtakuSocial.services.CommentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/commenti")
public class CommentiController {
    @Autowired
    private CommentiService commentiService;


    @GetMapping("/{commentoId}")
    public Commento getById(@PathVariable UUID commentoId) {
        return this.commentiService.findById(commentoId);
    }

    @GetMapping
    public Page<Commento> getAll(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "20") int size,
                             @RequestParam(defaultValue = "id") String sortBy) {
        return this.commentiService.findAll(page, size, sortBy);
    }
    @PostMapping("/crea")
    @PreAuthorize("hasAnyAuthority('ADMIN','BASIC_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Commento save(@AuthenticationPrincipal Utente utenteCorrenteAutenticato, @RequestBody @Validated CommentoDTO commentoDTO){

        return this.commentiService.save(utenteCorrenteAutenticato, commentoDTO);
    }

    @DeleteMapping("/{commentoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID commentoId) {
        this.commentiService.delete(commentoId);
    }

    @PutMapping("/{commentoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Commento findByIdAndUpdate(@PathVariable UUID commentoId, @RequestBody @Validated CommentoDTO commentoDTO) {
        return this.commentiService.update(commentoId, commentoDTO);
    }
}
