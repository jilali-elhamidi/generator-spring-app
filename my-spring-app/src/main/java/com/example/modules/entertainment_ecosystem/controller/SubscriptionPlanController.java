package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.SubscriptionPlanDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionPlanSimpleDto;
import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import com.example.modules.entertainment_ecosystem.mapper.SubscriptionPlanMapper;
import com.example.modules.entertainment_ecosystem.service.SubscriptionPlanService;
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
 * Controller for managing SubscriptionPlan entities.
 */
@RestController
@RequestMapping("/api/subscriptionplans")
public class SubscriptionPlanController extends BaseController<SubscriptionPlan, SubscriptionPlanDto, SubscriptionPlanSimpleDto> {

    public SubscriptionPlanController(SubscriptionPlanService subscriptionplanService,
                                    SubscriptionPlanMapper subscriptionplanMapper) {
        super(subscriptionplanService, subscriptionplanMapper);
    }

    @GetMapping
    public ResponseEntity<Page<SubscriptionPlanDto>> getAllSubscriptionPlans(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SubscriptionPlanDto>> searchSubscriptionPlans(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(SubscriptionPlan.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionPlanDto> getSubscriptionPlanById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<SubscriptionPlanDto> createSubscriptionPlan(
            @Valid @RequestBody SubscriptionPlanDto subscriptionplanDto,
            UriComponentsBuilder uriBuilder) {

        SubscriptionPlan entity = mapper.toEntity(subscriptionplanDto);
        SubscriptionPlan saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/subscriptionplans/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<SubscriptionPlanDto>> createAllSubscriptionPlans(
            @Valid @RequestBody List<SubscriptionPlanDto> subscriptionplanDtoList,
            UriComponentsBuilder uriBuilder) {

        List<SubscriptionPlan> entities = mapper.toEntityList(subscriptionplanDtoList);
        List<SubscriptionPlan> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/subscriptionplans").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionPlanDto> updateSubscriptionPlan(
            @PathVariable Long id,
            @Valid @RequestBody SubscriptionPlanDto subscriptionplanDto) {

        SubscriptionPlan entityToUpdate = mapper.toEntity(subscriptionplanDto);
        SubscriptionPlan updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriptionPlan(@PathVariable Long id) {
        return doDelete(id);
    }
}