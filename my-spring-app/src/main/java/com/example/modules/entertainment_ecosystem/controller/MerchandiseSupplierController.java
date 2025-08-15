package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseSupplierDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseSupplier;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseSupplierMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseSupplierService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandisesuppliers")
public class MerchandiseSupplierController {

    private final MerchandiseSupplierService merchandisesupplierService;
    private final MerchandiseSupplierMapper merchandisesupplierMapper;

    public MerchandiseSupplierController(MerchandiseSupplierService merchandisesupplierService,
                                    MerchandiseSupplierMapper merchandisesupplierMapper) {
        this.merchandisesupplierService = merchandisesupplierService;
        this.merchandisesupplierMapper = merchandisesupplierMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseSupplierDto>> getAllMerchandiseSuppliers() {
        List<MerchandiseSupplier> entities = merchandisesupplierService.findAll();
        return ResponseEntity.ok(merchandisesupplierMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseSupplierDto> getMerchandiseSupplierById(@PathVariable Long id) {
        return merchandisesupplierService.findById(id)
                .map(merchandisesupplierMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseSupplierDto> createMerchandiseSupplier(
            @Valid @RequestBody MerchandiseSupplierDto merchandisesupplierDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseSupplier entity = merchandisesupplierMapper.toEntity(merchandisesupplierDto);
        MerchandiseSupplier saved = merchandisesupplierService.save(entity);
        URI location = uriBuilder.path("/api/merchandisesuppliers/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(merchandisesupplierMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MerchandiseSupplierDto> updateMerchandiseSupplier(
                @PathVariable Long id,
                @RequestBody MerchandiseSupplierDto merchandisesupplierDto) {

                // Transformer le DTO en entity pour le service
                MerchandiseSupplier entityToUpdate = merchandisesupplierMapper.toEntity(merchandisesupplierDto);

                // Appel du service update
                MerchandiseSupplier updatedEntity = merchandisesupplierService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MerchandiseSupplierDto updatedDto = merchandisesupplierMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseSupplier(@PathVariable Long id) {
        merchandisesupplierService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}