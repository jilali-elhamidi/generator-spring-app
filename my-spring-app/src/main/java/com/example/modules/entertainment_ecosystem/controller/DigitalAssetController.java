package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.DigitalAssetDto;
import com.example.modules.entertainment_ecosystem.model.DigitalAsset;
import com.example.modules.entertainment_ecosystem.mapper.DigitalAssetMapper;
import com.example.modules.entertainment_ecosystem.service.DigitalAssetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/digitalassets")
public class DigitalAssetController {

    private final DigitalAssetService digitalassetService;
    private final DigitalAssetMapper digitalassetMapper;

    public DigitalAssetController(DigitalAssetService digitalassetService,
                                    DigitalAssetMapper digitalassetMapper) {
        this.digitalassetService = digitalassetService;
        this.digitalassetMapper = digitalassetMapper;
    }

    @GetMapping
    public ResponseEntity<List<DigitalAssetDto>> getAllDigitalAssets() {
        List<DigitalAsset> entities = digitalassetService.findAll();
        return ResponseEntity.ok(digitalassetMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DigitalAssetDto> getDigitalAssetById(@PathVariable Long id) {
        return digitalassetService.findById(id)
                .map(digitalassetMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DigitalAssetDto> createDigitalAsset(
            @Valid @RequestBody DigitalAssetDto digitalassetDto,
            UriComponentsBuilder uriBuilder) {

        DigitalAsset entity = digitalassetMapper.toEntity(digitalassetDto);
        DigitalAsset saved = digitalassetService.save(entity);

        URI location = uriBuilder
                                .path("/api/digitalassets/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(digitalassetMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DigitalAssetDto> updateDigitalAsset(
            @PathVariable Long id,
            @Valid @RequestBody DigitalAssetDto digitalassetDto) {


        DigitalAsset entityToUpdate = digitalassetMapper.toEntity(digitalassetDto);
        DigitalAsset updatedEntity = digitalassetService.update(id, entityToUpdate);

        return ResponseEntity.ok(digitalassetMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDigitalAsset(@PathVariable Long id) {
        boolean deleted = digitalassetService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}