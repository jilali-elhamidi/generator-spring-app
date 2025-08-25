package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.SponsorDto;
import com.example.modules.entertainment_ecosystem.model.Sponsor;
import com.example.modules.entertainment_ecosystem.mapper.SponsorMapper;
import com.example.modules.entertainment_ecosystem.service.SponsorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sponsors")
public class SponsorController {

    private final SponsorService sponsorService;
    private final SponsorMapper sponsorMapper;

    public SponsorController(SponsorService sponsorService,
                                    SponsorMapper sponsorMapper) {
        this.sponsorService = sponsorService;
        this.sponsorMapper = sponsorMapper;
    }

    @GetMapping
    public ResponseEntity<List<SponsorDto>> getAllSponsors() {
        List<Sponsor> entities = sponsorService.findAll();
        return ResponseEntity.ok(sponsorMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SponsorDto> getSponsorById(@PathVariable Long id) {
        return sponsorService.findById(id)
                .map(sponsorMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SponsorDto> createSponsor(
            @Valid @RequestBody SponsorDto sponsorDto,
            UriComponentsBuilder uriBuilder) {

        Sponsor entity = sponsorMapper.toEntity(sponsorDto);
        Sponsor saved = sponsorService.save(entity);

        URI location = uriBuilder
                                .path("/api/sponsors/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(sponsorMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<SponsorDto>> createAllSponsors(
            @Valid @RequestBody List<SponsorDto> sponsorDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Sponsor> entities = sponsorMapper.toEntityList(sponsorDtoList);
        List<Sponsor> savedEntities = sponsorService.saveAll(entities);

        URI location = uriBuilder.path("/api/sponsors").build().toUri();

        return ResponseEntity.created(location).body(sponsorMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SponsorDto> updateSponsor(
            @PathVariable Long id,
            @Valid @RequestBody SponsorDto sponsorDto) {


        Sponsor entityToUpdate = sponsorMapper.toEntity(sponsorDto);
        Sponsor updatedEntity = sponsorService.update(id, entityToUpdate);

        return ResponseEntity.ok(sponsorMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSponsor(@PathVariable Long id) {
        boolean deleted = sponsorService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}