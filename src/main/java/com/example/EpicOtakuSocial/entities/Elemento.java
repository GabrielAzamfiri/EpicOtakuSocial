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
    public List<UUID> numeroLike= new ArrayList<>();
    public List<UUID> numeroDislike = new ArrayList<>();

    public Elemento(String text, LocalDateTime ora) {
        this.text = text;
        this.ora = ora;

    }

    public abstract  void  addCommento(Commento commento);
    public abstract void deleteCommento(Commento commento);
    public abstract String getTipoElemento();
    public abstract  void  addLike(UUID utente);
    public abstract  void  removeLike(UUID utente);

    public abstract  void  addDislike(UUID utente);
    public abstract  void  removeDislike(UUID utente);

}
