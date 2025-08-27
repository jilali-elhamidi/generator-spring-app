package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseSaleDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSaleSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseSale;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseSaleMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseSaleService;
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
 * Controller for managing MerchandiseSale entities.
 */
@RestController
@RequestMapping("/api/merchandisesales")
public class MerchandiseSaleController extends BaseController<MerchandiseSale, MerchandiseSaleDto, MerchandiseSaleSimpleDto> {

    public MerchandiseSaleController(MerchandiseSaleService merchandisesaleService,
                                    MerchandiseSaleMapper merchandisesaleMapper) {
        super(merchandisesaleService, merchandisesaleMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MerchandiseSaleDto>> getAllMerchandiseSales(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseSaleDto>> searchMerchandiseSales(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MerchandiseSale.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseSaleDto> getMerchandiseSaleById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MerchandiseSaleDto> createMerchandiseSale(
            @Valid @RequestBody MerchandiseSaleDto merchandisesaleDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseSale entity = mapper.toEntity(merchandisesaleDto);
        MerchandiseSale saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/merchandisesales/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseSaleDto>> createAllMerchandiseSales(
            @Valid @RequestBody List<MerchandiseSaleDto> merchandisesaleDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseSale> entities = mapper.toEntityList(merchandisesaleDtoList);
        List<MerchandiseSale> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandisesales").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseSaleDto> updateMerchandiseSale(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseSaleDto merchandisesaleDto) {

        MerchandiseSale entityToUpdate = mapper.toEntity(merchandisesaleDto);
        MerchandiseSale updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseSale(@PathVariable Long id) {
        return doDelete(id);
    }
}