package com.example.EpicOtakuSocial.repositories;

import com.example.EpicOtakuSocial.entities.Post;
import com.example.EpicOtakuSocial.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostsRepository extends JpaRepository<Post, UUID> {
    List<Post> findByAutore(Utente autore);
}
