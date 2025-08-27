package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.ShipmentDto;
import com.example.modules.ecommerce.dtosimple.ShipmentSimpleDto;
import com.example.modules.ecommerce.model.Shipment;
import com.example.modules.ecommerce.mapper.ShipmentMapper;
import com.example.modules.ecommerce.service.ShipmentService;
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
 * Controller for managing Shipment entities.
 */
@RestController
@RequestMapping("/api/shipments")
public class ShipmentController extends BaseController<Shipment, ShipmentDto, ShipmentSimpleDto> {

    public ShipmentController(ShipmentService shipmentService,
                                    ShipmentMapper shipmentMapper) {
        super(shipmentService, shipmentMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ShipmentDto>> getAllShipments(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ShipmentDto>> searchShipments(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Shipment.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentDto> getShipmentById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ShipmentDto> createShipment(
            @Valid @RequestBody ShipmentDto shipmentDto,
            UriComponentsBuilder uriBuilder) {

        Shipment entity = mapper.toEntity(shipmentDto);
        Shipment saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/shipments/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ShipmentDto>> createAllShipments(
            @Valid @RequestBody List<ShipmentDto> shipmentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Shipment> entities = mapper.toEntityList(shipmentDtoList);
        List<Shipment> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/shipments").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShipmentDto> updateShipment(
            @PathVariable Long id,
            @Valid @RequestBody ShipmentDto shipmentDto) {

        Shipment entityToUpdate = mapper.toEntity(shipmentDto);
        Shipment updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        return doDelete(id);
    }
}