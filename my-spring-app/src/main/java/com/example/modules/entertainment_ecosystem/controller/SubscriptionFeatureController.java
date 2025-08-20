package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.SubscriptionFeatureDto;
import com.example.modules.entertainment_ecosystem.model.SubscriptionFeature;
import com.example.modules.entertainment_ecosystem.mapper.SubscriptionFeatureMapper;
import com.example.modules.entertainment_ecosystem.service.SubscriptionFeatureService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptionfeatures")
public class SubscriptionFeatureController {

    private final SubscriptionFeatureService subscriptionfeatureService;
    private final SubscriptionFeatureMapper subscriptionfeatureMapper;

    public SubscriptionFeatureController(SubscriptionFeatureService subscriptionfeatureService,
                                    SubscriptionFeatureMapper subscriptionfeatureMapper) {
        this.subscriptionfeatureService = subscriptionfeatureService;
        this.subscriptionfeatureMapper = subscriptionfeatureMapper;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionFeatureDto>> getAllSubscriptionFeatures() {
        List<SubscriptionFeature> entities = subscriptionfeatureService.findAll();
        return ResponseEntity.ok(subscriptionfeatureMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionFeatureDto> getSubscriptionFeatureById(@PathVariable Long id) {
        return subscriptionfeatureService.findById(id)
                .map(subscriptionfeatureMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SubscriptionFeatureDto> createSubscriptionFeature(
            @Valid @RequestBody SubscriptionFeatureDto subscriptionfeatureDto,
            UriComponentsBuilder uriBuilder) {

        SubscriptionFeature entity = subscriptionfeatureMapper.toEntity(subscriptionfeatureDto);
        SubscriptionFeature saved = subscriptionfeatureService.save(entity);
        URI location = uriBuilder.path("/api/subscriptionfeatures/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(subscriptionfeatureMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<SubscriptionFeatureDto> updateSubscriptionFeature(
                @PathVariable Long id,
                @RequestBody SubscriptionFeatureDto subscriptionfeatureDto) {

                // Transformer le DTO en entity pour le service
                SubscriptionFeature entityToUpdate = subscriptionfeatureMapper.toEntity(subscriptionfeatureDto);

                // Appel du service update
                SubscriptionFeature updatedEntity = subscriptionfeatureService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                SubscriptionFeatureDto updatedDto = subscriptionfeatureMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteSubscriptionFeature(@PathVariable Long id) {
                    boolean deleted = subscriptionfeatureService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}