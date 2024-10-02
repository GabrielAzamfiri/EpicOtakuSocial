package com.example.EpicOtakuSocial.entities;

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

    @ManyToOne
    @JoinColumn(name = "autore")
    private Utente autore;


    @OneToMany(mappedBy = "post")
    private List<Commento> listaCommenti = new ArrayList<>();


    public Post(String text, LocalDateTime ora, Integer numeroLike, Integer numeroDislike, String file, Utente autore) {
        super(text, ora, numeroLike, numeroDislike);
        this.file = file;
        this.autore = autore;
    }

    @Override
    public void addCommento(Commento commento) {
        this.listaCommenti.add(commento);
    }

    @Override
    public void deleteCommento(Commento commento) {
        this.listaCommenti.remove(commento);
    }

    @Override
    public String getTipoElemento() {
        return "Post";
    }
}
