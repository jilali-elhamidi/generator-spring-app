package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GamePlaySessionStatDto;
import com.example.modules.entertainment_ecosystem.model.GamePlaySessionStat;
import com.example.modules.entertainment_ecosystem.mapper.GamePlaySessionStatMapper;
import com.example.modules.entertainment_ecosystem.service.GamePlaySessionStatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gameplaysessionstats")
public class GamePlaySessionStatController {

    private final GamePlaySessionStatService gameplaysessionstatService;
    private final GamePlaySessionStatMapper gameplaysessionstatMapper;

    public GamePlaySessionStatController(GamePlaySessionStatService gameplaysessionstatService,
                                    GamePlaySessionStatMapper gameplaysessionstatMapper) {
        this.gameplaysessionstatService = gameplaysessionstatService;
        this.gameplaysessionstatMapper = gameplaysessionstatMapper;
    }

    @GetMapping
    public ResponseEntity<List<GamePlaySessionStatDto>> getAllGamePlaySessionStats() {
        List<GamePlaySessionStat> entities = gameplaysessionstatService.findAll();
        return ResponseEntity.ok(gameplaysessionstatMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GamePlaySessionStatDto> getGamePlaySessionStatById(@PathVariable Long id) {
        return gameplaysessionstatService.findById(id)
                .map(gameplaysessionstatMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GamePlaySessionStatDto> createGamePlaySessionStat(
            @Valid @RequestBody GamePlaySessionStatDto gameplaysessionstatDto,
            UriComponentsBuilder uriBuilder) {

        GamePlaySessionStat entity = gameplaysessionstatMapper.toEntity(gameplaysessionstatDto);
        GamePlaySessionStat saved = gameplaysessionstatService.save(entity);
        URI location = uriBuilder.path("/api/gameplaysessionstats/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(gameplaysessionstatMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<GamePlaySessionStatDto> updateGamePlaySessionStat(
                @PathVariable Long id,
                @Valid @RequestBody GamePlaySessionStatDto gameplaysessionstatDto) {

                try {
                // Récupérer l'entité existante avec Optional
                GamePlaySessionStat existing = gameplaysessionstatService.findById(id)
                .orElseThrow(() -> new RuntimeException("GamePlaySessionStat not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                gameplaysessionstatMapper.updateEntityFromDto(gameplaysessionstatDto, existing);

                // Sauvegarde
                GamePlaySessionStat updatedEntity = gameplaysessionstatService.save(existing);

                return ResponseEntity.ok(gameplaysessionstatMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGamePlaySessionStat(@PathVariable Long id) {
        gameplaysessionstatService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}