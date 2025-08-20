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
        URI location = uriBuilder.path("/api/gameexpansionpacks/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(gameexpansionpackMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<GameExpansionPackDto> updateGameExpansionPack(
                @PathVariable Long id,
                @RequestBody GameExpansionPackDto gameexpansionpackDto) {

                // Transformer le DTO en entity pour le service
                GameExpansionPack entityToUpdate = gameexpansionpackMapper.toEntity(gameexpansionpackDto);

                // Appel du service update
                GameExpansionPack updatedEntity = gameexpansionpackService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                GameExpansionPackDto updatedDto = gameexpansionpackMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteGameExpansionPack(@PathVariable Long id) {
                    boolean deleted = gameexpansionpackService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}