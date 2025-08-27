package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.SubscriptionTierDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionTierSimpleDto;
import com.example.modules.entertainment_ecosystem.model.SubscriptionTier;
import com.example.modules.entertainment_ecosystem.mapper.SubscriptionTierMapper;
import com.example.modules.entertainment_ecosystem.service.SubscriptionTierService;
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
 * Controller for managing SubscriptionTier entities.
 */
@RestController
@RequestMapping("/api/subscriptiontiers")
public class SubscriptionTierController extends BaseController<SubscriptionTier, SubscriptionTierDto, SubscriptionTierSimpleDto> {

    public SubscriptionTierController(SubscriptionTierService subscriptiontierService,
                                    SubscriptionTierMapper subscriptiontierMapper) {
        super(subscriptiontierService, subscriptiontierMapper);
    }

    @GetMapping
    public ResponseEntity<Page<SubscriptionTierDto>> getAllSubscriptionTiers(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SubscriptionTierDto>> searchSubscriptionTiers(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(SubscriptionTier.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionTierDto> getSubscriptionTierById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<SubscriptionTierDto> createSubscriptionTier(
            @Valid @RequestBody SubscriptionTierDto subscriptiontierDto,
            UriComponentsBuilder uriBuilder) {

        SubscriptionTier entity = mapper.toEntity(subscriptiontierDto);
        SubscriptionTier saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/subscriptiontiers/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<SubscriptionTierDto>> createAllSubscriptionTiers(
            @Valid @RequestBody List<SubscriptionTierDto> subscriptiontierDtoList,
            UriComponentsBuilder uriBuilder) {

        List<SubscriptionTier> entities = mapper.toEntityList(subscriptiontierDtoList);
        List<SubscriptionTier> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/subscriptiontiers").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionTierDto> updateSubscriptionTier(
            @PathVariable Long id,
            @Valid @RequestBody SubscriptionTierDto subscriptiontierDto) {

        SubscriptionTier entityToUpdate = mapper.toEntity(subscriptiontierDto);
        SubscriptionTier updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriptionTier(@PathVariable Long id) {
        return doDelete(id);
    }
}