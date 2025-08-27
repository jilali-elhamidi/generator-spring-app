package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameAchievementDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameAchievementSimpleDto;
import com.example.modules.entertainment_ecosystem.model.GameAchievement;
import com.example.modules.entertainment_ecosystem.mapper.GameAchievementMapper;
import com.example.modules.entertainment_ecosystem.service.GameAchievementService;
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
 * Controller for managing GameAchievement entities.
 */
@RestController
@RequestMapping("/api/gameachievements")
public class GameAchievementController extends BaseController<GameAchievement, GameAchievementDto, GameAchievementSimpleDto> {

    public GameAchievementController(GameAchievementService gameachievementService,
                                    GameAchievementMapper gameachievementMapper) {
        super(gameachievementService, gameachievementMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GameAchievementDto>> getAllGameAchievements(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GameAchievementDto>> searchGameAchievements(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(GameAchievement.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameAchievementDto> getGameAchievementById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GameAchievementDto> createGameAchievement(
            @Valid @RequestBody GameAchievementDto gameachievementDto,
            UriComponentsBuilder uriBuilder) {

        GameAchievement entity = mapper.toEntity(gameachievementDto);
        GameAchievement saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/gameachievements/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GameAchievementDto>> createAllGameAchievements(
            @Valid @RequestBody List<GameAchievementDto> gameachievementDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GameAchievement> entities = mapper.toEntityList(gameachievementDtoList);
        List<GameAchievement> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/gameachievements").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameAchievementDto> updateGameAchievement(
            @PathVariable Long id,
            @Valid @RequestBody GameAchievementDto gameachievementDto) {

        GameAchievement entityToUpdate = mapper.toEntity(gameachievementDto);
        GameAchievement updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameAchievement(@PathVariable Long id) {
        return doDelete(id);
    }
}