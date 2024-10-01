package com.example.EpicOtakuSocial.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "commenti")
@Getter
@Setter
@NoArgsConstructor
public class Commento {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String commento;
    private LocalDateTime ora;
    private Integer numeroLike;
    private Integer numeroDislike;
    private UUID postId;

    @JsonIgnore
    @ManyToMany
    private List<Commento> listaCommenti = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;


    @ManyToOne
    @JoinColumn(name = "autore_commento")
    private Utente autoreCommento;

    public Commento(String commento, Post post) {
        this.commento = commento;
        this.ora = LocalDateTime.now();
        this.numeroLike = 0;
        this.numeroDislike = 0;
        this.post = post;
        this.postId = post.getId();
    }
}
