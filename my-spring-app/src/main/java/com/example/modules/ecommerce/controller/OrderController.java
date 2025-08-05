package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
public ResponseEntity<Order> createOrder(@RequestBody Order order) {
return ResponseEntity.ok(orderService.save(order));
}

@PutMapping("/{id}")
public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
return ResponseEntity.ok(orderService.save(order));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
