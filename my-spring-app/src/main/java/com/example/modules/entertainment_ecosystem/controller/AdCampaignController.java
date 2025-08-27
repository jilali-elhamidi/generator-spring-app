package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.AdCampaignDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AdCampaignSimpleDto;
import com.example.modules.entertainment_ecosystem.model.AdCampaign;
import com.example.modules.entertainment_ecosystem.mapper.AdCampaignMapper;
import com.example.modules.entertainment_ecosystem.service.AdCampaignService;
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
 * Controller for managing AdCampaign entities.
 */
@RestController
@RequestMapping("/api/adcampaigns")
public class AdCampaignController extends BaseController<AdCampaign, AdCampaignDto, AdCampaignSimpleDto> {

    public AdCampaignController(AdCampaignService adcampaignService,
                                    AdCampaignMapper adcampaignMapper) {
        super(adcampaignService, adcampaignMapper);
    }

    @GetMapping
    public ResponseEntity<Page<AdCampaignDto>> getAllAdCampaigns(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AdCampaignDto>> searchAdCampaigns(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(AdCampaign.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdCampaignDto> getAdCampaignById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<AdCampaignDto> createAdCampaign(
            @Valid @RequestBody AdCampaignDto adcampaignDto,
            UriComponentsBuilder uriBuilder) {

        AdCampaign entity = mapper.toEntity(adcampaignDto);
        AdCampaign saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/adcampaigns/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AdCampaignDto>> createAllAdCampaigns(
            @Valid @RequestBody List<AdCampaignDto> adcampaignDtoList,
            UriComponentsBuilder uriBuilder) {

        List<AdCampaign> entities = mapper.toEntityList(adcampaignDtoList);
        List<AdCampaign> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/adcampaigns").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdCampaignDto> updateAdCampaign(
            @PathVariable Long id,
            @Valid @RequestBody AdCampaignDto adcampaignDto) {

        AdCampaign entityToUpdate = mapper.toEntity(adcampaignDto);
        AdCampaign updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdCampaign(@PathVariable Long id) {
        return doDelete(id);
    }
}