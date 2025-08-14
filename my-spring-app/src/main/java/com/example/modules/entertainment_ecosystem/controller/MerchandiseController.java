package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseDto;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandises")
public class MerchandiseController {

    private final MerchandiseService merchandiseService;
    private final MerchandiseMapper merchandiseMapper;

    public MerchandiseController(MerchandiseService merchandiseService,
                                    MerchandiseMapper merchandiseMapper) {
        this.merchandiseService = merchandiseService;
        this.merchandiseMapper = merchandiseMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseDto>> getAllMerchandises() {
        List<Merchandise> entities = merchandiseService.findAll();
        return ResponseEntity.ok(merchandiseMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseDto> getMerchandiseById(@PathVariable Long id) {
        return merchandiseService.findById(id)
                .map(merchandiseMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseDto> createMerchandise(
            @Valid @RequestBody MerchandiseDto merchandiseDto,
            UriComponentsBuilder uriBuilder) {

        Merchandise entity = merchandiseMapper.toEntity(merchandiseDto);
        Merchandise saved = merchandiseService.save(entity);
        URI location = uriBuilder.path("/api/merchandises/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(merchandiseMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MerchandiseDto> updateMerchandise(
                @PathVariable Long id,
                @Valid @RequestBody MerchandiseDto merchandiseDto) {

                try {
                // Récupérer l'entité existante avec Optional
                Merchandise existing = merchandiseService.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchandise not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                merchandiseMapper.updateEntityFromDto(merchandiseDto, existing);

                // Sauvegarde
                Merchandise updatedEntity = merchandiseService.save(existing);

                return ResponseEntity.ok(merchandiseMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandise(@PathVariable Long id) {
        merchandiseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}