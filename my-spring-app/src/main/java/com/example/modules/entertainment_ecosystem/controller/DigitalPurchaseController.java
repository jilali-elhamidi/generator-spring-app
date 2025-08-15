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
        URI location = uriBuilder.path("/api/digitalpurchases/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(digitalpurchaseMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<DigitalPurchaseDto> updateDigitalPurchase(
                @PathVariable Long id,
                @RequestBody DigitalPurchaseDto digitalpurchaseDto) {

                // Transformer le DTO en entity pour le service
                DigitalPurchase entityToUpdate = digitalpurchaseMapper.toEntity(digitalpurchaseDto);

                // Appel du service update
                DigitalPurchase updatedEntity = digitalpurchaseService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                DigitalPurchaseDto updatedDto = digitalpurchaseMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDigitalPurchase(@PathVariable Long id) {
        digitalpurchaseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}