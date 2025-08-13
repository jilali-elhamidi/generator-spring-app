package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameReviewDto;
import com.example.modules.entertainment_ecosystem.model.GameReview;
import com.example.modules.entertainment_ecosystem.mapper.GameReviewMapper;
import com.example.modules.entertainment_ecosystem.service.GameReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gamereviews")
public class GameReviewController {

    private final GameReviewService gamereviewService;
    private final GameReviewMapper gamereviewMapper;

    public GameReviewController(GameReviewService gamereviewService,
                                    GameReviewMapper gamereviewMapper) {
        this.gamereviewService = gamereviewService;
        this.gamereviewMapper = gamereviewMapper;
    }

    @GetMapping
    public ResponseEntity<List<GameReviewDto>> getAllGameReviews() {
        List<GameReview> entities = gamereviewService.findAll();
        return ResponseEntity.ok(gamereviewMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameReviewDto> getGameReviewById(@PathVariable Long id) {
        return gamereviewService.findById(id)
                .map(gamereviewMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GameReviewDto> createGameReview(
            @Valid @RequestBody GameReviewDto gamereviewDto,
            UriComponentsBuilder uriBuilder) {

        GameReview entity = gamereviewMapper.toEntity(gamereviewDto);
        GameReview saved = gamereviewService.save(entity);
        URI location = uriBuilder.path("/api/gamereviews/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(gamereviewMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameReviewDto> updateGameReview(
            @PathVariable Long id,
            @Valid @RequestBody GameReviewDto gamereviewDto) {

        try {
            GameReview updatedEntity = gamereviewService.update(
                    id,
                    gamereviewMapper.toEntity(gamereviewDto)
            );
            return ResponseEntity.ok(gamereviewMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameReview(@PathVariable Long id) {
        gamereviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}