package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.OrderDto;
import com.example.modules.ecommerce.dtosimple.OrderSimpleDto;
import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.mapper.OrderMapper;
import com.example.modules.ecommerce.service.OrderService;
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
 * Controller for managing Order entities.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController extends BaseController<Order, OrderDto, OrderSimpleDto> {

    public OrderController(OrderService orderService,
                                    OrderMapper orderMapper) {
        super(orderService, orderMapper);
    }

    @GetMapping
    public ResponseEntity<Page<OrderDto>> getAllOrders(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OrderDto>> searchOrders(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Order.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(
            @Valid @RequestBody OrderDto orderDto,
            UriComponentsBuilder uriBuilder) {

        Order entity = mapper.toEntity(orderDto);
        Order saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/orders/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<OrderDto>> createAllOrders(
            @Valid @RequestBody List<OrderDto> orderDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Order> entities = mapper.toEntityList(orderDtoList);
        List<Order> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/orders").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderDto orderDto) {

        Order entityToUpdate = mapper.toEntity(orderDto);
        Order updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        return doDelete(id);
    }
}