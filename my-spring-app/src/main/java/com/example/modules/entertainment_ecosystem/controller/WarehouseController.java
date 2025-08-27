package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.WarehouseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.WarehouseSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Warehouse;
import com.example.modules.entertainment_ecosystem.mapper.WarehouseMapper;
import com.example.modules.entertainment_ecosystem.service.WarehouseService;
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
 * Controller for managing Warehouse entities.
 */
@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController extends BaseController<Warehouse, WarehouseDto, WarehouseSimpleDto> {

    public WarehouseController(WarehouseService warehouseService,
                                    WarehouseMapper warehouseMapper) {
        super(warehouseService, warehouseMapper);
    }

    @GetMapping
    public ResponseEntity<Page<WarehouseDto>> getAllWarehouses(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<WarehouseDto>> searchWarehouses(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Warehouse.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDto> getWarehouseById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<WarehouseDto> createWarehouse(
            @Valid @RequestBody WarehouseDto warehouseDto,
            UriComponentsBuilder uriBuilder) {

        Warehouse entity = mapper.toEntity(warehouseDto);
        Warehouse saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/warehouses/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<WarehouseDto>> createAllWarehouses(
            @Valid @RequestBody List<WarehouseDto> warehouseDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Warehouse> entities = mapper.toEntityList(warehouseDtoList);
        List<Warehouse> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/warehouses").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseDto> updateWarehouse(
            @PathVariable Long id,
            @Valid @RequestBody WarehouseDto warehouseDto) {

        Warehouse entityToUpdate = mapper.toEntity(warehouseDto);
        Warehouse updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        return doDelete(id);
    }
}