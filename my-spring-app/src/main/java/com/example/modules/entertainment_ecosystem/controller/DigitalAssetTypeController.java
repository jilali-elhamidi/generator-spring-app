package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.DigitalAssetTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.DigitalAssetTypeSimpleDto;
import com.example.modules.entertainment_ecosystem.model.DigitalAssetType;
import com.example.modules.entertainment_ecosystem.mapper.DigitalAssetTypeMapper;
import com.example.modules.entertainment_ecosystem.service.DigitalAssetTypeService;
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
 * Controller for managing DigitalAssetType entities.
 */
@RestController
@RequestMapping("/api/digitalassettypes")
public class DigitalAssetTypeController extends BaseController<DigitalAssetType, DigitalAssetTypeDto, DigitalAssetTypeSimpleDto> {

    public DigitalAssetTypeController(DigitalAssetTypeService digitalassettypeService,
                                    DigitalAssetTypeMapper digitalassettypeMapper) {
        super(digitalassettypeService, digitalassettypeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<DigitalAssetTypeDto>> getAllDigitalAssetTypes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DigitalAssetTypeDto>> searchDigitalAssetTypes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(DigitalAssetType.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DigitalAssetTypeDto> getDigitalAssetTypeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<DigitalAssetTypeDto> createDigitalAssetType(
            @Valid @RequestBody DigitalAssetTypeDto digitalassettypeDto,
            UriComponentsBuilder uriBuilder) {

        DigitalAssetType entity = mapper.toEntity(digitalassettypeDto);
        DigitalAssetType saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/digitalassettypes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<DigitalAssetTypeDto>> createAllDigitalAssetTypes(
            @Valid @RequestBody List<DigitalAssetTypeDto> digitalassettypeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<DigitalAssetType> entities = mapper.toEntityList(digitalassettypeDtoList);
        List<DigitalAssetType> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/digitalassettypes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DigitalAssetTypeDto> updateDigitalAssetType(
            @PathVariable Long id,
            @Valid @RequestBody DigitalAssetTypeDto digitalassettypeDto) {

        DigitalAssetType entityToUpdate = mapper.toEntity(digitalassettypeDto);
        DigitalAssetType updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDigitalAssetType(@PathVariable Long id) {
        return doDelete(id);
    }
}