package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.ShipmentDto;
import com.example.modules.ecommerce.model.Shipment;
import com.example.modules.ecommerce.mapper.ShipmentMapper;
import com.example.modules.ecommerce.service.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final ShipmentMapper shipmentMapper;

    public ShipmentController(ShipmentService shipmentService,
                                    ShipmentMapper shipmentMapper) {
        this.shipmentService = shipmentService;
        this.shipmentMapper = shipmentMapper;
    }

    @GetMapping
    public ResponseEntity<List<ShipmentDto>> getAllShipments() {
        List<Shipment> entities = shipmentService.findAll();
        return ResponseEntity.ok(shipmentMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentDto> getShipmentById(@PathVariable Long id) {
        return shipmentService.findById(id)
                .map(shipmentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShipmentDto> createShipment(
            @Valid @RequestBody ShipmentDto shipmentDto,
            UriComponentsBuilder uriBuilder) {

        Shipment entity = shipmentMapper.toEntity(shipmentDto);
        Shipment saved = shipmentService.save(entity);
        URI location = uriBuilder.path("/api/shipments/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(shipmentMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShipmentDto> updateShipment(
            @PathVariable Long id,
            @Valid @RequestBody ShipmentDto shipmentDto) {

        try {
            Shipment updatedEntity = shipmentService.update(
                    id,
                    shipmentMapper.toEntity(shipmentDto)
            );
            return ResponseEntity.ok(shipmentMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        shipmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}