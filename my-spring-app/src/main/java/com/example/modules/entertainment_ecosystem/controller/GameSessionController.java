package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameSessionDto;
import com.example.modules.entertainment_ecosystem.model.GameSession;
import com.example.modules.entertainment_ecosystem.mapper.GameSessionMapper;
import com.example.modules.entertainment_ecosystem.service.GameSessionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gamesessions")
public class GameSessionController {

    private final GameSessionService gamesessionService;
    private final GameSessionMapper gamesessionMapper;

    public GameSessionController(GameSessionService gamesessionService,
                                    GameSessionMapper gamesessionMapper) {
        this.gamesessionService = gamesessionService;
        this.gamesessionMapper = gamesessionMapper;
    }

    @GetMapping
    public ResponseEntity<List<GameSessionDto>> getAllGameSessions() {
        List<GameSession> entities = gamesessionService.findAll();
        return ResponseEntity.ok(gamesessionMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameSessionDto> getGameSessionById(@PathVariable Long id) {
        return gamesessionService.findById(id)
                .map(gamesessionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GameSessionDto> createGameSession(
            @Valid @RequestBody GameSessionDto gamesessionDto,
            UriComponentsBuilder uriBuilder) {

        GameSession entity = gamesessionMapper.toEntity(gamesessionDto);
        GameSession saved = gamesessionService.save(entity);
        URI location = uriBuilder.path("/api/gamesessions/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(gamesessionMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<GameSessionDto> updateGameSession(
                @PathVariable Long id,
                @Valid @RequestBody GameSessionDto gamesessionDto) {

                try {
                // Récupérer l'entité existante avec Optional
                GameSession existing = gamesessionService.findById(id)
                .orElseThrow(() -> new RuntimeException("GameSession not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                gamesessionMapper.updateEntityFromDto(gamesessionDto, existing);

                // Sauvegarde
                GameSession updatedEntity = gamesessionService.save(existing);

                return ResponseEntity.ok(gamesessionMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameSession(@PathVariable Long id) {
        gamesessionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}