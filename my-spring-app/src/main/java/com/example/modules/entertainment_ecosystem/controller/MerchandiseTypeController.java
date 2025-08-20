package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseTypeDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseType;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseTypeMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandisetypes")
public class MerchandiseTypeController {

    private final MerchandiseTypeService merchandisetypeService;
    private final MerchandiseTypeMapper merchandisetypeMapper;

    public MerchandiseTypeController(MerchandiseTypeService merchandisetypeService,
                                    MerchandiseTypeMapper merchandisetypeMapper) {
        this.merchandisetypeService = merchandisetypeService;
        this.merchandisetypeMapper = merchandisetypeMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseTypeDto>> getAllMerchandiseTypes() {
        List<MerchandiseType> entities = merchandisetypeService.findAll();
        return ResponseEntity.ok(merchandisetypeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseTypeDto> getMerchandiseTypeById(@PathVariable Long id) {
        return merchandisetypeService.findById(id)
                .map(merchandisetypeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseTypeDto> createMerchandiseType(
            @Valid @RequestBody MerchandiseTypeDto merchandisetypeDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseType entity = merchandisetypeMapper.toEntity(merchandisetypeDto);
        MerchandiseType saved = merchandisetypeService.save(entity);

        URI location = uriBuilder
                                .path("/api/merchandisetypes/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(merchandisetypeMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseTypeDto> updateMerchandiseType(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseTypeDto merchandisetypeDto) {


        MerchandiseType entityToUpdate = merchandisetypeMapper.toEntity(merchandisetypeDto);
        MerchandiseType updatedEntity = merchandisetypeService.update(id, entityToUpdate);

        return ResponseEntity.ok(merchandisetypeMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseType(@PathVariable Long id) {
        boolean deleted = merchandisetypeService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}