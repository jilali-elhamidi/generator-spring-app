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

        URI location = uriBuilder
                                .path("/api/adcampaigns/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(adcampaignMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AdCampaignDto>> createAllAdCampaigns(
            @Valid @RequestBody List<AdCampaignDto> adcampaignDtoList,
            UriComponentsBuilder uriBuilder) {

        List<AdCampaign> entities = adcampaignMapper.toEntityList(adcampaignDtoList);
        List<AdCampaign> savedEntities = adcampaignService.saveAll(entities);

        URI location = uriBuilder.path("/api/adcampaigns").build().toUri();

        return ResponseEntity.created(location).body(adcampaignMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdCampaignDto> updateAdCampaign(
            @PathVariable Long id,
            @Valid @RequestBody AdCampaignDto adcampaignDto) {


        AdCampaign entityToUpdate = adcampaignMapper.toEntity(adcampaignDto);
        AdCampaign updatedEntity = adcampaignService.update(id, entityToUpdate);

        return ResponseEntity.ok(adcampaignMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdCampaign(@PathVariable Long id) {
        boolean deleted = adcampaignService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}