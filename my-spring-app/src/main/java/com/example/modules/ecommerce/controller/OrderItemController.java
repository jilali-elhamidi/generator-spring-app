package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.OrderItemDto;
import com.example.modules.ecommerce.model.OrderItem;
import com.example.modules.ecommerce.mapper.OrderItemMapper;
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
private final OrderItemMapper orderitemMapper;

public OrderItemController(OrderItemService orderitemService, OrderItemMapper orderitemMapper) {
this.orderitemService = orderitemService;
this.orderitemMapper = orderitemMapper;
}

@GetMapping
public ResponseEntity<List<OrderItemDto>> getAllOrderItems() {
    List<OrderItem> entities = orderitemService.findAll();
    return ResponseEntity.ok(orderitemMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDto> getOrderItemById(@PathVariable Long id) {
        return orderitemService.findById(id)
        .map(orderitemMapper::toDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<OrderItemDto> createOrderItem(
            @Valid @RequestBody OrderItemDto orderitemDto,
            UriComponentsBuilder uriBuilder) {

            OrderItem entity = orderitemMapper.toEntity(orderitemDto);
            OrderItem saved = orderitemService.save(entity);
            URI location = uriBuilder.path("/api/orderitems/{id}")
            .buildAndExpand(saved.getId()).toUri();
            return ResponseEntity.created(location).body(orderitemMapper.toDto(saved));
            }

            @PutMapping("/{id}")
            public ResponseEntity<OrderItemDto> updateOrderItem(
                @PathVariable Long id,
                @Valid @RequestBody OrderItemDto orderitemDto) {

                try {
                OrderItem updatedEntity = orderitemService.update(id, orderitemMapper.toEntity(orderitemDto));
                return ResponseEntity.ok(orderitemMapper.toDto(updatedEntity));
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