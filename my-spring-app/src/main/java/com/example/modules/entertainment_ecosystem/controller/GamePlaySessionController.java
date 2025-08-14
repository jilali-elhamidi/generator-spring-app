package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GamePlaySessionDto;
import com.example.modules.entertainment_ecosystem.model.GamePlaySession;
import com.example.modules.entertainment_ecosystem.mapper.GamePlaySessionMapper;
import com.example.modules.entertainment_ecosystem.service.GamePlaySessionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gameplaysessions")
public class GamePlaySessionController {

    private final GamePlaySessionService gameplaysessionService;
    private final GamePlaySessionMapper gameplaysessionMapper;

    public GamePlaySessionController(GamePlaySessionService gameplaysessionService,
                                    GamePlaySessionMapper gameplaysessionMapper) {
        this.gameplaysessionService = gameplaysessionService;
        this.gameplaysessionMapper = gameplaysessionMapper;
    }

    @GetMapping
    public ResponseEntity<List<GamePlaySessionDto>> getAllGamePlaySessions() {
        List<GamePlaySession> entities = gameplaysessionService.findAll();
        return ResponseEntity.ok(gameplaysessionMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GamePlaySessionDto> getGamePlaySessionById(@PathVariable Long id) {
        return gameplaysessionService.findById(id)
                .map(gameplaysessionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GamePlaySessionDto> createGamePlaySession(
            @Valid @RequestBody GamePlaySessionDto gameplaysessionDto,
            UriComponentsBuilder uriBuilder) {

        GamePlaySession entity = gameplaysessionMapper.toEntity(gameplaysessionDto);
        GamePlaySession saved = gameplaysessionService.save(entity);
        URI location = uriBuilder.path("/api/gameplaysessions/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(gameplaysessionMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<GamePlaySessionDto> updateGamePlaySession(
                @PathVariable Long id,
                @Valid @RequestBody GamePlaySessionDto gameplaysessionDto) {

                try {
                // Récupérer l'entité existante avec Optional
                GamePlaySession existing = gameplaysessionService.findById(id)
                .orElseThrow(() -> new RuntimeException("GamePlaySession not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                gameplaysessionMapper.updateEntityFromDto(gameplaysessionDto, existing);

                // Sauvegarde
                GamePlaySession updatedEntity = gameplaysessionService.save(existing);

                return ResponseEntity.ok(gameplaysessionMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGamePlaySession(@PathVariable Long id) {
        gameplaysessionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}