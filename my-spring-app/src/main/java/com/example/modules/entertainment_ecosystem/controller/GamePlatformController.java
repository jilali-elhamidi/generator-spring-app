package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GamePlatformDto;
import com.example.modules.entertainment_ecosystem.model.GamePlatform;
import com.example.modules.entertainment_ecosystem.mapper.GamePlatformMapper;
import com.example.modules.entertainment_ecosystem.service.GamePlatformService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gameplatforms")
public class GamePlatformController {

    private final GamePlatformService gameplatformService;
    private final GamePlatformMapper gameplatformMapper;

    public GamePlatformController(GamePlatformService gameplatformService,
                                    GamePlatformMapper gameplatformMapper) {
        this.gameplatformService = gameplatformService;
        this.gameplatformMapper = gameplatformMapper;
    }

    @GetMapping
    public ResponseEntity<List<GamePlatformDto>> getAllGamePlatforms() {
        List<GamePlatform> entities = gameplatformService.findAll();
        return ResponseEntity.ok(gameplatformMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GamePlatformDto> getGamePlatformById(@PathVariable Long id) {
        return gameplatformService.findById(id)
                .map(gameplatformMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GamePlatformDto> createGamePlatform(
            @Valid @RequestBody GamePlatformDto gameplatformDto,
            UriComponentsBuilder uriBuilder) {

        GamePlatform entity = gameplatformMapper.toEntity(gameplatformDto);
        GamePlatform saved = gameplatformService.save(entity);
        URI location = uriBuilder.path("/api/gameplatforms/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(gameplatformMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<GamePlatformDto> updateGamePlatform(
                @PathVariable Long id,
                @Valid @RequestBody GamePlatformDto gameplatformDto) {

                try {
                // Récupérer l'entité existante avec Optional
                GamePlatform existing = gameplatformService.findById(id)
                .orElseThrow(() -> new RuntimeException("GamePlatform not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                gameplatformMapper.updateEntityFromDto(gameplatformDto, existing);

                // Sauvegarde
                GamePlatform updatedEntity = gameplatformService.save(existing);

                return ResponseEntity.ok(gameplatformMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGamePlatform(@PathVariable Long id) {
        gameplatformService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}