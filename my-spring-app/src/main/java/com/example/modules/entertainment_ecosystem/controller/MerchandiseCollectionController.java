package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseCollectionDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseCollection;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseCollectionMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseCollectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandisecollections")
public class MerchandiseCollectionController {

    private final MerchandiseCollectionService merchandisecollectionService;
    private final MerchandiseCollectionMapper merchandisecollectionMapper;

    public MerchandiseCollectionController(MerchandiseCollectionService merchandisecollectionService,
                                    MerchandiseCollectionMapper merchandisecollectionMapper) {
        this.merchandisecollectionService = merchandisecollectionService;
        this.merchandisecollectionMapper = merchandisecollectionMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseCollectionDto>> getAllMerchandiseCollections() {
        List<MerchandiseCollection> entities = merchandisecollectionService.findAll();
        return ResponseEntity.ok(merchandisecollectionMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseCollectionDto> getMerchandiseCollectionById(@PathVariable Long id) {
        return merchandisecollectionService.findById(id)
                .map(merchandisecollectionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseCollectionDto> createMerchandiseCollection(
            @Valid @RequestBody MerchandiseCollectionDto merchandisecollectionDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseCollection entity = merchandisecollectionMapper.toEntity(merchandisecollectionDto);
        MerchandiseCollection saved = merchandisecollectionService.save(entity);

        URI location = uriBuilder
                                .path("/api/merchandisecollections/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(merchandisecollectionMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseCollectionDto>> createAllMerchandiseCollections(
            @Valid @RequestBody List<MerchandiseCollectionDto> merchandisecollectionDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseCollection> entities = merchandisecollectionMapper.toEntityList(merchandisecollectionDtoList);
        List<MerchandiseCollection> savedEntities = merchandisecollectionService.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandisecollections").build().toUri();

        return ResponseEntity.created(location).body(merchandisecollectionMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseCollectionDto> updateMerchandiseCollection(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseCollectionDto merchandisecollectionDto) {


        MerchandiseCollection entityToUpdate = merchandisecollectionMapper.toEntity(merchandisecollectionDto);
        MerchandiseCollection updatedEntity = merchandisecollectionService.update(id, entityToUpdate);

        return ResponseEntity.ok(merchandisecollectionMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseCollection(@PathVariable Long id) {
        boolean deleted = merchandisecollectionService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}