package com.example.EpicOtakuSocial.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.EpicOtakuSocial.entities.Utente;
import com.example.EpicOtakuSocial.enums.RuoloUser;
import com.example.EpicOtakuSocial.exceptions.BadRequestException;
import com.example.EpicOtakuSocial.exceptions.NotFoundException;
import com.example.EpicOtakuSocial.payloads.UtenteDTO;
import com.example.EpicOtakuSocial.payloads.UtenteRespDTO;
import com.example.EpicOtakuSocial.repositories.UtentiRepository;
import com.example.EpicOtakuSocial.tools.Mailgun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UtentiService {
    @Autowired
    private UtentiRepository utentiRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private Mailgun mailSander;

    public Utente findByEmail(String email) {
        return utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con l'email " + email + " non è stato trovato."));
    }

    public UtenteRespDTO saveUtente(UtenteDTO body) {

        this.utentiRepository.findByEmail(body.email()).ifPresent(author -> {
            throw new BadRequestException("L'email " + body.email() + " è già in uso.");
        });

        Utente newUtente = new Utente(body.username(),body.nome(),body.cognome(),body.email(),bcrypt.encode(body.password()), RuoloUser.BASIC_USER,  "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());

        // salvo il nuovo record
        Utente savedUtente = this.utentiRepository.save(newUtente);

        // invio email conferma registrazione
        mailSander.sendRegistrationEmail(savedUtente);

        return new UtenteRespDTO(savedUtente.getId());
    }


    // cerco tutti gli utenti
    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utentiRepository.findAll(pageable);
    }


    // cerco utenti byId

    public Utente findById(UUID utenteId) {
        return this.utentiRepository.findById(utenteId)
                .orElseThrow(() -> new NotFoundException("L'utente con l'id " + utenteId + " non è stato trovato."));
    }

    // delete utente
    public void findByIdAndDelete(UUID utenteId) {
        Utente trovato = this.findById(utenteId);
        this.utentiRepository.delete(trovato);
    }

    public UtenteRespDTO findByIdAndUpdate(UUID utenteId, UtenteDTO body) {

        this.utentiRepository.findByEmail(body.email()).ifPresent(author -> {
            if (!author.getId().equals(utenteId)) {
                throw new BadRequestException("L'email " + body.email() + " è già in uso.");
            }
        });


        Utente trovato = this.findById(utenteId);
        trovato.setUsername(body.username());
        trovato.setEmail(body.email());
        trovato.setPassword(bcrypt.encode(body.password()));
        trovato.setNome(body.nome());
        trovato.setCognome(body.cognome());


        // salvo il nuovo record
        return new UtenteRespDTO(this.utentiRepository.save(trovato).getId());

    }

    // upload immagine
    public Utente uploadImagine(MultipartFile file, UUID utenteId) throws IOException {
        Utente trovato = this.findById(utenteId);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");

        trovato.setAvatar(url);

        return utentiRepository.save(trovato);
    }
}
