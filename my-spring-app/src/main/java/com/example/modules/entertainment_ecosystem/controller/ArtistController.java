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

        URI location = uriBuilder
                                .path("/api/artists/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(artistMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDto> updateArtist(
            @PathVariable Long id,
            @Valid @RequestBody ArtistDto artistDto) {


        Artist entityToUpdate = artistMapper.toEntity(artistDto);
        Artist updatedEntity = artistService.update(id, entityToUpdate);

        return ResponseEntity.ok(artistMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        boolean deleted = artistService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}