package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.DigitalPurchaseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.DigitalPurchaseSimpleDto;
import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.mapper.DigitalPurchaseMapper;
import com.example.modules.entertainment_ecosystem.service.DigitalPurchaseService;
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
 * Controller for managing DigitalPurchase entities.
 */
@RestController
@RequestMapping("/api/digitalpurchases")
public class DigitalPurchaseController extends BaseController<DigitalPurchase, DigitalPurchaseDto, DigitalPurchaseSimpleDto> {

    public DigitalPurchaseController(DigitalPurchaseService digitalpurchaseService,
                                    DigitalPurchaseMapper digitalpurchaseMapper) {
        super(digitalpurchaseService, digitalpurchaseMapper);
    }

    @GetMapping
    public ResponseEntity<Page<DigitalPurchaseDto>> getAllDigitalPurchases(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DigitalPurchaseDto>> searchDigitalPurchases(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(DigitalPurchase.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DigitalPurchaseDto> getDigitalPurchaseById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<DigitalPurchaseDto> createDigitalPurchase(
            @Valid @RequestBody DigitalPurchaseDto digitalpurchaseDto,
            UriComponentsBuilder uriBuilder) {

        DigitalPurchase entity = mapper.toEntity(digitalpurchaseDto);
        DigitalPurchase saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/digitalpurchases/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<DigitalPurchaseDto>> createAllDigitalPurchases(
            @Valid @RequestBody List<DigitalPurchaseDto> digitalpurchaseDtoList,
            UriComponentsBuilder uriBuilder) {

        List<DigitalPurchase> entities = mapper.toEntityList(digitalpurchaseDtoList);
        List<DigitalPurchase> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/digitalpurchases").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DigitalPurchaseDto> updateDigitalPurchase(
            @PathVariable Long id,
            @Valid @RequestBody DigitalPurchaseDto digitalpurchaseDto) {

        DigitalPurchase entityToUpdate = mapper.toEntity(digitalpurchaseDto);
        DigitalPurchase updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDigitalPurchase(@PathVariable Long id) {
        return doDelete(id);
    }
}