package com.example.EpicOtakuSocial.services;

import com.example.EpicOtakuSocial.entities.AnimeFavorite;
import com.example.EpicOtakuSocial.entities.Utente;
import com.example.EpicOtakuSocial.exceptions.NotFoundException;
import com.example.EpicOtakuSocial.payloads.AnimeFavoriteDTO;
import com.example.EpicOtakuSocial.repositories.AnimeFavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Service
public class AnimeFavoriteService {
    @Autowired
    private AnimeFavoriteRepository animeFavoriteRepository;

    public AnimeFavorite findById(UUID animeFavoriteId) {
        return this.animeFavoriteRepository.findById(animeFavoriteId)
                .orElseThrow(() -> new NotFoundException("L'anime con l'id " + animeFavoriteId + " non è stato trovato."));
    }

    public AnimeFavorite findByIdAndUtente(Long idAnime , Utente utente) {
        return this.animeFavoriteRepository.findByIdAnimeAndUtente(idAnime, utente);
     }

    public List<AnimeFavorite> getAll() {
        return this.animeFavoriteRepository.findAll();
    }


    public AnimeFavorite save(AnimeFavoriteDTO body, Utente utente) {

        AnimeFavorite animeFavorite = new AnimeFavorite(body.id() ,body.title() , body.image(),body.genres() ,body.aired(), utente);
        return animeFavoriteRepository.save(animeFavorite);
    }
    public List<AnimeFavorite> findByUtente(Utente body) {

        return animeFavoriteRepository.findByUtente(body);
    }
    public void delete(UUID animeFavoriteId) {
        AnimeFavorite trovato = this.findById(animeFavoriteId);
        animeFavoriteRepository.delete(trovato);
    }
}
