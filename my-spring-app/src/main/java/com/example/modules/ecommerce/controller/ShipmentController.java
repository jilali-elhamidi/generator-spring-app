package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.model.Shipment;
import com.example.modules.ecommerce.service.ShipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment) {
return ResponseEntity.ok(shipmentService.save(shipment));
}

@PutMapping("/{id}")
public ResponseEntity<Shipment> updateShipment(@PathVariable Long id, @RequestBody Shipment shipment) {
return ResponseEntity.ok(shipmentService.save(shipment));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
    shipmentService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
