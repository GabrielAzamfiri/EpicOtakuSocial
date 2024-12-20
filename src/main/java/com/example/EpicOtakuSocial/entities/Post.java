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
import java.util.stream.Collectors;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post extends Elemento {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String file;
    private Long animeId;


    @ManyToOne
    @JoinColumn(name = "autore")
    private Utente autore;

    @JsonIgnore
    @OneToMany(mappedBy = "post" , cascade = CascadeType.REMOVE)
    private List<Commento> listaCommenti = new ArrayList<>();


    public Post(String text, LocalDateTime ora,
                String file, Utente autore, Long animeId) {
        super(text, ora);
        this.file = file;
        this.autore = autore;
        this.animeId= animeId;
    }

    public List<Commento> getCommentiPrincipali() {
        return listaCommenti.stream()
                .filter(commento -> commento.getCommentoPadre() == null)
                .collect(Collectors.toList());
    }

    @Override
    public void addCommento(Commento commento) {
        if (commento.getCommentoPadre() == null) {
            this.listaCommenti.add(commento);
        }
    }

    @Override
    public void deleteCommento(Commento commento) {
        this.listaCommenti.remove(commento);
    }

    @Override
    public String getTipoElemento() {
        return "Post";
    }

    @Override
    public void addLike(UUID utente) {
       this.numeroLike.add(utente);
        this.numeroDislike.remove(utente);
    }

    @Override
    public void removeLike(UUID utente) {
        this.numeroLike.remove(utente);
    }

    @Override
    public void addDislike(UUID utente) {
        this.numeroLike.remove(utente);
        this.numeroDislike.add(utente);

    }

    @Override
    public void removeDislike(UUID utente) {
        this.numeroDislike.remove(utente);
    }
}
