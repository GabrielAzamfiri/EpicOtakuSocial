package com.example.EpicOtakuSocial.services;


import com.example.EpicOtakuSocial.entities.Utente;
import com.example.EpicOtakuSocial.exceptions.UnauthorizedException;
import com.example.EpicOtakuSocial.payloads.UtenteLoginDTO;
import com.example.EpicOtakuSocial.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredenzialiAndGeneraToken(UtenteLoginDTO body) {

        Utente trovato = this.utentiService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), trovato.getPassword())) {
            return jwtTools.createToken(trovato);
        } else {
            throw new UnauthorizedException("Credenziali errate");
        }
    }
}
