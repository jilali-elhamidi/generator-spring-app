package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameReviewCommentDto;
import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import com.example.modules.entertainment_ecosystem.mapper.GameReviewCommentMapper;
import com.example.modules.entertainment_ecosystem.service.GameReviewCommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gamereviewcomments")
public class GameReviewCommentController {

    private final GameReviewCommentService gamereviewcommentService;
    private final GameReviewCommentMapper gamereviewcommentMapper;

    public GameReviewCommentController(GameReviewCommentService gamereviewcommentService,
                                    GameReviewCommentMapper gamereviewcommentMapper) {
        this.gamereviewcommentService = gamereviewcommentService;
        this.gamereviewcommentMapper = gamereviewcommentMapper;
    }

    @GetMapping
    public ResponseEntity<List<GameReviewCommentDto>> getAllGameReviewComments() {
        List<GameReviewComment> entities = gamereviewcommentService.findAll();
        return ResponseEntity.ok(gamereviewcommentMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameReviewCommentDto> getGameReviewCommentById(@PathVariable Long id) {
        return gamereviewcommentService.findById(id)
                .map(gamereviewcommentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GameReviewCommentDto> createGameReviewComment(
            @Valid @RequestBody GameReviewCommentDto gamereviewcommentDto,
            UriComponentsBuilder uriBuilder) {

        GameReviewComment entity = gamereviewcommentMapper.toEntity(gamereviewcommentDto);
        GameReviewComment saved = gamereviewcommentService.save(entity);
        URI location = uriBuilder.path("/api/gamereviewcomments/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(gamereviewcommentMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<GameReviewCommentDto> updateGameReviewComment(
                @PathVariable Long id,
                @RequestBody GameReviewCommentDto gamereviewcommentDto) {

                // Transformer le DTO en entity pour le service
                GameReviewComment entityToUpdate = gamereviewcommentMapper.toEntity(gamereviewcommentDto);

                // Appel du service update
                GameReviewComment updatedEntity = gamereviewcommentService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                GameReviewCommentDto updatedDto = gamereviewcommentMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteGameReviewComment(@PathVariable Long id) {
                    boolean deleted = gamereviewcommentService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}