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

        URI location = uriBuilder
                                .path("/api/gamereviewupvotes/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(gamereviewupvoteMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GameReviewUpvoteDto>> createAllGameReviewUpvotes(
            @Valid @RequestBody List<GameReviewUpvoteDto> gamereviewupvoteDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GameReviewUpvote> entities = gamereviewupvoteMapper.toEntityList(gamereviewupvoteDtoList);
        List<GameReviewUpvote> savedEntities = gamereviewupvoteService.saveAll(entities);

        URI location = uriBuilder.path("/api/gamereviewupvotes").build().toUri();

        return ResponseEntity.created(location).body(gamereviewupvoteMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameReviewUpvoteDto> updateGameReviewUpvote(
            @PathVariable Long id,
            @Valid @RequestBody GameReviewUpvoteDto gamereviewupvoteDto) {


        GameReviewUpvote entityToUpdate = gamereviewupvoteMapper.toEntity(gamereviewupvoteDto);
        GameReviewUpvote updatedEntity = gamereviewupvoteService.update(id, entityToUpdate);

        return ResponseEntity.ok(gamereviewupvoteMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameReviewUpvote(@PathVariable Long id) {
        boolean deleted = gamereviewupvoteService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}