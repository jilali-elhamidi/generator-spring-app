package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.DigitalAssetDto;
import com.example.modules.entertainment_ecosystem.dtosimple.DigitalAssetSimpleDto;
import com.example.modules.entertainment_ecosystem.model.DigitalAsset;
import com.example.modules.entertainment_ecosystem.mapper.DigitalAssetMapper;
import com.example.modules.entertainment_ecosystem.service.DigitalAssetService;
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
 * Controller for managing DigitalAsset entities.
 */
@RestController
@RequestMapping("/api/digitalassets")
public class DigitalAssetController extends BaseController<DigitalAsset, DigitalAssetDto, DigitalAssetSimpleDto> {

    public DigitalAssetController(DigitalAssetService digitalassetService,
                                    DigitalAssetMapper digitalassetMapper) {
        super(digitalassetService, digitalassetMapper);
    }

    @GetMapping
    public ResponseEntity<Page<DigitalAssetDto>> getAllDigitalAssets(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DigitalAssetDto>> searchDigitalAssets(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(DigitalAsset.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DigitalAssetDto> getDigitalAssetById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<DigitalAssetDto> createDigitalAsset(
            @Valid @RequestBody DigitalAssetDto digitalassetDto,
            UriComponentsBuilder uriBuilder) {

        DigitalAsset entity = mapper.toEntity(digitalassetDto);
        DigitalAsset saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/digitalassets/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<DigitalAssetDto>> createAllDigitalAssets(
            @Valid @RequestBody List<DigitalAssetDto> digitalassetDtoList,
            UriComponentsBuilder uriBuilder) {

        List<DigitalAsset> entities = mapper.toEntityList(digitalassetDtoList);
        List<DigitalAsset> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/digitalassets").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DigitalAssetDto> updateDigitalAsset(
            @PathVariable Long id,
            @Valid @RequestBody DigitalAssetDto digitalassetDto) {

        DigitalAsset entityToUpdate = mapper.toEntity(digitalassetDto);
        DigitalAsset updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDigitalAsset(@PathVariable Long id) {
        return doDelete(id);
    }
}