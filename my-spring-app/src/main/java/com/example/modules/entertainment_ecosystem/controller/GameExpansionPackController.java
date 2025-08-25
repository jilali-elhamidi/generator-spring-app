package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameExpansionPackDto;
import com.example.modules.entertainment_ecosystem.model.GameExpansionPack;
import com.example.modules.entertainment_ecosystem.mapper.GameExpansionPackMapper;
import com.example.modules.entertainment_ecosystem.service.GameExpansionPackService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gameexpansionpacks")
public class GameExpansionPackController {

    private final GameExpansionPackService gameexpansionpackService;
    private final GameExpansionPackMapper gameexpansionpackMapper;

    public GameExpansionPackController(GameExpansionPackService gameexpansionpackService,
                                    GameExpansionPackMapper gameexpansionpackMapper) {
        this.gameexpansionpackService = gameexpansionpackService;
        this.gameexpansionpackMapper = gameexpansionpackMapper;
    }

    @GetMapping
    public ResponseEntity<List<GameExpansionPackDto>> getAllGameExpansionPacks() {
        List<GameExpansionPack> entities = gameexpansionpackService.findAll();
        return ResponseEntity.ok(gameexpansionpackMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameExpansionPackDto> getGameExpansionPackById(@PathVariable Long id) {
        return gameexpansionpackService.findById(id)
                .map(gameexpansionpackMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GameExpansionPackDto> createGameExpansionPack(
            @Valid @RequestBody GameExpansionPackDto gameexpansionpackDto,
            UriComponentsBuilder uriBuilder) {

        GameExpansionPack entity = gameexpansionpackMapper.toEntity(gameexpansionpackDto);
        GameExpansionPack saved = gameexpansionpackService.save(entity);

        URI location = uriBuilder
                                .path("/api/gameexpansionpacks/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(gameexpansionpackMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GameExpansionPackDto>> createAllGameExpansionPacks(
            @Valid @RequestBody List<GameExpansionPackDto> gameexpansionpackDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GameExpansionPack> entities = gameexpansionpackMapper.toEntityList(gameexpansionpackDtoList);
        List<GameExpansionPack> savedEntities = gameexpansionpackService.saveAll(entities);

        URI location = uriBuilder.path("/api/gameexpansionpacks").build().toUri();

        return ResponseEntity.created(location).body(gameexpansionpackMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameExpansionPackDto> updateGameExpansionPack(
            @PathVariable Long id,
            @Valid @RequestBody GameExpansionPackDto gameexpansionpackDto) {


        GameExpansionPack entityToUpdate = gameexpansionpackMapper.toEntity(gameexpansionpackDto);
        GameExpansionPack updatedEntity = gameexpansionpackService.update(id, entityToUpdate);

        return ResponseEntity.ok(gameexpansionpackMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameExpansionPack(@PathVariable Long id) {
        boolean deleted = gameexpansionpackService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}