package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.SubscriptionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Subscription;
import com.example.modules.entertainment_ecosystem.mapper.SubscriptionMapper;
import com.example.modules.entertainment_ecosystem.service.SubscriptionService;
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
 * Controller for managing Subscription entities.
 */
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController extends BaseController<Subscription, SubscriptionDto, SubscriptionSimpleDto> {

    public SubscriptionController(SubscriptionService subscriptionService,
                                    SubscriptionMapper subscriptionMapper) {
        super(subscriptionService, subscriptionMapper);
    }

    @GetMapping
    public ResponseEntity<Page<SubscriptionDto>> getAllSubscriptions(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SubscriptionDto>> searchSubscriptions(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Subscription.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDto> getSubscriptionById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<SubscriptionDto> createSubscription(
            @Valid @RequestBody SubscriptionDto subscriptionDto,
            UriComponentsBuilder uriBuilder) {

        Subscription entity = mapper.toEntity(subscriptionDto);
        Subscription saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/subscriptions/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<SubscriptionDto>> createAllSubscriptions(
            @Valid @RequestBody List<SubscriptionDto> subscriptionDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Subscription> entities = mapper.toEntityList(subscriptionDtoList);
        List<Subscription> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/subscriptions").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDto> updateSubscription(
            @PathVariable Long id,
            @Valid @RequestBody SubscriptionDto subscriptionDto) {

        Subscription entityToUpdate = mapper.toEntity(subscriptionDto);
        Subscription updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        return doDelete(id);
    }
}