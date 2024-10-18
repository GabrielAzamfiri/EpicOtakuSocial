package com.example.EpicOtakuSocial.entities;

import com.example.EpicOtakuSocial.entities.anime.Datum;
import com.example.EpicOtakuSocial.enums.RuoloUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"password", "listaAmici", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired", "authorities"})
public class Utente implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private RuoloUser ruolo;
    private String avatar;

    @JsonIgnore
    @OneToMany(mappedBy = "utente" , cascade = CascadeType.REMOVE)
    private List<AnimeFavorite> listaAnimePreferiti = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "autore", cascade = CascadeType.REMOVE)
    private List<Post> postList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "autoreCommento", cascade = CascadeType.REMOVE)
    private List<Commento> commentiList = new ArrayList<>();

    public Utente(String username, String nome, String cognome, String email, String password, RuoloUser ruolo, String avatar) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        this.avatar = avatar;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }

}
