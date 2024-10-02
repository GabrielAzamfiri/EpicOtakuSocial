package com.example.EpicOtakuSocial.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "elemento")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Elemento {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String text;
    private LocalDateTime ora;
    private Integer numeroLike;
    private Integer numeroDislike;

    public Elemento(String text, LocalDateTime ora, Integer numeroLike, Integer numeroDislike) {
        this.text = text;
        this.ora = ora;
        this.numeroLike = numeroLike;
        this.numeroDislike = numeroDislike;
    }

    public abstract  void  addCommento(Commento commento);
    public abstract void deleteCommento(Commento commento);
    public abstract String getTipoElemento();
}
