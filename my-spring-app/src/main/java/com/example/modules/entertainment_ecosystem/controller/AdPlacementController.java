package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.AdPlacementDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AdPlacementSimpleDto;
import com.example.modules.entertainment_ecosystem.model.AdPlacement;
import com.example.modules.entertainment_ecosystem.mapper.AdPlacementMapper;
import com.example.modules.entertainment_ecosystem.service.AdPlacementService;
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
 * Controller for managing AdPlacement entities.
 */
@RestController
@RequestMapping("/api/adplacements")
public class AdPlacementController extends BaseController<AdPlacement, AdPlacementDto, AdPlacementSimpleDto> {

    public AdPlacementController(AdPlacementService adplacementService,
                                    AdPlacementMapper adplacementMapper) {
        super(adplacementService, adplacementMapper);
    }

    @GetMapping
    public ResponseEntity<Page<AdPlacementDto>> getAllAdPlacements(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AdPlacementDto>> searchAdPlacements(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(AdPlacement.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdPlacementDto> getAdPlacementById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<AdPlacementDto> createAdPlacement(
            @Valid @RequestBody AdPlacementDto adplacementDto,
            UriComponentsBuilder uriBuilder) {

        AdPlacement entity = mapper.toEntity(adplacementDto);
        AdPlacement saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/adplacements/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AdPlacementDto>> createAllAdPlacements(
            @Valid @RequestBody List<AdPlacementDto> adplacementDtoList,
            UriComponentsBuilder uriBuilder) {

        List<AdPlacement> entities = mapper.toEntityList(adplacementDtoList);
        List<AdPlacement> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/adplacements").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdPlacementDto> updateAdPlacement(
            @PathVariable Long id,
            @Valid @RequestBody AdPlacementDto adplacementDto) {

        AdPlacement entityToUpdate = mapper.toEntity(adplacementDto);
        AdPlacement updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdPlacement(@PathVariable Long id) {
        return doDelete(id);
    }
}