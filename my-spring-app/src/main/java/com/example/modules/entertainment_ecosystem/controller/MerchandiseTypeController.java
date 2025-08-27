package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseTypeSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseType;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseTypeMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseTypeService;
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
 * Controller for managing MerchandiseType entities.
 */
@RestController
@RequestMapping("/api/merchandisetypes")
public class MerchandiseTypeController extends BaseController<MerchandiseType, MerchandiseTypeDto, MerchandiseTypeSimpleDto> {

    public MerchandiseTypeController(MerchandiseTypeService merchandisetypeService,
                                    MerchandiseTypeMapper merchandisetypeMapper) {
        super(merchandisetypeService, merchandisetypeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MerchandiseTypeDto>> getAllMerchandiseTypes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseTypeDto>> searchMerchandiseTypes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MerchandiseType.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseTypeDto> getMerchandiseTypeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MerchandiseTypeDto> createMerchandiseType(
            @Valid @RequestBody MerchandiseTypeDto merchandisetypeDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseType entity = mapper.toEntity(merchandisetypeDto);
        MerchandiseType saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/merchandisetypes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseTypeDto>> createAllMerchandiseTypes(
            @Valid @RequestBody List<MerchandiseTypeDto> merchandisetypeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseType> entities = mapper.toEntityList(merchandisetypeDtoList);
        List<MerchandiseType> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandisetypes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseTypeDto> updateMerchandiseType(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseTypeDto merchandisetypeDto) {

        MerchandiseType entityToUpdate = mapper.toEntity(merchandisetypeDto);
        MerchandiseType updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseType(@PathVariable Long id) {
        return doDelete(id);
    }
}