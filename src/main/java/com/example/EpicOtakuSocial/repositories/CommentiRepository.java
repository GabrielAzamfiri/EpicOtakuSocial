package com.example.EpicOtakuSocial.repositories;

import com.example.EpicOtakuSocial.entities.Commento;
import com.example.EpicOtakuSocial.entities.Post;
import com.example.EpicOtakuSocial.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentiRepository extends JpaRepository<Commento, UUID> {
    List<Commento> findByAutoreCommento(Utente autoreCommento);

}
