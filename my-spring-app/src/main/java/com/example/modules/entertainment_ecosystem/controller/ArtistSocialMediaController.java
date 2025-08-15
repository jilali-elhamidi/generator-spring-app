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
                @RequestBody ArtistSocialMediaDto artistsocialmediaDto) {

                // Transformer le DTO en entity pour le service
                ArtistSocialMedia entityToUpdate = artistsocialmediaMapper.toEntity(artistsocialmediaDto);

                // Appel du service update
                ArtistSocialMedia updatedEntity = artistsocialmediaService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                ArtistSocialMediaDto updatedDto = artistsocialmediaMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteArtistSocialMedia(@PathVariable Long id) {
                    boolean deleted = artistsocialmediaService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}