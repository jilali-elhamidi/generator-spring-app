package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GamePlatformTypeDto;
import com.example.modules.entertainment_ecosystem.model.GamePlatformType;
import com.example.modules.entertainment_ecosystem.mapper.GamePlatformTypeMapper;
import com.example.modules.entertainment_ecosystem.service.GamePlatformTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gameplatformtypes")
public class GamePlatformTypeController {

    private final GamePlatformTypeService gameplatformtypeService;
    private final GamePlatformTypeMapper gameplatformtypeMapper;

    public GamePlatformTypeController(GamePlatformTypeService gameplatformtypeService,
                                    GamePlatformTypeMapper gameplatformtypeMapper) {
        this.gameplatformtypeService = gameplatformtypeService;
        this.gameplatformtypeMapper = gameplatformtypeMapper;
    }

    @GetMapping
    public ResponseEntity<List<GamePlatformTypeDto>> getAllGamePlatformTypes() {
        List<GamePlatformType> entities = gameplatformtypeService.findAll();
        return ResponseEntity.ok(gameplatformtypeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GamePlatformTypeDto> getGamePlatformTypeById(@PathVariable Long id) {
        return gameplatformtypeService.findById(id)
                .map(gameplatformtypeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GamePlatformTypeDto> createGamePlatformType(
            @Valid @RequestBody GamePlatformTypeDto gameplatformtypeDto,
            UriComponentsBuilder uriBuilder) {

        GamePlatformType entity = gameplatformtypeMapper.toEntity(gameplatformtypeDto);
        GamePlatformType saved = gameplatformtypeService.save(entity);
        URI location = uriBuilder.path("/api/gameplatformtypes/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(gameplatformtypeMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<GamePlatformTypeDto> updateGamePlatformType(
                @PathVariable Long id,
                @RequestBody GamePlatformTypeDto gameplatformtypeDto) {

                // Transformer le DTO en entity pour le service
                GamePlatformType entityToUpdate = gameplatformtypeMapper.toEntity(gameplatformtypeDto);

                // Appel du service update
                GamePlatformType updatedEntity = gameplatformtypeService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                GamePlatformTypeDto updatedDto = gameplatformtypeMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteGamePlatformType(@PathVariable Long id) {
                    boolean deleted = gameplatformtypeService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}