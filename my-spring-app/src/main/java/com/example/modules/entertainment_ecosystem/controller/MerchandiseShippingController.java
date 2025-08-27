package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseShippingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseShippingMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseShippingService;
import com.example.core.controller.BaseController;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing MerchandiseShipping entities.
 */
@RestController
@RequestMapping("/api/merchandiseshippings")
public class MerchandiseShippingController extends BaseController<MerchandiseShipping, MerchandiseShippingDto, MerchandiseShippingSimpleDto> {

    public MerchandiseShippingController(MerchandiseShippingService merchandiseshippingService,
                                    MerchandiseShippingMapper merchandiseshippingMapper) {
        super(merchandiseshippingService, merchandiseshippingMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MerchandiseShippingDto>> getAllMerchandiseShippings(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseShippingDto>> searchMerchandiseShippings(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MerchandiseShipping.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseShippingDto> getMerchandiseShippingById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MerchandiseShippingDto> createMerchandiseShipping(
            @Valid @RequestBody MerchandiseShippingDto merchandiseshippingDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseShipping entity = mapper.toEntity(merchandiseshippingDto);
        MerchandiseShipping saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/merchandiseshippings/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseShippingDto>> createAllMerchandiseShippings(
            @Valid @RequestBody List<MerchandiseShippingDto> merchandiseshippingDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseShipping> entities = mapper.toEntityList(merchandiseshippingDtoList);
        List<MerchandiseShipping> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandiseshippings").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseShippingDto> updateMerchandiseShipping(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseShippingDto merchandiseshippingDto) {

        MerchandiseShipping entityToUpdate = mapper.toEntity(merchandiseshippingDto);
        MerchandiseShipping updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseShipping(@PathVariable Long id) {
        return doDelete(id);
    }
}