package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameReviewDownvoteDto;
import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;
import com.example.modules.entertainment_ecosystem.mapper.GameReviewDownvoteMapper;
import com.example.modules.entertainment_ecosystem.service.GameReviewDownvoteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gamereviewdownvotes")
public class GameReviewDownvoteController {

    private final GameReviewDownvoteService gamereviewdownvoteService;
    private final GameReviewDownvoteMapper gamereviewdownvoteMapper;

    public GameReviewDownvoteController(GameReviewDownvoteService gamereviewdownvoteService,
                                    GameReviewDownvoteMapper gamereviewdownvoteMapper) {
        this.gamereviewdownvoteService = gamereviewdownvoteService;
        this.gamereviewdownvoteMapper = gamereviewdownvoteMapper;
    }

    @GetMapping
    public ResponseEntity<List<GameReviewDownvoteDto>> getAllGameReviewDownvotes() {
        List<GameReviewDownvote> entities = gamereviewdownvoteService.findAll();
        return ResponseEntity.ok(gamereviewdownvoteMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameReviewDownvoteDto> getGameReviewDownvoteById(@PathVariable Long id) {
        return gamereviewdownvoteService.findById(id)
                .map(gamereviewdownvoteMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GameReviewDownvoteDto> createGameReviewDownvote(
            @Valid @RequestBody GameReviewDownvoteDto gamereviewdownvoteDto,
            UriComponentsBuilder uriBuilder) {

        GameReviewDownvote entity = gamereviewdownvoteMapper.toEntity(gamereviewdownvoteDto);
        GameReviewDownvote saved = gamereviewdownvoteService.save(entity);
        URI location = uriBuilder.path("/api/gamereviewdownvotes/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(gamereviewdownvoteMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameReviewDownvoteDto> updateGameReviewDownvote(
            @PathVariable Long id,
            @Valid @RequestBody GameReviewDownvoteDto gamereviewdownvoteDto) {

        try {
            GameReviewDownvote updatedEntity = gamereviewdownvoteService.update(
                    id,
                    gamereviewdownvoteMapper.toEntity(gamereviewdownvoteDto)
            );
            return ResponseEntity.ok(gamereviewdownvoteMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameReviewDownvote(@PathVariable Long id) {
        gamereviewdownvoteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}