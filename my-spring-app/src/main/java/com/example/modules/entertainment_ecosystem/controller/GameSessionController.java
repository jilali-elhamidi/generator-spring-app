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

        URI location = uriBuilder
                                .path("/api/gamesessions/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(gamesessionMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GameSessionDto>> createAllGameSessions(
            @Valid @RequestBody List<GameSessionDto> gamesessionDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GameSession> entities = gamesessionMapper.toEntityList(gamesessionDtoList);
        List<GameSession> savedEntities = gamesessionService.saveAll(entities);

        URI location = uriBuilder.path("/api/gamesessions").build().toUri();

        return ResponseEntity.created(location).body(gamesessionMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameSessionDto> updateGameSession(
            @PathVariable Long id,
            @Valid @RequestBody GameSessionDto gamesessionDto) {


        GameSession entityToUpdate = gamesessionMapper.toEntity(gamesessionDto);
        GameSession updatedEntity = gamesessionService.update(id, entityToUpdate);

        return ResponseEntity.ok(gamesessionMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameSession(@PathVariable Long id) {
        boolean deleted = gamesessionService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}