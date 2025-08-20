package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.SubscriptionTierDto;
import com.example.modules.entertainment_ecosystem.model.SubscriptionTier;
import com.example.modules.entertainment_ecosystem.mapper.SubscriptionTierMapper;
import com.example.modules.entertainment_ecosystem.service.SubscriptionTierService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptiontiers")
public class SubscriptionTierController {

    private final SubscriptionTierService subscriptiontierService;
    private final SubscriptionTierMapper subscriptiontierMapper;

    public SubscriptionTierController(SubscriptionTierService subscriptiontierService,
                                    SubscriptionTierMapper subscriptiontierMapper) {
        this.subscriptiontierService = subscriptiontierService;
        this.subscriptiontierMapper = subscriptiontierMapper;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionTierDto>> getAllSubscriptionTiers() {
        List<SubscriptionTier> entities = subscriptiontierService.findAll();
        return ResponseEntity.ok(subscriptiontierMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionTierDto> getSubscriptionTierById(@PathVariable Long id) {
        return subscriptiontierService.findById(id)
                .map(subscriptiontierMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SubscriptionTierDto> createSubscriptionTier(
            @Valid @RequestBody SubscriptionTierDto subscriptiontierDto,
            UriComponentsBuilder uriBuilder) {

        SubscriptionTier entity = subscriptiontierMapper.toEntity(subscriptiontierDto);
        SubscriptionTier saved = subscriptiontierService.save(entity);
        URI location = uriBuilder.path("/api/subscriptiontiers/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(subscriptiontierMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<SubscriptionTierDto> updateSubscriptionTier(
                @PathVariable Long id,
                @RequestBody SubscriptionTierDto subscriptiontierDto) {

                // Transformer le DTO en entity pour le service
                SubscriptionTier entityToUpdate = subscriptiontierMapper.toEntity(subscriptiontierDto);

                // Appel du service update
                SubscriptionTier updatedEntity = subscriptiontierService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                SubscriptionTierDto updatedDto = subscriptiontierMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteSubscriptionTier(@PathVariable Long id) {
                    boolean deleted = subscriptiontierService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}