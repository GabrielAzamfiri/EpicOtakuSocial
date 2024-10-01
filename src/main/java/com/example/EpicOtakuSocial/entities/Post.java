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
public class Post {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String text;
    private LocalDateTime ora;
    private String file;
    private Integer numeroLike;
    private Integer numeroDislike;

    @ManyToOne
    @JoinColumn(name = "autore")
    private Utente autore;


    @OneToMany(mappedBy = "post")
    private List<Commento> listaCommenti = new ArrayList<>();

    public Post(String text, String file) {
        this.text = text;
        this.ora = LocalDateTime.now();
        this.file = file;
        this.numeroLike = 0;
        this.numeroDislike = 0;
    }

    public void addCommento(Commento commento){
        this.listaCommenti.add(commento);
    }
    public void deleteCommento(Commento commento){
        this.listaCommenti.remove(commento);
    }
}
