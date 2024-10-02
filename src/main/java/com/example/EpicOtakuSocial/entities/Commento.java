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
public class Commento extends Elemento {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private UUID postId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "commento_padre_id")
    private Commento commentoPadre;

    @OneToMany(mappedBy = "commentoPadre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commento> sottoCommenti = new ArrayList<>();



    @ManyToOne
    @JoinColumn(name = "autore_commento")
    private Utente autoreCommento;

    public Commento(String text, LocalDateTime ora, Integer numeroLike, Integer numeroDislike, UUID postId, Post post) {
        super(text, ora, numeroLike, numeroDislike);
        this.postId = post.getId();
        this.post = post;
    }

    @Override
    public void addCommento(Commento sottoCommento) {
        sottoCommenti.add(sottoCommento);
        sottoCommento.setCommentoPadre(this);
        // Imposta il commento corrente come padre del sotto-commento
    }

    @Override
    public void deleteCommento(Commento sottoCommento) {
        sottoCommenti.remove(sottoCommento);
    }



    @Override
    public String getTipoElemento() {
        return "Commento";
    }
}
