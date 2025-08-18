package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.OrderDto;
import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.mapper.OrderMapper;
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
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService,
                                    OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<Order> entities = orderService.findAll();
        return ResponseEntity.ok(orderMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return orderService.findById(id)
                .map(orderMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(
            @Valid @RequestBody OrderDto orderDto,
            UriComponentsBuilder uriBuilder) {

        Order entity = orderMapper.toEntity(orderDto);
        Order saved = orderService.save(entity);
        URI location = uriBuilder.path("/api/orders/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(orderMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<OrderDto> updateOrder(
                @PathVariable Long id,
                @RequestBody OrderDto orderDto) {

                // Transformer le DTO en entity pour le service
                Order entityToUpdate = orderMapper.toEntity(orderDto);

                // Appel du service update
                Order updatedEntity = orderService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                OrderDto updatedDto = orderMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
                    boolean deleted = orderService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}