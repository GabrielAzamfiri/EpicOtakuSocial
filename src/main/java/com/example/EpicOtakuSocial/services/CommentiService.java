package com.example.EpicOtakuSocial.services;

import com.example.EpicOtakuSocial.entities.Commento;
import com.example.EpicOtakuSocial.entities.Post;
import com.example.EpicOtakuSocial.entities.Utente;
import com.example.EpicOtakuSocial.exceptions.BadRequestException;
import com.example.EpicOtakuSocial.exceptions.NotFoundException;
import com.example.EpicOtakuSocial.payloads.CommentoDTO;
import com.example.EpicOtakuSocial.repositories.CommentiRepository;
import com.example.EpicOtakuSocial.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentiService {
    @Autowired
    private CommentiRepository commentiRepository;

    @Autowired
    private PostsRepository postsRepository;

    public Page<Commento> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.commentiRepository.findAll(pageable);
    }

    public Commento findById(UUID commentoId) {

        return this.commentiRepository.findById(commentoId)
                .orElseThrow(() -> new NotFoundException(commentoId));
    }

    public List<Commento> findByAutoreCommento(Utente autoreCommento) {
        return this.commentiRepository.findByAutoreCommento(autoreCommento);
    }

    public Commento save(Utente autoreCommento, CommentoDTO commentoDTO) {

        UUID uuidElemento;
        try {
            uuidElemento =  UUID.fromString(commentoDTO.elementoCommentato());
        }catch (Exception e){
            throw new BadRequestException("L'id inserito non è valido! Necessario inserire un ID di Tipo UUID");
        }

        Post elemento = postsRepository.findById(uuidElemento)
                .orElseThrow(() -> new NotFoundException(uuidElemento));

        Commento nuovoCommento = new Commento(commentoDTO.commento(),LocalDateTime.now(),
                uuidElemento,elemento);

        nuovoCommento.setAutoreCommento(autoreCommento);


        elemento.addCommento(nuovoCommento);
        postsRepository.save(elemento);

        return commentiRepository.save(nuovoCommento);


    }
    public Commento saveSottoCommento(Utente autoreCommento, CommentoDTO commentoDTO, UUID idCommentoPadre) {

        UUID uuidElemento;
        try {
            uuidElemento =  UUID.fromString(commentoDTO.elementoCommentato());
        }catch (Exception e){
            throw new BadRequestException("L'id inserito non è valido! Necessario inserire un ID di Tipo UUID");
        }

        Post elemento = postsRepository.findById(uuidElemento)
                .orElseThrow(() -> new NotFoundException(uuidElemento));

        Commento nuovoSottoCommento = new Commento(commentoDTO.commento(),LocalDateTime.now(),uuidElemento,elemento);
        nuovoSottoCommento.setAutoreCommento(autoreCommento);

        Optional<Commento> commentoPadreOpt = commentiRepository.findById(idCommentoPadre);
        if (commentoPadreOpt.isPresent()) {
            Commento commentoPadre = commentoPadreOpt.get();

            commentoPadre.addCommento(nuovoSottoCommento);

            return commentiRepository.save(commentoPadre);
        } else {
            throw new NotFoundException("Commento padre non trovato con ID: " + idCommentoPadre);
        }
    }

    public Commento update(UUID commentoId , CommentoDTO commentoDTO) {

        Commento commento = this.findById(commentoId);
        commento.setText(commentoDTO.commento());
        commento.setOra(LocalDateTime.now());

        return commentiRepository.save(commento);
    }

    public void delete(UUID commentoId) {
        Commento commento = this.findById(commentoId);
        this.commentiRepository.delete(commento);
    }
}
