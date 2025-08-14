package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameReviewUpvoteDto;
import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;
import com.example.modules.entertainment_ecosystem.mapper.GameReviewUpvoteMapper;
import com.example.modules.entertainment_ecosystem.service.GameReviewUpvoteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gamereviewupvotes")
public class GameReviewUpvoteController {

    private final GameReviewUpvoteService gamereviewupvoteService;
    private final GameReviewUpvoteMapper gamereviewupvoteMapper;

    public GameReviewUpvoteController(GameReviewUpvoteService gamereviewupvoteService,
                                    GameReviewUpvoteMapper gamereviewupvoteMapper) {
        this.gamereviewupvoteService = gamereviewupvoteService;
        this.gamereviewupvoteMapper = gamereviewupvoteMapper;
    }

    @GetMapping
    public ResponseEntity<List<GameReviewUpvoteDto>> getAllGameReviewUpvotes() {
        List<GameReviewUpvote> entities = gamereviewupvoteService.findAll();
        return ResponseEntity.ok(gamereviewupvoteMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameReviewUpvoteDto> getGameReviewUpvoteById(@PathVariable Long id) {
        return gamereviewupvoteService.findById(id)
                .map(gamereviewupvoteMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GameReviewUpvoteDto> createGameReviewUpvote(
            @Valid @RequestBody GameReviewUpvoteDto gamereviewupvoteDto,
            UriComponentsBuilder uriBuilder) {

        GameReviewUpvote entity = gamereviewupvoteMapper.toEntity(gamereviewupvoteDto);
        GameReviewUpvote saved = gamereviewupvoteService.save(entity);
        URI location = uriBuilder.path("/api/gamereviewupvotes/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(gamereviewupvoteMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<GameReviewUpvoteDto> updateGameReviewUpvote(
                @PathVariable Long id,
                @Valid @RequestBody GameReviewUpvoteDto gamereviewupvoteDto) {

                try {
                // Récupérer l'entité existante avec Optional
                GameReviewUpvote existing = gamereviewupvoteService.findById(id)
                .orElseThrow(() -> new RuntimeException("GameReviewUpvote not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                gamereviewupvoteMapper.updateEntityFromDto(gamereviewupvoteDto, existing);

                // Sauvegarde
                GameReviewUpvote updatedEntity = gamereviewupvoteService.save(existing);

                return ResponseEntity.ok(gamereviewupvoteMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameReviewUpvote(@PathVariable Long id) {
        gamereviewupvoteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}