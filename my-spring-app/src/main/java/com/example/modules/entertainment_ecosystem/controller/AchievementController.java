package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.AchievementDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AchievementSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Achievement;
import com.example.modules.entertainment_ecosystem.mapper.AchievementMapper;
import com.example.modules.entertainment_ecosystem.service.AchievementService;
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
 * Controller for managing Achievement entities.
 */
@RestController
@RequestMapping("/api/achievements")
public class AchievementController extends BaseController<Achievement, AchievementDto, AchievementSimpleDto> {

    public AchievementController(AchievementService achievementService,
                                    AchievementMapper achievementMapper) {
        super(achievementService, achievementMapper);
    }

    @GetMapping
    public ResponseEntity<Page<AchievementDto>> getAllAchievements(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AchievementDto>> searchAchievements(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Achievement.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchievementDto> getAchievementById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<AchievementDto> createAchievement(
            @Valid @RequestBody AchievementDto achievementDto,
            UriComponentsBuilder uriBuilder) {

        Achievement entity = mapper.toEntity(achievementDto);
        Achievement saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/achievements/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AchievementDto>> createAllAchievements(
            @Valid @RequestBody List<AchievementDto> achievementDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Achievement> entities = mapper.toEntityList(achievementDtoList);
        List<Achievement> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/achievements").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AchievementDto> updateAchievement(
            @PathVariable Long id,
            @Valid @RequestBody AchievementDto achievementDto) {

        Achievement entityToUpdate = mapper.toEntity(achievementDto);
        Achievement updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Long id) {
        return doDelete(id);
    }
}