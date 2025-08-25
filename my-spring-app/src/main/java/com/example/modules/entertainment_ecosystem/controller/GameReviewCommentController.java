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

        URI location = uriBuilder
                                .path("/api/gamereviewcomments/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(gamereviewcommentMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GameReviewCommentDto>> createAllGameReviewComments(
            @Valid @RequestBody List<GameReviewCommentDto> gamereviewcommentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GameReviewComment> entities = gamereviewcommentMapper.toEntityList(gamereviewcommentDtoList);
        List<GameReviewComment> savedEntities = gamereviewcommentService.saveAll(entities);

        URI location = uriBuilder.path("/api/gamereviewcomments").build().toUri();

        return ResponseEntity.created(location).body(gamereviewcommentMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameReviewCommentDto> updateGameReviewComment(
            @PathVariable Long id,
            @Valid @RequestBody GameReviewCommentDto gamereviewcommentDto) {


        GameReviewComment entityToUpdate = gamereviewcommentMapper.toEntity(gamereviewcommentDto);
        GameReviewComment updatedEntity = gamereviewcommentService.update(id, entityToUpdate);

        return ResponseEntity.ok(gamereviewcommentMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameReviewComment(@PathVariable Long id) {
        boolean deleted = gamereviewcommentService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}