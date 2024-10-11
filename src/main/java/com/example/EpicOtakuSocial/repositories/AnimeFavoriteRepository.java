package com.example.EpicOtakuSocial.repositories;

import com.example.EpicOtakuSocial.entities.AnimeFavorite;
import com.example.EpicOtakuSocial.entities.Commento;
import com.example.EpicOtakuSocial.entities.Utente;
import com.example.EpicOtakuSocial.entities.anime.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface AnimeFavoriteRepository extends JpaRepository<AnimeFavorite,UUID > {

    List<AnimeFavorite> findByUtente(Utente utente);
    AnimeFavorite findByIdAnimeAndUtente(Long idAnime, Utente utente);
}
