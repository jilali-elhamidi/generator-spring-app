package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ForumModeratorDto;
import com.example.modules.entertainment_ecosystem.model.ForumModerator;
import com.example.modules.entertainment_ecosystem.mapper.ForumModeratorMapper;
import com.example.modules.entertainment_ecosystem.service.ForumModeratorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/forummoderators")
public class ForumModeratorController {

    private final ForumModeratorService forummoderatorService;
    private final ForumModeratorMapper forummoderatorMapper;

    public ForumModeratorController(ForumModeratorService forummoderatorService,
                                    ForumModeratorMapper forummoderatorMapper) {
        this.forummoderatorService = forummoderatorService;
        this.forummoderatorMapper = forummoderatorMapper;
    }

    @GetMapping
    public ResponseEntity<List<ForumModeratorDto>> getAllForumModerators() {
        List<ForumModerator> entities = forummoderatorService.findAll();
        return ResponseEntity.ok(forummoderatorMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumModeratorDto> getForumModeratorById(@PathVariable Long id) {
        return forummoderatorService.findById(id)
                .map(forummoderatorMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ForumModeratorDto> createForumModerator(
            @Valid @RequestBody ForumModeratorDto forummoderatorDto,
            UriComponentsBuilder uriBuilder) {

        ForumModerator entity = forummoderatorMapper.toEntity(forummoderatorDto);
        ForumModerator saved = forummoderatorService.save(entity);
        URI location = uriBuilder.path("/api/forummoderators/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(forummoderatorMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ForumModeratorDto> updateForumModerator(
                @PathVariable Long id,
                @RequestBody ForumModeratorDto forummoderatorDto) {

                // Transformer le DTO en entity pour le service
                ForumModerator entityToUpdate = forummoderatorMapper.toEntity(forummoderatorDto);

                // Appel du service update
                ForumModerator updatedEntity = forummoderatorService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                ForumModeratorDto updatedDto = forummoderatorMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteForumModerator(@PathVariable Long id) {
                    boolean deleted = forummoderatorService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}