package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ArtistAwardDto;
import com.example.modules.entertainment_ecosystem.model.ArtistAward;
import com.example.modules.entertainment_ecosystem.mapper.ArtistAwardMapper;
import com.example.modules.entertainment_ecosystem.service.ArtistAwardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/artistawards")
public class ArtistAwardController {

    private final ArtistAwardService artistawardService;
    private final ArtistAwardMapper artistawardMapper;

    public ArtistAwardController(ArtistAwardService artistawardService,
                                    ArtistAwardMapper artistawardMapper) {
        this.artistawardService = artistawardService;
        this.artistawardMapper = artistawardMapper;
    }

    @GetMapping
    public ResponseEntity<List<ArtistAwardDto>> getAllArtistAwards() {
        List<ArtistAward> entities = artistawardService.findAll();
        return ResponseEntity.ok(artistawardMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistAwardDto> getArtistAwardById(@PathVariable Long id) {
        return artistawardService.findById(id)
                .map(artistawardMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ArtistAwardDto> createArtistAward(
            @Valid @RequestBody ArtistAwardDto artistawardDto,
            UriComponentsBuilder uriBuilder) {

        ArtistAward entity = artistawardMapper.toEntity(artistawardDto);
        ArtistAward saved = artistawardService.save(entity);
        URI location = uriBuilder.path("/api/artistawards/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(artistawardMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ArtistAwardDto> updateArtistAward(
                @PathVariable Long id,
                @Valid @RequestBody ArtistAwardDto artistawardDto) {

                try {
                // Récupérer l'entité existante avec Optional
                ArtistAward existing = artistawardService.findById(id)
                .orElseThrow(() -> new RuntimeException("ArtistAward not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                artistawardMapper.updateEntityFromDto(artistawardDto, existing);

                // Sauvegarde
                ArtistAward updatedEntity = artistawardService.save(existing);

                return ResponseEntity.ok(artistawardMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtistAward(@PathVariable Long id) {
        artistawardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}