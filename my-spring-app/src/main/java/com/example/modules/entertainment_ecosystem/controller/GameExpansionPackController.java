package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameExpansionPackDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameExpansionPackSimpleDto;
import com.example.modules.entertainment_ecosystem.model.GameExpansionPack;
import com.example.modules.entertainment_ecosystem.mapper.GameExpansionPackMapper;
import com.example.modules.entertainment_ecosystem.service.GameExpansionPackService;
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
 * Controller for managing GameExpansionPack entities.
 */
@RestController
@RequestMapping("/api/gameexpansionpacks")
public class GameExpansionPackController extends BaseController<GameExpansionPack, GameExpansionPackDto, GameExpansionPackSimpleDto> {

    public GameExpansionPackController(GameExpansionPackService gameexpansionpackService,
                                    GameExpansionPackMapper gameexpansionpackMapper) {
        super(gameexpansionpackService, gameexpansionpackMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GameExpansionPackDto>> getAllGameExpansionPacks(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GameExpansionPackDto>> searchGameExpansionPacks(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(GameExpansionPack.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameExpansionPackDto> getGameExpansionPackById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GameExpansionPackDto> createGameExpansionPack(
            @Valid @RequestBody GameExpansionPackDto gameexpansionpackDto,
            UriComponentsBuilder uriBuilder) {

        GameExpansionPack entity = mapper.toEntity(gameexpansionpackDto);
        GameExpansionPack saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/gameexpansionpacks/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GameExpansionPackDto>> createAllGameExpansionPacks(
            @Valid @RequestBody List<GameExpansionPackDto> gameexpansionpackDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GameExpansionPack> entities = mapper.toEntityList(gameexpansionpackDtoList);
        List<GameExpansionPack> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/gameexpansionpacks").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameExpansionPackDto> updateGameExpansionPack(
            @PathVariable Long id,
            @Valid @RequestBody GameExpansionPackDto gameexpansionpackDto) {

        GameExpansionPack entityToUpdate = mapper.toEntity(gameexpansionpackDto);
        GameExpansionPack updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameExpansionPack(@PathVariable Long id) {
        return doDelete(id);
    }
}