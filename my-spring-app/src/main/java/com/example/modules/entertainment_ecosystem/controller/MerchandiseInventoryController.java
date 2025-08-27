package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseInventoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseInventorySimpleDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseInventory;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseInventoryMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseInventoryService;
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
 * Controller for managing MerchandiseInventory entities.
 */
@RestController
@RequestMapping("/api/merchandiseinventorys")
public class MerchandiseInventoryController extends BaseController<MerchandiseInventory, MerchandiseInventoryDto, MerchandiseInventorySimpleDto> {

    public MerchandiseInventoryController(MerchandiseInventoryService merchandiseinventoryService,
                                    MerchandiseInventoryMapper merchandiseinventoryMapper) {
        super(merchandiseinventoryService, merchandiseinventoryMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MerchandiseInventoryDto>> getAllMerchandiseInventorys(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseInventoryDto>> searchMerchandiseInventorys(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MerchandiseInventory.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseInventoryDto> getMerchandiseInventoryById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MerchandiseInventoryDto> createMerchandiseInventory(
            @Valid @RequestBody MerchandiseInventoryDto merchandiseinventoryDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseInventory entity = mapper.toEntity(merchandiseinventoryDto);
        MerchandiseInventory saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/merchandiseinventorys/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseInventoryDto>> createAllMerchandiseInventorys(
            @Valid @RequestBody List<MerchandiseInventoryDto> merchandiseinventoryDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseInventory> entities = mapper.toEntityList(merchandiseinventoryDtoList);
        List<MerchandiseInventory> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandiseinventorys").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseInventoryDto> updateMerchandiseInventory(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseInventoryDto merchandiseinventoryDto) {

        MerchandiseInventory entityToUpdate = mapper.toEntity(merchandiseinventoryDto);
        MerchandiseInventory updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseInventory(@PathVariable Long id) {
        return doDelete(id);
    }
}