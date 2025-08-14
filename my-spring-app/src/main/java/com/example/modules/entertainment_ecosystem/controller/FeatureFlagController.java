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
        URI location = uriBuilder.path("/api/featureflags/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(featureflagMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<FeatureFlagDto> updateFeatureFlag(
                @PathVariable Long id,
                @Valid @RequestBody FeatureFlagDto featureflagDto) {

                try {
                // Récupérer l'entité existante avec Optional
                FeatureFlag existing = featureflagService.findById(id)
                .orElseThrow(() -> new RuntimeException("FeatureFlag not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                featureflagMapper.updateEntityFromDto(featureflagDto, existing);

                // Sauvegarde
                FeatureFlag updatedEntity = featureflagService.save(existing);

                return ResponseEntity.ok(featureflagMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeatureFlag(@PathVariable Long id) {
        featureflagService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}