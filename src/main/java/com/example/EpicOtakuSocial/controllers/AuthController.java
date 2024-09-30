package com.example.EpicOtakuSocial.controllers;


import com.example.EpicOtakuSocial.exceptions.BadRequestException;
import com.example.EpicOtakuSocial.payloads.UtenteDTO;
import com.example.EpicOtakuSocial.payloads.UtenteLoginDTO;
import com.example.EpicOtakuSocial.payloads.UtenteLoginRespDTO;
import com.example.EpicOtakuSocial.payloads.UtenteRespDTO;
import com.example.EpicOtakuSocial.services.AuthService;
import com.example.EpicOtakuSocial.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173") // annotazione per abilitare CORS
public class AuthController {

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public UtenteLoginRespDTO login(@RequestBody @Validated UtenteLoginDTO body) {
        return new UtenteLoginRespDTO(this.authService.checkCredenzialiAndGeneraToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteRespDTO creaUtente(@RequestBody @Validated UtenteDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException(("Errori nel Payload. " + messages));
        } else {
            return new UtenteRespDTO(this.utentiService.saveUtente(body).utenteId());
        }
    }


}
