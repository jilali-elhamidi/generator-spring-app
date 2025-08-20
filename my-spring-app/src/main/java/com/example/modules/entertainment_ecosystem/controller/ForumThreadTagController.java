package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ForumThreadTagDto;
import com.example.modules.entertainment_ecosystem.model.ForumThreadTag;
import com.example.modules.entertainment_ecosystem.mapper.ForumThreadTagMapper;
import com.example.modules.entertainment_ecosystem.service.ForumThreadTagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/forumthreadtags")
public class ForumThreadTagController {

    private final ForumThreadTagService forumthreadtagService;
    private final ForumThreadTagMapper forumthreadtagMapper;

    public ForumThreadTagController(ForumThreadTagService forumthreadtagService,
                                    ForumThreadTagMapper forumthreadtagMapper) {
        this.forumthreadtagService = forumthreadtagService;
        this.forumthreadtagMapper = forumthreadtagMapper;
    }

    @GetMapping
    public ResponseEntity<List<ForumThreadTagDto>> getAllForumThreadTags() {
        List<ForumThreadTag> entities = forumthreadtagService.findAll();
        return ResponseEntity.ok(forumthreadtagMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumThreadTagDto> getForumThreadTagById(@PathVariable Long id) {
        return forumthreadtagService.findById(id)
                .map(forumthreadtagMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ForumThreadTagDto> createForumThreadTag(
            @Valid @RequestBody ForumThreadTagDto forumthreadtagDto,
            UriComponentsBuilder uriBuilder) {

        ForumThreadTag entity = forumthreadtagMapper.toEntity(forumthreadtagDto);
        ForumThreadTag saved = forumthreadtagService.save(entity);
        URI location = uriBuilder.path("/api/forumthreadtags/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(forumthreadtagMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ForumThreadTagDto> updateForumThreadTag(
                @PathVariable Long id,
                @RequestBody ForumThreadTagDto forumthreadtagDto) {

                // Transformer le DTO en entity pour le service
                ForumThreadTag entityToUpdate = forumthreadtagMapper.toEntity(forumthreadtagDto);

                // Appel du service update
                ForumThreadTag updatedEntity = forumthreadtagService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                ForumThreadTagDto updatedDto = forumthreadtagMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteForumThreadTag(@PathVariable Long id) {
                    boolean deleted = forumthreadtagService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}