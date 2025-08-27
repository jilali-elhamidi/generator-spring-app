package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.OrderItemDto;
import com.example.modules.ecommerce.dtosimple.OrderItemSimpleDto;
import com.example.modules.ecommerce.model.OrderItem;
import com.example.modules.ecommerce.mapper.OrderItemMapper;
import com.example.modules.ecommerce.service.OrderItemService;
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
 * Controller for managing OrderItem entities.
 */
@RestController
@RequestMapping("/api/orderitems")
public class OrderItemController extends BaseController<OrderItem, OrderItemDto, OrderItemSimpleDto> {

    public OrderItemController(OrderItemService orderitemService,
                                    OrderItemMapper orderitemMapper) {
        super(orderitemService, orderitemMapper);
    }

    @GetMapping
    public ResponseEntity<Page<OrderItemDto>> getAllOrderItems(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OrderItemDto>> searchOrderItems(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(OrderItem.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDto> getOrderItemById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<OrderItemDto> createOrderItem(
            @Valid @RequestBody OrderItemDto orderitemDto,
            UriComponentsBuilder uriBuilder) {

        OrderItem entity = mapper.toEntity(orderitemDto);
        OrderItem saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/orderitems/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<OrderItemDto>> createAllOrderItems(
            @Valid @RequestBody List<OrderItemDto> orderitemDtoList,
            UriComponentsBuilder uriBuilder) {

        List<OrderItem> entities = mapper.toEntityList(orderitemDtoList);
        List<OrderItem> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/orderitems").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDto> updateOrderItem(
            @PathVariable Long id,
            @Valid @RequestBody OrderItemDto orderitemDto) {

        OrderItem entityToUpdate = mapper.toEntity(orderitemDto);
        OrderItem updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        return doDelete(id);
    }
}