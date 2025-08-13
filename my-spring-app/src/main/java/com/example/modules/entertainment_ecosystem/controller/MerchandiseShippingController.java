package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseShippingDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseShippingMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseShippingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/merchandiseshippings")
public class MerchandiseShippingController {

    private final MerchandiseShippingService merchandiseshippingService;
    private final MerchandiseShippingMapper merchandiseshippingMapper;

    public MerchandiseShippingController(MerchandiseShippingService merchandiseshippingService,
                                    MerchandiseShippingMapper merchandiseshippingMapper) {
        this.merchandiseshippingService = merchandiseshippingService;
        this.merchandiseshippingMapper = merchandiseshippingMapper;
    }

    @GetMapping
    public ResponseEntity<List<MerchandiseShippingDto>> getAllMerchandiseShippings() {
        List<MerchandiseShipping> entities = merchandiseshippingService.findAll();
        return ResponseEntity.ok(merchandiseshippingMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseShippingDto> getMerchandiseShippingById(@PathVariable Long id) {
        return merchandiseshippingService.findById(id)
                .map(merchandiseshippingMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchandiseShippingDto> createMerchandiseShipping(
            @Valid @RequestBody MerchandiseShippingDto merchandiseshippingDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseShipping entity = merchandiseshippingMapper.toEntity(merchandiseshippingDto);
        MerchandiseShipping saved = merchandiseshippingService.save(entity);
        URI location = uriBuilder.path("/api/merchandiseshippings/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(merchandiseshippingMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseShippingDto> updateMerchandiseShipping(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseShippingDto merchandiseshippingDto) {

        try {
            MerchandiseShipping updatedEntity = merchandiseshippingService.update(
                    id,
                    merchandiseshippingMapper.toEntity(merchandiseshippingDto)
            );
            return ResponseEntity.ok(merchandiseshippingMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseShipping(@PathVariable Long id) {
        merchandiseshippingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}