package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseInventoryDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseInventory;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseInventoryMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseInventoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandiseinventorys")
public class MerchandiseInventoryController {

    private final MerchandiseInventoryService merchandiseinventoryService;
    private final MerchandiseInventoryMapper merchandiseinventoryMapper;

    public MerchandiseInventoryController(MerchandiseInventoryService merchandiseinventoryService,
                                    MerchandiseInventoryMapper merchandiseinventoryMapper) {
        this.merchandiseinventoryService = merchandiseinventoryService;
        this.merchandiseinventoryMapper = merchandiseinventoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseInventoryDto>> getAllMerchandiseInventorys() {
        List<MerchandiseInventory> entities = merchandiseinventoryService.findAll();
        return ResponseEntity.ok(merchandiseinventoryMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseInventoryDto> getMerchandiseInventoryById(@PathVariable Long id) {
        return merchandiseinventoryService.findById(id)
                .map(merchandiseinventoryMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseInventoryDto> createMerchandiseInventory(
            @Valid @RequestBody MerchandiseInventoryDto merchandiseinventoryDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseInventory entity = merchandiseinventoryMapper.toEntity(merchandiseinventoryDto);
        MerchandiseInventory saved = merchandiseinventoryService.save(entity);
        URI location = uriBuilder.path("/api/merchandiseinventorys/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(merchandiseinventoryMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MerchandiseInventoryDto> updateMerchandiseInventory(
                @PathVariable Long id,
                @RequestBody MerchandiseInventoryDto merchandiseinventoryDto) {

                // Transformer le DTO en entity pour le service
                MerchandiseInventory entityToUpdate = merchandiseinventoryMapper.toEntity(merchandiseinventoryDto);

                // Appel du service update
                MerchandiseInventory updatedEntity = merchandiseinventoryService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MerchandiseInventoryDto updatedDto = merchandiseinventoryMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseInventory(@PathVariable Long id) {
        merchandiseinventoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}