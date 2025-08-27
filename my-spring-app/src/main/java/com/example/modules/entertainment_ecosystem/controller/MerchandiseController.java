package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseService;
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
 * Controller for managing Merchandise entities.
 */
@RestController
@RequestMapping("/api/merchandises")
public class MerchandiseController extends BaseController<Merchandise, MerchandiseDto, MerchandiseSimpleDto> {

    public MerchandiseController(MerchandiseService merchandiseService,
                                    MerchandiseMapper merchandiseMapper) {
        super(merchandiseService, merchandiseMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MerchandiseDto>> getAllMerchandises(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseDto>> searchMerchandises(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Merchandise.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseDto> getMerchandiseById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MerchandiseDto> createMerchandise(
            @Valid @RequestBody MerchandiseDto merchandiseDto,
            UriComponentsBuilder uriBuilder) {

        Merchandise entity = mapper.toEntity(merchandiseDto);
        Merchandise saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/merchandises/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseDto>> createAllMerchandises(
            @Valid @RequestBody List<MerchandiseDto> merchandiseDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Merchandise> entities = mapper.toEntityList(merchandiseDtoList);
        List<Merchandise> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandises").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseDto> updateMerchandise(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseDto merchandiseDto) {

        Merchandise entityToUpdate = mapper.toEntity(merchandiseDto);
        Merchandise updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandise(@PathVariable Long id) {
        return doDelete(id);
    }
}