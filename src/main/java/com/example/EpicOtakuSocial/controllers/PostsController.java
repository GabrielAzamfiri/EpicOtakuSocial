package com.example.EpicOtakuSocial.controllers;


import com.example.EpicOtakuSocial.entities.Post;
import com.example.EpicOtakuSocial.entities.Utente;
import com.example.EpicOtakuSocial.exceptions.BadRequestException;
import com.example.EpicOtakuSocial.payloads.PostDTO;
import com.example.EpicOtakuSocial.payloads.UtenteDTO;
import com.example.EpicOtakuSocial.payloads.UtenteRespDTO;
import com.example.EpicOtakuSocial.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private PostsService postsService;

    @GetMapping("/{postId}")
    public Post getById(@PathVariable UUID postId) {
        return this.postsService.findById(postId);
    }

    @GetMapping
    public Page<Post> getAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size,
                               @RequestParam(defaultValue = "id") String sortBy) {
        return this.postsService.findAll(page, size, sortBy);
    }

    @PostMapping("/crea")
    @PreAuthorize("hasAnyAuthority('ADMIN','BASIC_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Post save( @RequestParam("message") String message, @RequestParam("avatar") MultipartFile file,
                      @AuthenticationPrincipal Utente utenteCorrenteAutenticato)throws IOException{


            return this.postsService.save(message,file, utenteCorrenteAutenticato);

    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID postId) {
        this.postsService.delete(postId);
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Post findByIdAndUpdate(@PathVariable UUID postId, @RequestParam("message") String message,
                                  @RequestParam("avatar") MultipartFile file)throws IOException {
        return this.postsService.update(postId, message, file);
    }

}
