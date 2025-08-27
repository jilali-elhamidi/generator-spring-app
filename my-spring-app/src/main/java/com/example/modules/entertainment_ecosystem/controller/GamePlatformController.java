package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GamePlatformDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GamePlatformSimpleDto;
import com.example.modules.entertainment_ecosystem.model.GamePlatform;
import com.example.modules.entertainment_ecosystem.mapper.GamePlatformMapper;
import com.example.modules.entertainment_ecosystem.service.GamePlatformService;
import com.example.core.controller.BaseController;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing GamePlatform entities.
 */
@RestController
@RequestMapping("/api/gameplatforms")
public class GamePlatformController extends BaseController<GamePlatform, GamePlatformDto, GamePlatformSimpleDto> {

    public GamePlatformController(GamePlatformService gameplatformService,
                                    GamePlatformMapper gameplatformMapper) {
        super(gameplatformService, gameplatformMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GamePlatformDto>> getAllGamePlatforms(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GamePlatformDto>> searchGamePlatforms(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(GamePlatform.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GamePlatformDto> getGamePlatformById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GamePlatformDto> createGamePlatform(
            @Valid @RequestBody GamePlatformDto gameplatformDto,
            UriComponentsBuilder uriBuilder) {

        GamePlatform entity = mapper.toEntity(gameplatformDto);
        GamePlatform saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/gameplatforms/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GamePlatformDto>> createAllGamePlatforms(
            @Valid @RequestBody List<GamePlatformDto> gameplatformDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GamePlatform> entities = mapper.toEntityList(gameplatformDtoList);
        List<GamePlatform> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/gameplatforms").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GamePlatformDto> updateGamePlatform(
            @PathVariable Long id,
            @Valid @RequestBody GamePlatformDto gameplatformDto) {

        GamePlatform entityToUpdate = mapper.toEntity(gameplatformDto);
        GamePlatform updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGamePlatform(@PathVariable Long id) {
        return doDelete(id);
    }
}