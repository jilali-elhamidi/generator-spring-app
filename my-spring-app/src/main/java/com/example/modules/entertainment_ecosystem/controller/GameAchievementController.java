package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameAchievementDto;
import com.example.modules.entertainment_ecosystem.model.GameAchievement;
import com.example.modules.entertainment_ecosystem.mapper.GameAchievementMapper;
import com.example.modules.entertainment_ecosystem.service.GameAchievementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gameachievements")
public class GameAchievementController {

    private final GameAchievementService gameachievementService;
    private final GameAchievementMapper gameachievementMapper;

    public GameAchievementController(GameAchievementService gameachievementService,
                                    GameAchievementMapper gameachievementMapper) {
        this.gameachievementService = gameachievementService;
        this.gameachievementMapper = gameachievementMapper;
    }

    @GetMapping
    public ResponseEntity<List<GameAchievementDto>> getAllGameAchievements() {
        List<GameAchievement> entities = gameachievementService.findAll();
        return ResponseEntity.ok(gameachievementMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameAchievementDto> getGameAchievementById(@PathVariable Long id) {
        return gameachievementService.findById(id)
                .map(gameachievementMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GameAchievementDto> createGameAchievement(
            @Valid @RequestBody GameAchievementDto gameachievementDto,
            UriComponentsBuilder uriBuilder) {

        GameAchievement entity = gameachievementMapper.toEntity(gameachievementDto);
        GameAchievement saved = gameachievementService.save(entity);
        URI location = uriBuilder.path("/api/gameachievements/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(gameachievementMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameAchievementDto> updateGameAchievement(
            @PathVariable Long id,
            @Valid @RequestBody GameAchievementDto gameachievementDto) {

        try {
            GameAchievement updatedEntity = gameachievementService.update(
                    id,
                    gameachievementMapper.toEntity(gameachievementDto)
            );
            return ResponseEntity.ok(gameachievementMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameAchievement(@PathVariable Long id) {
        gameachievementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}