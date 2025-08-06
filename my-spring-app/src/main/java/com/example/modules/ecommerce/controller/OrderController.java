package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

private final OrderService orderService;

public OrderController(OrderService orderService) {
this.orderService = orderService;
}

@GetMapping
public ResponseEntity<List<Order>> getAllOrders() {
return ResponseEntity.ok(orderService.findAll());
}

@GetMapping("/{id}")
public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
return orderService.findById(id)
.map(ResponseEntity::ok)
.orElse(ResponseEntity.notFound().build());
}

@PostMapping
public ResponseEntity<Order> createOrder(
@Valid @RequestBody Order order,
UriComponentsBuilder uriBuilder) {

Order saved = orderService.save(order);
URI location = uriBuilder.path("/api/orders/{id}")
.buildAndExpand(saved.getId()).toUri();

return ResponseEntity.created(location).body(saved);
}

@PutMapping("/{id}")
public ResponseEntity<Order> updateOrder(
@PathVariable Long id,
@Valid @RequestBody Order orderRequest) {

try {
Order updated = orderService.update(id, orderRequest);
return ResponseEntity.ok(updated);
} catch (RuntimeException e) {
return ResponseEntity.notFound().build();
}
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
