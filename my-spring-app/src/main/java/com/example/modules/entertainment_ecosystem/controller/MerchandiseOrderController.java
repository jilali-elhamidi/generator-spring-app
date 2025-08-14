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
        URI location = uriBuilder.path("/api/merchandiseorders/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(merchandiseorderMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MerchandiseOrderDto> updateMerchandiseOrder(
                @PathVariable Long id,
                @Valid @RequestBody MerchandiseOrderDto merchandiseorderDto) {

                try {
                // Récupérer l'entité existante avec Optional
                MerchandiseOrder existing = merchandiseorderService.findById(id)
                .orElseThrow(() -> new RuntimeException("MerchandiseOrder not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                merchandiseorderMapper.updateEntityFromDto(merchandiseorderDto, existing);

                // Sauvegarde
                MerchandiseOrder updatedEntity = merchandiseorderService.save(existing);

                return ResponseEntity.ok(merchandiseorderMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseOrder(@PathVariable Long id) {
        merchandiseorderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}