package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.model.OrderItem;
import com.example.modules.ecommerce.service.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderitem) {
return ResponseEntity.ok(orderitemService.save(orderitem));
}

@PutMapping("/{id}")
public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderitem) {
return ResponseEntity.ok(orderitemService.save(orderitem));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
    orderitemService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
