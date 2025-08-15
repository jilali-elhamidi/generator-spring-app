package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.SubscriptionDto;
import com.example.modules.entertainment_ecosystem.model.Subscription;
import com.example.modules.entertainment_ecosystem.mapper.SubscriptionMapper;
import com.example.modules.entertainment_ecosystem.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionController(SubscriptionService subscriptionService,
                                    SubscriptionMapper subscriptionMapper) {
        this.subscriptionService = subscriptionService;
        this.subscriptionMapper = subscriptionMapper;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> getAllSubscriptions() {
        List<Subscription> entities = subscriptionService.findAll();
        return ResponseEntity.ok(subscriptionMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDto> getSubscriptionById(@PathVariable Long id) {
        return subscriptionService.findById(id)
                .map(subscriptionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SubscriptionDto> createSubscription(
            @Valid @RequestBody SubscriptionDto subscriptionDto,
            UriComponentsBuilder uriBuilder) {

        Subscription entity = subscriptionMapper.toEntity(subscriptionDto);
        Subscription saved = subscriptionService.save(entity);
        URI location = uriBuilder.path("/api/subscriptions/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(subscriptionMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<SubscriptionDto> updateSubscription(
                @PathVariable Long id,
                @RequestBody SubscriptionDto subscriptionDto) {

                // Transformer le DTO en entity pour le service
                Subscription entityToUpdate = subscriptionMapper.toEntity(subscriptionDto);

                // Appel du service update
                Subscription updatedEntity = subscriptionService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                SubscriptionDto updatedDto = subscriptionMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}