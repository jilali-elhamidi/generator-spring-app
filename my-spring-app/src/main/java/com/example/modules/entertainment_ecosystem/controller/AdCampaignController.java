package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.AdCampaignDto;
import com.example.modules.entertainment_ecosystem.model.AdCampaign;
import com.example.modules.entertainment_ecosystem.mapper.AdCampaignMapper;
import com.example.modules.entertainment_ecosystem.service.AdCampaignService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/adcampaigns")
public class AdCampaignController {

    private final AdCampaignService adcampaignService;
    private final AdCampaignMapper adcampaignMapper;

    public AdCampaignController(AdCampaignService adcampaignService,
                                    AdCampaignMapper adcampaignMapper) {
        this.adcampaignService = adcampaignService;
        this.adcampaignMapper = adcampaignMapper;
    }

    @GetMapping
    public ResponseEntity<List<AdCampaignDto>> getAllAdCampaigns() {
        List<AdCampaign> entities = adcampaignService.findAll();
        return ResponseEntity.ok(adcampaignMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdCampaignDto> getAdCampaignById(@PathVariable Long id) {
        return adcampaignService.findById(id)
                .map(adcampaignMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AdCampaignDto> createAdCampaign(
            @Valid @RequestBody AdCampaignDto adcampaignDto,
            UriComponentsBuilder uriBuilder) {

        AdCampaign entity = adcampaignMapper.toEntity(adcampaignDto);
        AdCampaign saved = adcampaignService.save(entity);
        URI location = uriBuilder.path("/api/adcampaigns/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(adcampaignMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<AdCampaignDto> updateAdCampaign(
                @PathVariable Long id,
                @Valid @RequestBody AdCampaignDto adcampaignDto) {

                try {
                // Récupérer l'entité existante avec Optional
                AdCampaign existing = adcampaignService.findById(id)
                .orElseThrow(() -> new RuntimeException("AdCampaign not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                adcampaignMapper.updateEntityFromDto(adcampaignDto, existing);

                // Sauvegarde
                AdCampaign updatedEntity = adcampaignService.save(existing);

                return ResponseEntity.ok(adcampaignMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdCampaign(@PathVariable Long id) {
        adcampaignService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}