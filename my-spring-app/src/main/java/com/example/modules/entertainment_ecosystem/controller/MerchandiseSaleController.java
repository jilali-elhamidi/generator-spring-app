package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseSaleDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseSale;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseSaleMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseSaleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandisesales")
public class MerchandiseSaleController {

    private final MerchandiseSaleService merchandisesaleService;
    private final MerchandiseSaleMapper merchandisesaleMapper;

    public MerchandiseSaleController(MerchandiseSaleService merchandisesaleService,
                                    MerchandiseSaleMapper merchandisesaleMapper) {
        this.merchandisesaleService = merchandisesaleService;
        this.merchandisesaleMapper = merchandisesaleMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseSaleDto>> getAllMerchandiseSales() {
        List<MerchandiseSale> entities = merchandisesaleService.findAll();
        return ResponseEntity.ok(merchandisesaleMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseSaleDto> getMerchandiseSaleById(@PathVariable Long id) {
        return merchandisesaleService.findById(id)
                .map(merchandisesaleMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseSaleDto> createMerchandiseSale(
            @Valid @RequestBody MerchandiseSaleDto merchandisesaleDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseSale entity = merchandisesaleMapper.toEntity(merchandisesaleDto);
        MerchandiseSale saved = merchandisesaleService.save(entity);
        URI location = uriBuilder.path("/api/merchandisesales/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(merchandisesaleMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseSaleDto> updateMerchandiseSale(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseSaleDto merchandisesaleDto) {

        try {
            MerchandiseSale updatedEntity = merchandisesaleService.update(
                    id,
                    merchandisesaleMapper.toEntity(merchandisesaleDto)
            );
            return ResponseEntity.ok(merchandisesaleMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseSale(@PathVariable Long id) {
        merchandisesaleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}