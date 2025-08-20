package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.SubscriptionPlanDto;
import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import com.example.modules.entertainment_ecosystem.mapper.SubscriptionPlanMapper;
import com.example.modules.entertainment_ecosystem.service.SubscriptionPlanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptionplans")
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionplanService;
    private final SubscriptionPlanMapper subscriptionplanMapper;

    public SubscriptionPlanController(SubscriptionPlanService subscriptionplanService,
                                    SubscriptionPlanMapper subscriptionplanMapper) {
        this.subscriptionplanService = subscriptionplanService;
        this.subscriptionplanMapper = subscriptionplanMapper;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionPlanDto>> getAllSubscriptionPlans() {
        List<SubscriptionPlan> entities = subscriptionplanService.findAll();
        return ResponseEntity.ok(subscriptionplanMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionPlanDto> getSubscriptionPlanById(@PathVariable Long id) {
        return subscriptionplanService.findById(id)
                .map(subscriptionplanMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SubscriptionPlanDto> createSubscriptionPlan(
            @Valid @RequestBody SubscriptionPlanDto subscriptionplanDto,
            UriComponentsBuilder uriBuilder) {

        SubscriptionPlan entity = subscriptionplanMapper.toEntity(subscriptionplanDto);
        SubscriptionPlan saved = subscriptionplanService.save(entity);

        URI location = uriBuilder
                                .path("/api/subscriptionplans/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(subscriptionplanMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionPlanDto> updateSubscriptionPlan(
            @PathVariable Long id,
            @Valid @RequestBody SubscriptionPlanDto subscriptionplanDto) {


        SubscriptionPlan entityToUpdate = subscriptionplanMapper.toEntity(subscriptionplanDto);
        SubscriptionPlan updatedEntity = subscriptionplanService.update(id, entityToUpdate);

        return ResponseEntity.ok(subscriptionplanMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriptionPlan(@PathVariable Long id) {
        boolean deleted = subscriptionplanService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}