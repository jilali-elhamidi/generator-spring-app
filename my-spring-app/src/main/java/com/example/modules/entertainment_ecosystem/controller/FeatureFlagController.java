package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.FeatureFlagDto;
import com.example.modules.entertainment_ecosystem.dtosimple.FeatureFlagSimpleDto;
import com.example.modules.entertainment_ecosystem.model.FeatureFlag;
import com.example.modules.entertainment_ecosystem.mapper.FeatureFlagMapper;
import com.example.modules.entertainment_ecosystem.service.FeatureFlagService;
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
 * Controller for managing FeatureFlag entities.
 */
@RestController
@RequestMapping("/api/featureflags")
public class FeatureFlagController extends BaseController<FeatureFlag, FeatureFlagDto, FeatureFlagSimpleDto> {

    public FeatureFlagController(FeatureFlagService featureflagService,
                                    FeatureFlagMapper featureflagMapper) {
        super(featureflagService, featureflagMapper);
    }

    @GetMapping
    public ResponseEntity<Page<FeatureFlagDto>> getAllFeatureFlags(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<FeatureFlagDto>> searchFeatureFlags(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(FeatureFlag.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeatureFlagDto> getFeatureFlagById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<FeatureFlagDto> createFeatureFlag(
            @Valid @RequestBody FeatureFlagDto featureflagDto,
            UriComponentsBuilder uriBuilder) {

        FeatureFlag entity = mapper.toEntity(featureflagDto);
        FeatureFlag saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/featureflags/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<FeatureFlagDto>> createAllFeatureFlags(
            @Valid @RequestBody List<FeatureFlagDto> featureflagDtoList,
            UriComponentsBuilder uriBuilder) {

        List<FeatureFlag> entities = mapper.toEntityList(featureflagDtoList);
        List<FeatureFlag> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/featureflags").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeatureFlagDto> updateFeatureFlag(
            @PathVariable Long id,
            @Valid @RequestBody FeatureFlagDto featureflagDto) {

        FeatureFlag entityToUpdate = mapper.toEntity(featureflagDto);
        FeatureFlag updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeatureFlag(@PathVariable Long id) {
        return doDelete(id);
    }
}