package com.example.EpicOtakuSocial.entities;

import com.example.EpicOtakuSocial.enums.RuoloUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@JsonIgnoreProperties({"password", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired", "authorities"})
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
    private RuoloUser ruolo;
    private String avatar;
    private List<Utente> listaAmici = new ArrayList<>();

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

    @Override
    public String getUsername() {
        return this.email;
    }
}
