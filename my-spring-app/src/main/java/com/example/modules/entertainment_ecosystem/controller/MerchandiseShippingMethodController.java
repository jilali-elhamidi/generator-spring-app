package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseShippingMethodDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingMethod;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseShippingMethodMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseShippingMethodService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandiseshippingmethods")
public class MerchandiseShippingMethodController {

    private final MerchandiseShippingMethodService merchandiseshippingmethodService;
    private final MerchandiseShippingMethodMapper merchandiseshippingmethodMapper;

    public MerchandiseShippingMethodController(MerchandiseShippingMethodService merchandiseshippingmethodService,
                                    MerchandiseShippingMethodMapper merchandiseshippingmethodMapper) {
        this.merchandiseshippingmethodService = merchandiseshippingmethodService;
        this.merchandiseshippingmethodMapper = merchandiseshippingmethodMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseShippingMethodDto>> getAllMerchandiseShippingMethods() {
        List<MerchandiseShippingMethod> entities = merchandiseshippingmethodService.findAll();
        return ResponseEntity.ok(merchandiseshippingmethodMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseShippingMethodDto> getMerchandiseShippingMethodById(@PathVariable Long id) {
        return merchandiseshippingmethodService.findById(id)
                .map(merchandiseshippingmethodMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseShippingMethodDto> createMerchandiseShippingMethod(
            @Valid @RequestBody MerchandiseShippingMethodDto merchandiseshippingmethodDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseShippingMethod entity = merchandiseshippingmethodMapper.toEntity(merchandiseshippingmethodDto);
        MerchandiseShippingMethod saved = merchandiseshippingmethodService.save(entity);

        URI location = uriBuilder
                                .path("/api/merchandiseshippingmethods/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(merchandiseshippingmethodMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseShippingMethodDto>> createAllMerchandiseShippingMethods(
            @Valid @RequestBody List<MerchandiseShippingMethodDto> merchandiseshippingmethodDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseShippingMethod> entities = merchandiseshippingmethodMapper.toEntityList(merchandiseshippingmethodDtoList);
        List<MerchandiseShippingMethod> savedEntities = merchandiseshippingmethodService.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandiseshippingmethods").build().toUri();

        return ResponseEntity.created(location).body(merchandiseshippingmethodMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseShippingMethodDto> updateMerchandiseShippingMethod(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseShippingMethodDto merchandiseshippingmethodDto) {


        MerchandiseShippingMethod entityToUpdate = merchandiseshippingmethodMapper.toEntity(merchandiseshippingmethodDto);
        MerchandiseShippingMethod updatedEntity = merchandiseshippingmethodService.update(id, entityToUpdate);

        return ResponseEntity.ok(merchandiseshippingmethodMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseShippingMethod(@PathVariable Long id) {
        boolean deleted = merchandiseshippingmethodService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}