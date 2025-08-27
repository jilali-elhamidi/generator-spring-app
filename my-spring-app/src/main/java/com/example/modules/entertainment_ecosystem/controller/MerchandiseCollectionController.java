package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseCollectionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseCollectionSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseCollection;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseCollectionMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseCollectionService;
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
 * Controller for managing MerchandiseCollection entities.
 */
@RestController
@RequestMapping("/api/merchandisecollections")
public class MerchandiseCollectionController extends BaseController<MerchandiseCollection, MerchandiseCollectionDto, MerchandiseCollectionSimpleDto> {

    public MerchandiseCollectionController(MerchandiseCollectionService merchandisecollectionService,
                                    MerchandiseCollectionMapper merchandisecollectionMapper) {
        super(merchandisecollectionService, merchandisecollectionMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MerchandiseCollectionDto>> getAllMerchandiseCollections(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseCollectionDto>> searchMerchandiseCollections(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MerchandiseCollection.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseCollectionDto> getMerchandiseCollectionById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MerchandiseCollectionDto> createMerchandiseCollection(
            @Valid @RequestBody MerchandiseCollectionDto merchandisecollectionDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseCollection entity = mapper.toEntity(merchandisecollectionDto);
        MerchandiseCollection saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/merchandisecollections/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseCollectionDto>> createAllMerchandiseCollections(
            @Valid @RequestBody List<MerchandiseCollectionDto> merchandisecollectionDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseCollection> entities = mapper.toEntityList(merchandisecollectionDtoList);
        List<MerchandiseCollection> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandisecollections").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseCollectionDto> updateMerchandiseCollection(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseCollectionDto merchandisecollectionDto) {

        MerchandiseCollection entityToUpdate = mapper.toEntity(merchandisecollectionDto);
        MerchandiseCollection updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseCollection(@PathVariable Long id) {
        return doDelete(id);
    }
}