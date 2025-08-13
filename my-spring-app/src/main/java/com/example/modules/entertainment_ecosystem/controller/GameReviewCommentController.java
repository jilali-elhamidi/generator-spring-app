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
            @Valid @RequestBody GameReviewCommentDto gamereviewcommentDto) {

        try {
            GameReviewComment updatedEntity = gamereviewcommentService.update(
                    id,
                    gamereviewcommentMapper.toEntity(gamereviewcommentDto)
            );
            return ResponseEntity.ok(gamereviewcommentMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameReviewComment(@PathVariable Long id) {
        gamereviewcommentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}