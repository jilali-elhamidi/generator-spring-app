package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GamePlatformTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GamePlatformTypeSimpleDto;
import com.example.modules.entertainment_ecosystem.model.GamePlatformType;
import com.example.modules.entertainment_ecosystem.mapper.GamePlatformTypeMapper;
import com.example.modules.entertainment_ecosystem.service.GamePlatformTypeService;
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
 * Controller for managing GamePlatformType entities.
 */
@RestController
@RequestMapping("/api/gameplatformtypes")
public class GamePlatformTypeController extends BaseController<GamePlatformType, GamePlatformTypeDto, GamePlatformTypeSimpleDto> {

    public GamePlatformTypeController(GamePlatformTypeService gameplatformtypeService,
                                    GamePlatformTypeMapper gameplatformtypeMapper) {
        super(gameplatformtypeService, gameplatformtypeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GamePlatformTypeDto>> getAllGamePlatformTypes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GamePlatformTypeDto>> searchGamePlatformTypes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(GamePlatformType.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GamePlatformTypeDto> getGamePlatformTypeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GamePlatformTypeDto> createGamePlatformType(
            @Valid @RequestBody GamePlatformTypeDto gameplatformtypeDto,
            UriComponentsBuilder uriBuilder) {

        GamePlatformType entity = mapper.toEntity(gameplatformtypeDto);
        GamePlatformType saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/gameplatformtypes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GamePlatformTypeDto>> createAllGamePlatformTypes(
            @Valid @RequestBody List<GamePlatformTypeDto> gameplatformtypeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GamePlatformType> entities = mapper.toEntityList(gameplatformtypeDtoList);
        List<GamePlatformType> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/gameplatformtypes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GamePlatformTypeDto> updateGamePlatformType(
            @PathVariable Long id,
            @Valid @RequestBody GamePlatformTypeDto gameplatformtypeDto) {

        GamePlatformType entityToUpdate = mapper.toEntity(gameplatformtypeDto);
        GamePlatformType updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGamePlatformType(@PathVariable Long id) {
        return doDelete(id);
    }
}