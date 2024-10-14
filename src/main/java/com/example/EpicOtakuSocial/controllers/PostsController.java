package com.example.EpicOtakuSocial.controllers;
import com.example.EpicOtakuSocial.entities.Post;
import com.example.EpicOtakuSocial.entities.Utente;
import com.example.EpicOtakuSocial.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
    @GetMapping("/{animeId}")
    public List<Post> getByAnimeId(@PathVariable Long animeId) {
        return this.postsService.findByAnimeId(animeId);
    }

    @PostMapping("/{animeId}/crea")
    @PreAuthorize("hasAnyAuthority('ADMIN','BASIC_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Post save( @RequestParam("message") String message, @RequestParam("avatar") MultipartFile file,
                      @AuthenticationPrincipal Utente utenteCorrenteAutenticato, @PathVariable String animeId)throws IOException{

        return this.postsService.save(message,file, utenteCorrenteAutenticato, Long.valueOf(animeId));
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
