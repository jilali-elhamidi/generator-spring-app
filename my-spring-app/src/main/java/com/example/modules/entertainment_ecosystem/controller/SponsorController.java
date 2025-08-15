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
        URI location = uriBuilder.path("/api/sponsors/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(sponsorMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<SponsorDto> updateSponsor(
                @PathVariable Long id,
                @RequestBody SponsorDto sponsorDto) {

                // Transformer le DTO en entity pour le service
                Sponsor entityToUpdate = sponsorMapper.toEntity(sponsorDto);

                // Appel du service update
                Sponsor updatedEntity = sponsorService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                SponsorDto updatedDto = sponsorMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSponsor(@PathVariable Long id) {
        sponsorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}