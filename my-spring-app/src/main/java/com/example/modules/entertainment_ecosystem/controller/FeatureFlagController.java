package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.FeatureFlagDto;
import com.example.modules.entertainment_ecosystem.model.FeatureFlag;
import com.example.modules.entertainment_ecosystem.mapper.FeatureFlagMapper;
import com.example.modules.entertainment_ecosystem.service.FeatureFlagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/featureflags")
public class FeatureFlagController {

    private final FeatureFlagService featureflagService;
    private final FeatureFlagMapper featureflagMapper;

    public FeatureFlagController(FeatureFlagService featureflagService,
                                    FeatureFlagMapper featureflagMapper) {
        this.featureflagService = featureflagService;
        this.featureflagMapper = featureflagMapper;
    }

    @GetMapping
    public ResponseEntity<List<FeatureFlagDto>> getAllFeatureFlags() {
        List<FeatureFlag> entities = featureflagService.findAll();
        return ResponseEntity.ok(featureflagMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeatureFlagDto> getFeatureFlagById(@PathVariable Long id) {
        return featureflagService.findById(id)
                .map(featureflagMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FeatureFlagDto> createFeatureFlag(
            @Valid @RequestBody FeatureFlagDto featureflagDto,
            UriComponentsBuilder uriBuilder) {

        FeatureFlag entity = featureflagMapper.toEntity(featureflagDto);
        FeatureFlag saved = featureflagService.save(entity);

        URI location = uriBuilder
                                .path("/api/featureflags/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(featureflagMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<FeatureFlagDto>> createAllFeatureFlags(
            @Valid @RequestBody List<FeatureFlagDto> featureflagDtoList,
            UriComponentsBuilder uriBuilder) {

        List<FeatureFlag> entities = featureflagMapper.toEntityList(featureflagDtoList);
        List<FeatureFlag> savedEntities = featureflagService.saveAll(entities);

        URI location = uriBuilder.path("/api/featureflags").build().toUri();

        return ResponseEntity.created(location).body(featureflagMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeatureFlagDto> updateFeatureFlag(
            @PathVariable Long id,
            @Valid @RequestBody FeatureFlagDto featureflagDto) {


        FeatureFlag entityToUpdate = featureflagMapper.toEntity(featureflagDto);
        FeatureFlag updatedEntity = featureflagService.update(id, entityToUpdate);

        return ResponseEntity.ok(featureflagMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeatureFlag(@PathVariable Long id) {
        boolean deleted = featureflagService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}