package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.SubscriptionFeatureDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionFeatureSimpleDto;
import com.example.modules.entertainment_ecosystem.model.SubscriptionFeature;
import com.example.modules.entertainment_ecosystem.mapper.SubscriptionFeatureMapper;
import com.example.modules.entertainment_ecosystem.service.SubscriptionFeatureService;
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
 * Controller for managing SubscriptionFeature entities.
 */
@RestController
@RequestMapping("/api/subscriptionfeatures")
public class SubscriptionFeatureController extends BaseController<SubscriptionFeature, SubscriptionFeatureDto, SubscriptionFeatureSimpleDto> {

    public SubscriptionFeatureController(SubscriptionFeatureService subscriptionfeatureService,
                                    SubscriptionFeatureMapper subscriptionfeatureMapper) {
        super(subscriptionfeatureService, subscriptionfeatureMapper);
    }

    @GetMapping
    public ResponseEntity<Page<SubscriptionFeatureDto>> getAllSubscriptionFeatures(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SubscriptionFeatureDto>> searchSubscriptionFeatures(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(SubscriptionFeature.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionFeatureDto> getSubscriptionFeatureById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<SubscriptionFeatureDto> createSubscriptionFeature(
            @Valid @RequestBody SubscriptionFeatureDto subscriptionfeatureDto,
            UriComponentsBuilder uriBuilder) {

        SubscriptionFeature entity = mapper.toEntity(subscriptionfeatureDto);
        SubscriptionFeature saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/subscriptionfeatures/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<SubscriptionFeatureDto>> createAllSubscriptionFeatures(
            @Valid @RequestBody List<SubscriptionFeatureDto> subscriptionfeatureDtoList,
            UriComponentsBuilder uriBuilder) {

        List<SubscriptionFeature> entities = mapper.toEntityList(subscriptionfeatureDtoList);
        List<SubscriptionFeature> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/subscriptionfeatures").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionFeatureDto> updateSubscriptionFeature(
            @PathVariable Long id,
            @Valid @RequestBody SubscriptionFeatureDto subscriptionfeatureDto) {

        SubscriptionFeature entityToUpdate = mapper.toEntity(subscriptionfeatureDto);
        SubscriptionFeature updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriptionFeature(@PathVariable Long id) {
        return doDelete(id);
    }
}