package com.example.EpicOtakuSocial.controllers;


import com.example.EpicOtakuSocial.entities.AnimeFavorite;
import com.example.EpicOtakuSocial.entities.Commento;
import com.example.EpicOtakuSocial.entities.Post;
import com.example.EpicOtakuSocial.entities.Utente;
import com.example.EpicOtakuSocial.entities.anime.Datum;
import com.example.EpicOtakuSocial.exceptions.NotFoundException;
import com.example.EpicOtakuSocial.payloads.CommentoDTO;
import com.example.EpicOtakuSocial.payloads.UtenteDTO;
import com.example.EpicOtakuSocial.payloads.UtenteRespDTO;
import com.example.EpicOtakuSocial.services.AnimeFavoriteService;
import com.example.EpicOtakuSocial.services.CommentiService;
import com.example.EpicOtakuSocial.services.PostsService;
import com.example.EpicOtakuSocial.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/utenti")
public class UtentiController {

    @Autowired
    private UtentiService utentiService;
    @Autowired
    private PostsService postsService;
    @Autowired
    private CommentiService commentiService;

    @Autowired
    private AnimeFavoriteService animeFavoriteService;



    @GetMapping("/{utenteId}")
    public Utente getById(@PathVariable UUID utenteId) {
        return this.utentiService.findById(utenteId);
    }

    @GetMapping("/{utenteId}/posts")
    public List<Post> getPostByIdUser(@PathVariable UUID utenteId) {

        return this.utentiService.findById(utenteId).getPostList();
    }

    @GetMapping("/{utenteId}/comments")
    public List<Commento> getCommentsByIdUser(@PathVariable UUID utenteId) {
        return this.utentiService.findById(utenteId).getCommentiList();
    }
    @GetMapping("/{utenteId}/favoriteAnime")
    public List<AnimeFavorite> getFavoriteAnimeByIdUser(@PathVariable UUID utenteId) {
        return this.utentiService.findById(utenteId).getListaAnimePreferiti();
    }

    @GetMapping
    public Page<Utente> getAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sortBy) {
        return this.utentiService.findAll(page, size, sortBy);
    }

    @DeleteMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID utenteId) {
        this.utentiService.findByIdAndDelete(utenteId);
    }

    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UtenteRespDTO findByIdAndUpdate(@PathVariable UUID utenteId, @RequestBody @Validated UtenteDTO body) {
        return this.utentiService.findByIdAndUpdate(utenteId, body);
    }

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        return utenteCorrenteAutenticato;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        this.utentiService.findByIdAndDelete(utenteCorrenteAutenticato.getId());
    }

    @PutMapping("/me")
    public UtenteRespDTO updateProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato, @RequestBody @Validated UtenteDTO body) {
        return this.utentiService.findByIdAndUpdate(utenteCorrenteAutenticato.getId(), body);
    }


    // CLOUDINARY
    // UPLOAD IMMAGINE
    @PostMapping("/me/avatar")
    public Utente uploadImage(@RequestParam("avatar") MultipartFile img, @AuthenticationPrincipal Utente utenteCorrenteAutenticato) throws IOException {
        try {
            return this.utentiService.uploadImagine(img, utenteCorrenteAutenticato.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/me/posts/{postId}")
    public Post findByIdAndUpdate(@PathVariable UUID postId, @AuthenticationPrincipal Utente utenteCorrenteAutenticato,
                                  @RequestParam("message") String message,
                                  @RequestParam("avatar") MultipartFile file)throws IOException {
        List<Post> myPostList = postsService.findByAutore(utenteCorrenteAutenticato);

        //controllo che l'id passato sia uno della sua lista
        Post myPost = myPostList.stream().filter(prenotazione -> prenotazione.getId()
                .equals(postId)).findFirst().orElseThrow(() -> new NotFoundException("Il post con id" + postId + " non è stato trovato nella tua lista di post!"));

        return this.postsService.update(myPost.getId(), message, file);
    }
    @GetMapping("/me/posts")
    public List<Post> getMyPosts(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "20") int size,
                             @RequestParam(defaultValue = "id") String sortBy,
                             @AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        return this.postsService.findByAutore(utenteCorrenteAutenticato);
    }
    @DeleteMapping("/me/posts/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyPost(@PathVariable UUID postId, @AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        List<Post> myPostList = postsService.findByAutore(utenteCorrenteAutenticato);

        //controllo che l'id passato sia uno della sua lista
        Post myPost = myPostList.stream().filter(prenotazione -> prenotazione.getId()
                .equals(postId)).findFirst().orElseThrow(() -> new NotFoundException("Il post con id" + postId + " non è stato trovato nella tua lista di post!"));

        this.postsService.delete(myPost.getId());
    }
    @GetMapping("/me/commenti")
    public List<Commento> getMyCommenti(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size,
                                     @RequestParam(defaultValue = "id") String sortBy,
                                     @AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        return this.commentiService.findByAutoreCommento(utenteCorrenteAutenticato);
    }



    @PutMapping("/me/commenti/{commentoId}")
    public Commento findByIdAndUpdate(@PathVariable UUID commentoId, @AuthenticationPrincipal Utente utenteCorrenteAutenticato,
                                      @RequestBody @Validated CommentoDTO commentoDTO) {
        List<Commento> myCommentiList = commentiService.findByAutoreCommento(utenteCorrenteAutenticato);

        //controllo che l'id passato sia uno della sua lista
        Commento myCommento = myCommentiList.stream().filter(prenotazione -> prenotazione.getId()
                .equals(commentoId)).findFirst().orElseThrow(() -> new NotFoundException("Il Commento con id" + commentoId + " non è stato trovato nella tua lista di commenti!"));

        return this.commentiService.update(myCommento.getId(), commentoDTO);
    }
    @DeleteMapping("/me/commenti/{commentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyCommento(@PathVariable UUID commentoId, @AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        List<Commento> myCommentiList = commentiService.findByAutoreCommento(utenteCorrenteAutenticato);

        //controllo che l'id passato sia uno della sua lista
        Commento myCommento = myCommentiList.stream().filter(commento -> commento.getId()
                .equals(commentoId)).findFirst().orElseThrow(() -> new NotFoundException("Il Commento con id" + commentoId + " non è stato trovato nella tua lista di commenti!"));

        this.commentiService.delete(myCommento.getId());
    }

    @DeleteMapping("/me/anime/{idAnime}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimeFavorite(@AuthenticationPrincipal Utente utenteCorrenteAutenticato, @PathVariable Long idAnime) {
        AnimeFavorite animeFavorite = animeFavoriteService.findByIdAndUtente(idAnime,utenteCorrenteAutenticato);
        this.animeFavoriteService.delete(animeFavorite.getId());
    }
    @GetMapping("/me/anime")
    public List<AnimeFavorite> getMyAnime(@AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        return this.animeFavoriteService.findByUtente(utenteCorrenteAutenticato);
    }

    // CLOUDINARY
    // UPLOAD IMMAGINE
    @PostMapping("/{utenteId}/avatar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente uploadImage(@RequestParam("avatar") MultipartFile img, @PathVariable UUID utenteId) throws IOException {
        try {
            return this.utentiService.uploadImagine(img, utenteId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
