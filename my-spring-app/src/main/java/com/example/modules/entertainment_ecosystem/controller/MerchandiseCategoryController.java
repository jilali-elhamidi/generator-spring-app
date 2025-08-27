package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseCategoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseCategorySimpleDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseCategory;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseCategoryMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseCategoryService;
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
 * Controller for managing MerchandiseCategory entities.
 */
@RestController
@RequestMapping("/api/merchandisecategorys")
public class MerchandiseCategoryController extends BaseController<MerchandiseCategory, MerchandiseCategoryDto, MerchandiseCategorySimpleDto> {

    public MerchandiseCategoryController(MerchandiseCategoryService merchandisecategoryService,
                                    MerchandiseCategoryMapper merchandisecategoryMapper) {
        super(merchandisecategoryService, merchandisecategoryMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MerchandiseCategoryDto>> getAllMerchandiseCategorys(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseCategoryDto>> searchMerchandiseCategorys(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MerchandiseCategory.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseCategoryDto> getMerchandiseCategoryById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MerchandiseCategoryDto> createMerchandiseCategory(
            @Valid @RequestBody MerchandiseCategoryDto merchandisecategoryDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseCategory entity = mapper.toEntity(merchandisecategoryDto);
        MerchandiseCategory saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/merchandisecategorys/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseCategoryDto>> createAllMerchandiseCategorys(
            @Valid @RequestBody List<MerchandiseCategoryDto> merchandisecategoryDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseCategory> entities = mapper.toEntityList(merchandisecategoryDtoList);
        List<MerchandiseCategory> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandisecategorys").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseCategoryDto> updateMerchandiseCategory(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseCategoryDto merchandisecategoryDto) {

        MerchandiseCategory entityToUpdate = mapper.toEntity(merchandisecategoryDto);
        MerchandiseCategory updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseCategory(@PathVariable Long id) {
        return doDelete(id);
    }
}