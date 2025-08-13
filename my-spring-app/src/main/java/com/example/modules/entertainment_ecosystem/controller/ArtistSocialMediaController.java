package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ArtistSocialMediaDto;
import com.example.modules.entertainment_ecosystem.model.ArtistSocialMedia;
import com.example.modules.entertainment_ecosystem.mapper.ArtistSocialMediaMapper;
import com.example.modules.entertainment_ecosystem.service.ArtistSocialMediaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/artistsocialmedias")
public class ArtistSocialMediaController {

    private final ArtistSocialMediaService artistsocialmediaService;
    private final ArtistSocialMediaMapper artistsocialmediaMapper;

    public ArtistSocialMediaController(ArtistSocialMediaService artistsocialmediaService,
                                    ArtistSocialMediaMapper artistsocialmediaMapper) {
        this.artistsocialmediaService = artistsocialmediaService;
        this.artistsocialmediaMapper = artistsocialmediaMapper;
    }

    @GetMapping
    public ResponseEntity<List<ArtistSocialMediaDto>> getAllArtistSocialMedias() {
        List<ArtistSocialMedia> entities = artistsocialmediaService.findAll();
        return ResponseEntity.ok(artistsocialmediaMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistSocialMediaDto> getArtistSocialMediaById(@PathVariable Long id) {
        return artistsocialmediaService.findById(id)
                .map(artistsocialmediaMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ArtistSocialMediaDto> createArtistSocialMedia(
            @Valid @RequestBody ArtistSocialMediaDto artistsocialmediaDto,
            UriComponentsBuilder uriBuilder) {

        ArtistSocialMedia entity = artistsocialmediaMapper.toEntity(artistsocialmediaDto);
        ArtistSocialMedia saved = artistsocialmediaService.save(entity);
        URI location = uriBuilder.path("/api/artistsocialmedias/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(artistsocialmediaMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistSocialMediaDto> updateArtistSocialMedia(
            @PathVariable Long id,
            @Valid @RequestBody ArtistSocialMediaDto artistsocialmediaDto) {

        try {
            ArtistSocialMedia updatedEntity = artistsocialmediaService.update(
                    id,
                    artistsocialmediaMapper.toEntity(artistsocialmediaDto)
            );
            return ResponseEntity.ok(artistsocialmediaMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtistSocialMedia(@PathVariable Long id) {
        artistsocialmediaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}