package com.example.EpicOtakuSocial.entities;

import com.example.EpicOtakuSocial.entities.anime.Aired;
import com.example.EpicOtakuSocial.entities.anime.Datum;
import com.example.EpicOtakuSocial.entities.anime.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "anime_favorite")
@Getter
@Setter
@NoArgsConstructor
public class AnimeFavorite {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue
    private UUID id;

    private Long idAnime;
    private String title;
    private String image;
    private List<String> genres;
    private String aired;
    private String synopsis;


    @ManyToOne
    @JoinColumn(name = "utente")
    private Utente utente ;

    public AnimeFavorite(long idAnime, String title, String image, List<String> genres, String aired, String synopsis, Utente utente) {
        this.idAnime = idAnime;
        this.title = title;
        this.image = image;
        this.genres = genres;
        this.aired = aired;
        this.synopsis = synopsis;
        this.utente = utente;
    }
}
