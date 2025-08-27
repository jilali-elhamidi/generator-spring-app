package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseSupplierDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSupplierSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseSupplier;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseSupplierMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseSupplierService;
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
 * Controller for managing MerchandiseSupplier entities.
 */
@RestController
@RequestMapping("/api/merchandisesuppliers")
public class MerchandiseSupplierController extends BaseController<MerchandiseSupplier, MerchandiseSupplierDto, MerchandiseSupplierSimpleDto> {

    public MerchandiseSupplierController(MerchandiseSupplierService merchandisesupplierService,
                                    MerchandiseSupplierMapper merchandisesupplierMapper) {
        super(merchandisesupplierService, merchandisesupplierMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MerchandiseSupplierDto>> getAllMerchandiseSuppliers(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseSupplierDto>> searchMerchandiseSuppliers(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MerchandiseSupplier.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseSupplierDto> getMerchandiseSupplierById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MerchandiseSupplierDto> createMerchandiseSupplier(
            @Valid @RequestBody MerchandiseSupplierDto merchandisesupplierDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseSupplier entity = mapper.toEntity(merchandisesupplierDto);
        MerchandiseSupplier saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/merchandisesuppliers/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseSupplierDto>> createAllMerchandiseSuppliers(
            @Valid @RequestBody List<MerchandiseSupplierDto> merchandisesupplierDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseSupplier> entities = mapper.toEntityList(merchandisesupplierDtoList);
        List<MerchandiseSupplier> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandisesuppliers").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseSupplierDto> updateMerchandiseSupplier(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseSupplierDto merchandisesupplierDto) {

        MerchandiseSupplier entityToUpdate = mapper.toEntity(merchandisesupplierDto);
        MerchandiseSupplier updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseSupplier(@PathVariable Long id) {
        return doDelete(id);
    }
}