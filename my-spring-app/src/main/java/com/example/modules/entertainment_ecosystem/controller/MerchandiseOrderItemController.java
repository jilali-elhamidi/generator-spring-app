package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseOrderItemDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseOrderItemSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrderItem;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseOrderItemMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseOrderItemService;
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
 * Controller for managing MerchandiseOrderItem entities.
 */
@RestController
@RequestMapping("/api/merchandiseorderitems")
public class MerchandiseOrderItemController extends BaseController<MerchandiseOrderItem, MerchandiseOrderItemDto, MerchandiseOrderItemSimpleDto> {

    public MerchandiseOrderItemController(MerchandiseOrderItemService merchandiseorderitemService,
                                    MerchandiseOrderItemMapper merchandiseorderitemMapper) {
        super(merchandiseorderitemService, merchandiseorderitemMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MerchandiseOrderItemDto>> getAllMerchandiseOrderItems(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseOrderItemDto>> searchMerchandiseOrderItems(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MerchandiseOrderItem.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseOrderItemDto> getMerchandiseOrderItemById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MerchandiseOrderItemDto> createMerchandiseOrderItem(
            @Valid @RequestBody MerchandiseOrderItemDto merchandiseorderitemDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseOrderItem entity = mapper.toEntity(merchandiseorderitemDto);
        MerchandiseOrderItem saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/merchandiseorderitems/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseOrderItemDto>> createAllMerchandiseOrderItems(
            @Valid @RequestBody List<MerchandiseOrderItemDto> merchandiseorderitemDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseOrderItem> entities = mapper.toEntityList(merchandiseorderitemDtoList);
        List<MerchandiseOrderItem> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandiseorderitems").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseOrderItemDto> updateMerchandiseOrderItem(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseOrderItemDto merchandiseorderitemDto) {

        MerchandiseOrderItem entityToUpdate = mapper.toEntity(merchandiseorderitemDto);
        MerchandiseOrderItem updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseOrderItem(@PathVariable Long id) {
        return doDelete(id);
    }
}