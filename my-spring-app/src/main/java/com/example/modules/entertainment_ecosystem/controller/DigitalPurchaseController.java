package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.DigitalPurchaseDto;
import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.mapper.DigitalPurchaseMapper;
import com.example.modules.entertainment_ecosystem.service.DigitalPurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/digitalpurchases")
public class DigitalPurchaseController {

    private final DigitalPurchaseService digitalpurchaseService;
    private final DigitalPurchaseMapper digitalpurchaseMapper;

    public DigitalPurchaseController(DigitalPurchaseService digitalpurchaseService,
                                    DigitalPurchaseMapper digitalpurchaseMapper) {
        this.digitalpurchaseService = digitalpurchaseService;
        this.digitalpurchaseMapper = digitalpurchaseMapper;
    }

    @GetMapping
    public ResponseEntity<List<DigitalPurchaseDto>> getAllDigitalPurchases() {
        List<DigitalPurchase> entities = digitalpurchaseService.findAll();
        return ResponseEntity.ok(digitalpurchaseMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DigitalPurchaseDto> getDigitalPurchaseById(@PathVariable Long id) {
        return digitalpurchaseService.findById(id)
                .map(digitalpurchaseMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DigitalPurchaseDto> createDigitalPurchase(
            @Valid @RequestBody DigitalPurchaseDto digitalpurchaseDto,
            UriComponentsBuilder uriBuilder) {

        DigitalPurchase entity = digitalpurchaseMapper.toEntity(digitalpurchaseDto);
        DigitalPurchase saved = digitalpurchaseService.save(entity);

        URI location = uriBuilder
                                .path("/api/digitalpurchases/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(digitalpurchaseMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<DigitalPurchaseDto>> createAllDigitalPurchases(
            @Valid @RequestBody List<DigitalPurchaseDto> digitalpurchaseDtoList,
            UriComponentsBuilder uriBuilder) {

        List<DigitalPurchase> entities = digitalpurchaseMapper.toEntityList(digitalpurchaseDtoList);
        List<DigitalPurchase> savedEntities = digitalpurchaseService.saveAll(entities);

        URI location = uriBuilder.path("/api/digitalpurchases").build().toUri();

        return ResponseEntity.created(location).body(digitalpurchaseMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DigitalPurchaseDto> updateDigitalPurchase(
            @PathVariable Long id,
            @Valid @RequestBody DigitalPurchaseDto digitalpurchaseDto) {


        DigitalPurchase entityToUpdate = digitalpurchaseMapper.toEntity(digitalpurchaseDto);
        DigitalPurchase updatedEntity = digitalpurchaseService.update(id, entityToUpdate);

        return ResponseEntity.ok(digitalpurchaseMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDigitalPurchase(@PathVariable Long id) {
        boolean deleted = digitalpurchaseService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}