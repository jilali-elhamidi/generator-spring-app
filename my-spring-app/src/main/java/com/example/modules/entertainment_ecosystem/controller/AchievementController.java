package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.AchievementDto;
import com.example.modules.entertainment_ecosystem.model.Achievement;
import com.example.modules.entertainment_ecosystem.mapper.AchievementMapper;
import com.example.modules.entertainment_ecosystem.service.AchievementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    private final AchievementService achievementService;
    private final AchievementMapper achievementMapper;

    public AchievementController(AchievementService achievementService,
                                    AchievementMapper achievementMapper) {
        this.achievementService = achievementService;
        this.achievementMapper = achievementMapper;
    }

    @GetMapping
    public ResponseEntity<List<AchievementDto>> getAllAchievements() {
        List<Achievement> entities = achievementService.findAll();
        return ResponseEntity.ok(achievementMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchievementDto> getAchievementById(@PathVariable Long id) {
        return achievementService.findById(id)
                .map(achievementMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AchievementDto> createAchievement(
            @Valid @RequestBody AchievementDto achievementDto,
            UriComponentsBuilder uriBuilder) {

        Achievement entity = achievementMapper.toEntity(achievementDto);
        Achievement saved = achievementService.save(entity);

        URI location = uriBuilder
                                .path("/api/achievements/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(achievementMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AchievementDto>> createAllAchievements(
            @Valid @RequestBody List<AchievementDto> achievementDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Achievement> entities = achievementMapper.toEntityList(achievementDtoList);
        List<Achievement> savedEntities = achievementService.saveAll(entities);

        URI location = uriBuilder.path("/api/achievements").build().toUri();

        return ResponseEntity.created(location).body(achievementMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AchievementDto> updateAchievement(
            @PathVariable Long id,
            @Valid @RequestBody AchievementDto achievementDto) {


        Achievement entityToUpdate = achievementMapper.toEntity(achievementDto);
        Achievement updatedEntity = achievementService.update(id, entityToUpdate);

        return ResponseEntity.ok(achievementMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Long id) {
        boolean deleted = achievementService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}