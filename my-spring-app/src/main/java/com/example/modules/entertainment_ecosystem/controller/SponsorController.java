package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.SponsorDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SponsorSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Sponsor;
import com.example.modules.entertainment_ecosystem.mapper.SponsorMapper;
import com.example.modules.entertainment_ecosystem.service.SponsorService;
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
 * Controller for managing Sponsor entities.
 */
@RestController
@RequestMapping("/api/sponsors")
public class SponsorController extends BaseController<Sponsor, SponsorDto, SponsorSimpleDto> {

    public SponsorController(SponsorService sponsorService,
                                    SponsorMapper sponsorMapper) {
        super(sponsorService, sponsorMapper);
    }

    @GetMapping
    public ResponseEntity<Page<SponsorDto>> getAllSponsors(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SponsorDto>> searchSponsors(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Sponsor.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SponsorDto> getSponsorById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<SponsorDto> createSponsor(
            @Valid @RequestBody SponsorDto sponsorDto,
            UriComponentsBuilder uriBuilder) {

        Sponsor entity = mapper.toEntity(sponsorDto);
        Sponsor saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/sponsors/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<SponsorDto>> createAllSponsors(
            @Valid @RequestBody List<SponsorDto> sponsorDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Sponsor> entities = mapper.toEntityList(sponsorDtoList);
        List<Sponsor> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/sponsors").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SponsorDto> updateSponsor(
            @PathVariable Long id,
            @Valid @RequestBody SponsorDto sponsorDto) {

        Sponsor entityToUpdate = mapper.toEntity(sponsorDto);
        Sponsor updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSponsor(@PathVariable Long id) {
        return doDelete(id);
    }
}