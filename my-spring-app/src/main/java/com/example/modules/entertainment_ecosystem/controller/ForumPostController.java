package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ForumPostDto;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.mapper.ForumPostMapper;
import com.example.modules.entertainment_ecosystem.service.ForumPostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/forumposts")
public class ForumPostController {

    private final ForumPostService forumpostService;
    private final ForumPostMapper forumpostMapper;

    public ForumPostController(ForumPostService forumpostService,
                                    ForumPostMapper forumpostMapper) {
        this.forumpostService = forumpostService;
        this.forumpostMapper = forumpostMapper;
    }

    @GetMapping
    public ResponseEntity<List<ForumPostDto>> getAllForumPosts() {
        List<ForumPost> entities = forumpostService.findAll();
        return ResponseEntity.ok(forumpostMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumPostDto> getForumPostById(@PathVariable Long id) {
        return forumpostService.findById(id)
                .map(forumpostMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ForumPostDto> createForumPost(
            @Valid @RequestBody ForumPostDto forumpostDto,
            UriComponentsBuilder uriBuilder) {

        ForumPost entity = forumpostMapper.toEntity(forumpostDto);
        ForumPost saved = forumpostService.save(entity);
        URI location = uriBuilder.path("/api/forumposts/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(forumpostMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ForumPostDto> updateForumPost(
                @PathVariable Long id,
                @RequestBody ForumPostDto forumpostDto) {

                // Transformer le DTO en entity pour le service
                ForumPost entityToUpdate = forumpostMapper.toEntity(forumpostDto);

                // Appel du service update
                ForumPost updatedEntity = forumpostService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                ForumPostDto updatedDto = forumpostMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteForumPost(@PathVariable Long id) {
                    boolean deleted = forumpostService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}