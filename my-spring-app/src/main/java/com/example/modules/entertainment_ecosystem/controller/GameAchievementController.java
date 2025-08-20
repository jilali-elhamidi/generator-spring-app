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
                @RequestBody GameAchievementDto gameachievementDto) {

                // Transformer le DTO en entity pour le service
                GameAchievement entityToUpdate = gameachievementMapper.toEntity(gameachievementDto);

                // Appel du service update
                GameAchievement updatedEntity = gameachievementService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                GameAchievementDto updatedDto = gameachievementMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteGameAchievement(@PathVariable Long id) {
                    boolean deleted = gameachievementService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}