package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseOrderDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseOrderSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrder;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseOrderMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseOrderService;
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
 * Controller for managing MerchandiseOrder entities.
 */
@RestController
@RequestMapping("/api/merchandiseorders")
public class MerchandiseOrderController extends BaseController<MerchandiseOrder, MerchandiseOrderDto, MerchandiseOrderSimpleDto> {

    public MerchandiseOrderController(MerchandiseOrderService merchandiseorderService,
                                    MerchandiseOrderMapper merchandiseorderMapper) {
        super(merchandiseorderService, merchandiseorderMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MerchandiseOrderDto>> getAllMerchandiseOrders(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseOrderDto>> searchMerchandiseOrders(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MerchandiseOrder.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseOrderDto> getMerchandiseOrderById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MerchandiseOrderDto> createMerchandiseOrder(
            @Valid @RequestBody MerchandiseOrderDto merchandiseorderDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseOrder entity = mapper.toEntity(merchandiseorderDto);
        MerchandiseOrder saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/merchandiseorders/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseOrderDto>> createAllMerchandiseOrders(
            @Valid @RequestBody List<MerchandiseOrderDto> merchandiseorderDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseOrder> entities = mapper.toEntityList(merchandiseorderDtoList);
        List<MerchandiseOrder> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandiseorders").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseOrderDto> updateMerchandiseOrder(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseOrderDto merchandiseorderDto) {

        MerchandiseOrder entityToUpdate = mapper.toEntity(merchandiseorderDto);
        MerchandiseOrder updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseOrder(@PathVariable Long id) {
        return doDelete(id);
    }
}