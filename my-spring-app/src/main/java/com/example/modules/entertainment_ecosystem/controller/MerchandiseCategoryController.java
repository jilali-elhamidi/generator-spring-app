package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseCategoryDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseCategory;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseCategoryMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandisecategorys")
public class MerchandiseCategoryController {

    private final MerchandiseCategoryService merchandisecategoryService;
    private final MerchandiseCategoryMapper merchandisecategoryMapper;

    public MerchandiseCategoryController(MerchandiseCategoryService merchandisecategoryService,
                                    MerchandiseCategoryMapper merchandisecategoryMapper) {
        this.merchandisecategoryService = merchandisecategoryService;
        this.merchandisecategoryMapper = merchandisecategoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseCategoryDto>> getAllMerchandiseCategorys() {
        List<MerchandiseCategory> entities = merchandisecategoryService.findAll();
        return ResponseEntity.ok(merchandisecategoryMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseCategoryDto> getMerchandiseCategoryById(@PathVariable Long id) {
        return merchandisecategoryService.findById(id)
                .map(merchandisecategoryMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseCategoryDto> createMerchandiseCategory(
            @Valid @RequestBody MerchandiseCategoryDto merchandisecategoryDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseCategory entity = merchandisecategoryMapper.toEntity(merchandisecategoryDto);
        MerchandiseCategory saved = merchandisecategoryService.save(entity);

        URI location = uriBuilder
                                .path("/api/merchandisecategorys/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(merchandisecategoryMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseCategoryDto> updateMerchandiseCategory(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseCategoryDto merchandisecategoryDto) {


        MerchandiseCategory entityToUpdate = merchandisecategoryMapper.toEntity(merchandisecategoryDto);
        MerchandiseCategory updatedEntity = merchandisecategoryService.update(id, entityToUpdate);

        return ResponseEntity.ok(merchandisecategoryMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseCategory(@PathVariable Long id) {
        boolean deleted = merchandisecategoryService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}