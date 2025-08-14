package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ArtistDto;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.mapper.ArtistMapper;
import com.example.modules.entertainment_ecosystem.service.ArtistService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;
    private final ArtistMapper artistMapper;

    public ArtistController(ArtistService artistService,
                                    ArtistMapper artistMapper) {
        this.artistService = artistService;
        this.artistMapper = artistMapper;
    }

    @GetMapping
    public ResponseEntity<List<ArtistDto>> getAllArtists() {
        List<Artist> entities = artistService.findAll();
        return ResponseEntity.ok(artistMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDto> getArtistById(@PathVariable Long id) {
        return artistService.findById(id)
                .map(artistMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ArtistDto> createArtist(
            @Valid @RequestBody ArtistDto artistDto,
            UriComponentsBuilder uriBuilder) {

        Artist entity = artistMapper.toEntity(artistDto);
        Artist saved = artistService.save(entity);
        URI location = uriBuilder.path("/api/artists/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(artistMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ArtistDto> updateArtist(
                @PathVariable Long id,
                @Valid @RequestBody ArtistDto artistDto) {

                try {
                // Récupérer l'entité existante avec Optional
                Artist existing = artistService.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                artistMapper.updateEntityFromDto(artistDto, existing);

                // Sauvegarde
                Artist updatedEntity = artistService.save(existing);

                return ResponseEntity.ok(artistMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}