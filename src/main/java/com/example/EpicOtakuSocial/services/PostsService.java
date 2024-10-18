package com.example.EpicOtakuSocial.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.EpicOtakuSocial.entities.Post;
import com.example.EpicOtakuSocial.entities.Utente;
import com.example.EpicOtakuSocial.exceptions.BadRequestException;
import com.example.EpicOtakuSocial.exceptions.NotFoundException;
import com.example.EpicOtakuSocial.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private Cloudinary cloudinary;


    public Page<Post> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.postsRepository.findAll(pageable);
    }

    public Post findById(UUID postId) {
        Post post = this.postsRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Il post con l'id " + postId + " non è stato trovato."));
        post.setListaCommenti(post.getCommentiPrincipali());
        return post;
    }
    public List<Post> findByAnimeId(Long animeId) {
        return this.postsRepository.findByAnimeId(animeId);
    }

    public List<Post> findByAutore(Utente autore) {
        return this.postsRepository.findByAutore(autore);
    }

    public Post save(String message, MultipartFile file, Utente author, Long animeId) throws IOException {

        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        Post post = new Post(message,LocalDateTime.now(),url,author, animeId);

        return postsRepository.save(post);
    }

    public Post update(UUID postId, String message, MultipartFile file) throws IOException {

        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");

        Post post = this.findById(postId);

        post.setText(message);
        post.setFile(url);
        post.setOra(LocalDateTime.now());

        return postsRepository.save(post);
    }
    public Post addLike(UUID postId, UUID utente)  {
        Post post = this.findById(postId);
        boolean hasLiked =   post.numeroLike.stream().anyMatch(uuid -> uuid.equals(utente));
        if (hasLiked) {
            throw new BadRequestException("Hai già messo like al post!");
        }else {
            post.addLike(utente);
        }
        return postsRepository.save(post);
    }
    public Post removeLike(UUID postId, UUID utente)  {

        Post post = this.findById(postId);
        boolean hasLiked =   post.numeroLike.stream().anyMatch(uuid -> uuid.equals(utente));
        if (hasLiked) {
            post.removeLike(utente);
        }else {
            throw new BadRequestException("Nessun like da rimuovere!");
        }
        return postsRepository.save(post);
    }
    public Post addDislike(UUID postId, UUID utente) {

        Post post = this.findById(postId);
      boolean hasDisliked =   post.numeroDislike.stream().anyMatch(uuid -> uuid.equals(utente));
        if (hasDisliked) {
            throw new BadRequestException("Hai già messo dislike al post!");
        }else {
            post.addDislike(utente);
        }
        return postsRepository.save(post);
    }
    public Post removeDislike(UUID postId, UUID utente)  {

        Post post = this.findById(postId);
        boolean hasDisliked =   post.numeroDislike.stream().anyMatch(uuid -> uuid.equals(utente));
        if (hasDisliked) {
            post.removeDislike(utente);
        }else {
            throw new BadRequestException("Nessun dislike da rimuovere!");
        }
        return postsRepository.save(post);
    }

    public void delete(UUID postId) {
        Post post = this.findById(postId);
        this.postsRepository.delete(post);
    }
}
