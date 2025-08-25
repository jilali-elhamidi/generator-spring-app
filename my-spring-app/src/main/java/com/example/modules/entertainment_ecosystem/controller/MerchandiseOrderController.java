package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseOrderDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrder;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseOrderMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseOrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandiseorders")
public class MerchandiseOrderController {

    private final MerchandiseOrderService merchandiseorderService;
    private final MerchandiseOrderMapper merchandiseorderMapper;

    public MerchandiseOrderController(MerchandiseOrderService merchandiseorderService,
                                    MerchandiseOrderMapper merchandiseorderMapper) {
        this.merchandiseorderService = merchandiseorderService;
        this.merchandiseorderMapper = merchandiseorderMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseOrderDto>> getAllMerchandiseOrders() {
        List<MerchandiseOrder> entities = merchandiseorderService.findAll();
        return ResponseEntity.ok(merchandiseorderMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseOrderDto> getMerchandiseOrderById(@PathVariable Long id) {
        return merchandiseorderService.findById(id)
                .map(merchandiseorderMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseOrderDto> createMerchandiseOrder(
            @Valid @RequestBody MerchandiseOrderDto merchandiseorderDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseOrder entity = merchandiseorderMapper.toEntity(merchandiseorderDto);
        MerchandiseOrder saved = merchandiseorderService.save(entity);

        URI location = uriBuilder
                                .path("/api/merchandiseorders/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(merchandiseorderMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseOrderDto>> createAllMerchandiseOrders(
            @Valid @RequestBody List<MerchandiseOrderDto> merchandiseorderDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseOrder> entities = merchandiseorderMapper.toEntityList(merchandiseorderDtoList);
        List<MerchandiseOrder> savedEntities = merchandiseorderService.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandiseorders").build().toUri();

        return ResponseEntity.created(location).body(merchandiseorderMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseOrderDto> updateMerchandiseOrder(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseOrderDto merchandiseorderDto) {


        MerchandiseOrder entityToUpdate = merchandiseorderMapper.toEntity(merchandiseorderDto);
        MerchandiseOrder updatedEntity = merchandiseorderService.update(id, entityToUpdate);

        return ResponseEntity.ok(merchandiseorderMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseOrder(@PathVariable Long id) {
        boolean deleted = merchandiseorderService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}