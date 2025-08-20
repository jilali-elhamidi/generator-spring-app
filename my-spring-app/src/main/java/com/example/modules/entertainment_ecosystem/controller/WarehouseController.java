package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.WarehouseDto;
import com.example.modules.entertainment_ecosystem.model.Warehouse;
import com.example.modules.entertainment_ecosystem.mapper.WarehouseMapper;
import com.example.modules.entertainment_ecosystem.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final WarehouseMapper warehouseMapper;

    public WarehouseController(WarehouseService warehouseService,
                                    WarehouseMapper warehouseMapper) {
        this.warehouseService = warehouseService;
        this.warehouseMapper = warehouseMapper;
    }

    @GetMapping
    public ResponseEntity<List<WarehouseDto>> getAllWarehouses() {
        List<Warehouse> entities = warehouseService.findAll();
        return ResponseEntity.ok(warehouseMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDto> getWarehouseById(@PathVariable Long id) {
        return warehouseService.findById(id)
                .map(warehouseMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<WarehouseDto> createWarehouse(
            @Valid @RequestBody WarehouseDto warehouseDto,
            UriComponentsBuilder uriBuilder) {

        Warehouse entity = warehouseMapper.toEntity(warehouseDto);
        Warehouse saved = warehouseService.save(entity);

        URI location = uriBuilder
                                .path("/api/warehouses/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(warehouseMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseDto> updateWarehouse(
            @PathVariable Long id,
            @Valid @RequestBody WarehouseDto warehouseDto) {


        Warehouse entityToUpdate = warehouseMapper.toEntity(warehouseDto);
        Warehouse updatedEntity = warehouseService.update(id, entityToUpdate);

        return ResponseEntity.ok(warehouseMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        boolean deleted = warehouseService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}