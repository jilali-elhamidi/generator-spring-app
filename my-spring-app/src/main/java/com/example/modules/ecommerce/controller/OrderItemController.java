package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.model.OrderItem;
import com.example.modules.ecommerce.service.OrderItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orderitems")
public class OrderItemController {

private final OrderItemService orderitemService;

public OrderItemController(OrderItemService orderitemService) {
this.orderitemService = orderitemService;
}

@GetMapping
public ResponseEntity<List<OrderItem>> getAllOrderItems() {
return ResponseEntity.ok(orderitemService.findAll());
}

@GetMapping("/{id}")
public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
return orderitemService.findById(id)
.map(ResponseEntity::ok)
.orElse(ResponseEntity.notFound().build());
}

@PostMapping
public ResponseEntity<OrderItem> createOrderItem(
@Valid @RequestBody OrderItem orderitem,
UriComponentsBuilder uriBuilder) {

OrderItem saved = orderitemService.save(orderitem);
URI location = uriBuilder.path("/api/orderitems/{id}")
.buildAndExpand(saved.getId()).toUri();

return ResponseEntity.created(location).body(saved);
}

@PutMapping("/{id}")
public ResponseEntity<OrderItem> updateOrderItem(
@PathVariable Long id,
@Valid @RequestBody OrderItem orderitemRequest) {

try {
OrderItem updated = orderitemService.update(id, orderitemRequest);
return ResponseEntity.ok(updated);
} catch (RuntimeException e) {
return ResponseEntity.notFound().build();
}
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
    orderitemService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
