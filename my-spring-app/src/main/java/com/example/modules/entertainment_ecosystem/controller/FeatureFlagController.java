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
                @RequestBody FeatureFlagDto featureflagDto) {

                // Transformer le DTO en entity pour le service
                FeatureFlag entityToUpdate = featureflagMapper.toEntity(featureflagDto);

                // Appel du service update
                FeatureFlag updatedEntity = featureflagService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                FeatureFlagDto updatedDto = featureflagMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeatureFlag(@PathVariable Long id) {
        featureflagService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}