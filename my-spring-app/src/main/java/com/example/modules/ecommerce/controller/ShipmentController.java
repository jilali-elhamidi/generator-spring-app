package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.model.Shipment;
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

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        return ResponseEntity.ok(shipmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable Long id) {
        return shipmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Shipment> createShipment(
            @Valid @RequestBody Shipment shipment,
            UriComponentsBuilder uriBuilder) {

        Shipment saved = shipmentService.save(shipment);
        URI location = uriBuilder.path("/api/shipments/{id}")
                .buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipment> updateShipment(
            @PathVariable Long id,
            @Valid @RequestBody Shipment shipmentRequest) {

        try {
            Shipment updated = shipmentService.update(id, shipmentRequest);
            return ResponseEntity.ok(updated);
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
