package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseOrderItemDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrderItem;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseOrderItemMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseOrderItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandiseorderitems")
public class MerchandiseOrderItemController {

    private final MerchandiseOrderItemService merchandiseorderitemService;
    private final MerchandiseOrderItemMapper merchandiseorderitemMapper;

    public MerchandiseOrderItemController(MerchandiseOrderItemService merchandiseorderitemService,
                                    MerchandiseOrderItemMapper merchandiseorderitemMapper) {
        this.merchandiseorderitemService = merchandiseorderitemService;
        this.merchandiseorderitemMapper = merchandiseorderitemMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseOrderItemDto>> getAllMerchandiseOrderItems() {
        List<MerchandiseOrderItem> entities = merchandiseorderitemService.findAll();
        return ResponseEntity.ok(merchandiseorderitemMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseOrderItemDto> getMerchandiseOrderItemById(@PathVariable Long id) {
        return merchandiseorderitemService.findById(id)
                .map(merchandiseorderitemMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseOrderItemDto> createMerchandiseOrderItem(
            @Valid @RequestBody MerchandiseOrderItemDto merchandiseorderitemDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseOrderItem entity = merchandiseorderitemMapper.toEntity(merchandiseorderitemDto);
        MerchandiseOrderItem saved = merchandiseorderitemService.save(entity);
        URI location = uriBuilder.path("/api/merchandiseorderitems/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(merchandiseorderitemMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MerchandiseOrderItemDto> updateMerchandiseOrderItem(
                @PathVariable Long id,
                @RequestBody MerchandiseOrderItemDto merchandiseorderitemDto) {

                // Transformer le DTO en entity pour le service
                MerchandiseOrderItem entityToUpdate = merchandiseorderitemMapper.toEntity(merchandiseorderitemDto);

                // Appel du service update
                MerchandiseOrderItem updatedEntity = merchandiseorderitemService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MerchandiseOrderItemDto updatedDto = merchandiseorderitemMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseOrderItem(@PathVariable Long id) {
        merchandiseorderitemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}