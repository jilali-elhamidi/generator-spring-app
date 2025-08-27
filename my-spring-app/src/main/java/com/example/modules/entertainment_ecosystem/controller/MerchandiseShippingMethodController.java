package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MerchandiseShippingMethodDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingMethodSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingMethod;
import com.example.modules.entertainment_ecosystem.mapper.MerchandiseShippingMethodMapper;
import com.example.modules.entertainment_ecosystem.service.MerchandiseShippingMethodService;
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
 * Controller for managing MerchandiseShippingMethod entities.
 */
@RestController
@RequestMapping("/api/merchandiseshippingmethods")
public class MerchandiseShippingMethodController extends BaseController<MerchandiseShippingMethod, MerchandiseShippingMethodDto, MerchandiseShippingMethodSimpleDto> {

    public MerchandiseShippingMethodController(MerchandiseShippingMethodService merchandiseshippingmethodService,
                                    MerchandiseShippingMethodMapper merchandiseshippingmethodMapper) {
        super(merchandiseshippingmethodService, merchandiseshippingmethodMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MerchandiseShippingMethodDto>> getAllMerchandiseShippingMethods(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseShippingMethodDto>> searchMerchandiseShippingMethods(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MerchandiseShippingMethod.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchandiseShippingMethodDto> getMerchandiseShippingMethodById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MerchandiseShippingMethodDto> createMerchandiseShippingMethod(
            @Valid @RequestBody MerchandiseShippingMethodDto merchandiseshippingmethodDto,
            UriComponentsBuilder uriBuilder) {

        MerchandiseShippingMethod entity = mapper.toEntity(merchandiseshippingmethodDto);
        MerchandiseShippingMethod saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/merchandiseshippingmethods/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MerchandiseShippingMethodDto>> createAllMerchandiseShippingMethods(
            @Valid @RequestBody List<MerchandiseShippingMethodDto> merchandiseshippingmethodDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MerchandiseShippingMethod> entities = mapper.toEntityList(merchandiseshippingmethodDtoList);
        List<MerchandiseShippingMethod> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/merchandiseshippingmethods").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchandiseShippingMethodDto> updateMerchandiseShippingMethod(
            @PathVariable Long id,
            @Valid @RequestBody MerchandiseShippingMethodDto merchandiseshippingmethodDto) {

        MerchandiseShippingMethod entityToUpdate = mapper.toEntity(merchandiseshippingmethodDto);
        MerchandiseShippingMethod updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchandiseShippingMethod(@PathVariable Long id) {
        return doDelete(id);
    }
}