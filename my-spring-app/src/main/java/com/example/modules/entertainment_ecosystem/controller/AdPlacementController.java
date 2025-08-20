package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.AdPlacementDto;
import com.example.modules.entertainment_ecosystem.model.AdPlacement;
import com.example.modules.entertainment_ecosystem.mapper.AdPlacementMapper;
import com.example.modules.entertainment_ecosystem.service.AdPlacementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/adplacements")
public class AdPlacementController {

    private final AdPlacementService adplacementService;
    private final AdPlacementMapper adplacementMapper;

    public AdPlacementController(AdPlacementService adplacementService,
                                    AdPlacementMapper adplacementMapper) {
        this.adplacementService = adplacementService;
        this.adplacementMapper = adplacementMapper;
    }

    @GetMapping
    public ResponseEntity<List<AdPlacementDto>> getAllAdPlacements() {
        List<AdPlacement> entities = adplacementService.findAll();
        return ResponseEntity.ok(adplacementMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdPlacementDto> getAdPlacementById(@PathVariable Long id) {
        return adplacementService.findById(id)
                .map(adplacementMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AdPlacementDto> createAdPlacement(
            @Valid @RequestBody AdPlacementDto adplacementDto,
            UriComponentsBuilder uriBuilder) {

        AdPlacement entity = adplacementMapper.toEntity(adplacementDto);
        AdPlacement saved = adplacementService.save(entity);

        URI location = uriBuilder
                                .path("/api/adplacements/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(adplacementMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdPlacementDto> updateAdPlacement(
            @PathVariable Long id,
            @Valid @RequestBody AdPlacementDto adplacementDto) {


        AdPlacement entityToUpdate = adplacementMapper.toEntity(adplacementDto);
        AdPlacement updatedEntity = adplacementService.update(id, entityToUpdate);

        return ResponseEntity.ok(adplacementMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdPlacement(@PathVariable Long id) {
        boolean deleted = adplacementService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}