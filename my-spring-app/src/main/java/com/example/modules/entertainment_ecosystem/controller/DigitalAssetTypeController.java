package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.DigitalAssetTypeDto;
import com.example.modules.entertainment_ecosystem.model.DigitalAssetType;
import com.example.modules.entertainment_ecosystem.mapper.DigitalAssetTypeMapper;
import com.example.modules.entertainment_ecosystem.service.DigitalAssetTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/digitalassettypes")
public class DigitalAssetTypeController {

    private final DigitalAssetTypeService digitalassettypeService;
    private final DigitalAssetTypeMapper digitalassettypeMapper;

    public DigitalAssetTypeController(DigitalAssetTypeService digitalassettypeService,
                                    DigitalAssetTypeMapper digitalassettypeMapper) {
        this.digitalassettypeService = digitalassettypeService;
        this.digitalassettypeMapper = digitalassettypeMapper;
    }

    @GetMapping
    public ResponseEntity<List<DigitalAssetTypeDto>> getAllDigitalAssetTypes() {
        List<DigitalAssetType> entities = digitalassettypeService.findAll();
        return ResponseEntity.ok(digitalassettypeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DigitalAssetTypeDto> getDigitalAssetTypeById(@PathVariable Long id) {
        return digitalassettypeService.findById(id)
                .map(digitalassettypeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DigitalAssetTypeDto> createDigitalAssetType(
            @Valid @RequestBody DigitalAssetTypeDto digitalassettypeDto,
            UriComponentsBuilder uriBuilder) {

        DigitalAssetType entity = digitalassettypeMapper.toEntity(digitalassettypeDto);
        DigitalAssetType saved = digitalassettypeService.save(entity);
        URI location = uriBuilder.path("/api/digitalassettypes/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(digitalassettypeMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<DigitalAssetTypeDto> updateDigitalAssetType(
                @PathVariable Long id,
                @Valid @RequestBody DigitalAssetTypeDto digitalassettypeDto) {

                try {
                // Récupérer l'entité existante avec Optional
                DigitalAssetType existing = digitalassettypeService.findById(id)
                .orElseThrow(() -> new RuntimeException("DigitalAssetType not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                digitalassettypeMapper.updateEntityFromDto(digitalassettypeDto, existing);

                // Sauvegarde
                DigitalAssetType updatedEntity = digitalassettypeService.save(existing);

                return ResponseEntity.ok(digitalassettypeMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDigitalAssetType(@PathVariable Long id) {
        digitalassettypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}