package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ArtistSocialMediaDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSocialMediaSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ArtistSocialMedia;
import com.example.modules.entertainment_ecosystem.mapper.ArtistSocialMediaMapper;
import com.example.modules.entertainment_ecosystem.service.ArtistSocialMediaService;
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
 * Controller for managing ArtistSocialMedia entities.
 */
@RestController
@RequestMapping("/api/artistsocialmedias")
public class ArtistSocialMediaController extends BaseController<ArtistSocialMedia, ArtistSocialMediaDto, ArtistSocialMediaSimpleDto> {

    public ArtistSocialMediaController(ArtistSocialMediaService artistsocialmediaService,
                                    ArtistSocialMediaMapper artistsocialmediaMapper) {
        super(artistsocialmediaService, artistsocialmediaMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ArtistSocialMediaDto>> getAllArtistSocialMedias(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ArtistSocialMediaDto>> searchArtistSocialMedias(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ArtistSocialMedia.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistSocialMediaDto> getArtistSocialMediaById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ArtistSocialMediaDto> createArtistSocialMedia(
            @Valid @RequestBody ArtistSocialMediaDto artistsocialmediaDto,
            UriComponentsBuilder uriBuilder) {

        ArtistSocialMedia entity = mapper.toEntity(artistsocialmediaDto);
        ArtistSocialMedia saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/artistsocialmedias/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ArtistSocialMediaDto>> createAllArtistSocialMedias(
            @Valid @RequestBody List<ArtistSocialMediaDto> artistsocialmediaDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ArtistSocialMedia> entities = mapper.toEntityList(artistsocialmediaDtoList);
        List<ArtistSocialMedia> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/artistsocialmedias").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistSocialMediaDto> updateArtistSocialMedia(
            @PathVariable Long id,
            @Valid @RequestBody ArtistSocialMediaDto artistsocialmediaDto) {

        ArtistSocialMedia entityToUpdate = mapper.toEntity(artistsocialmediaDto);
        ArtistSocialMedia updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtistSocialMedia(@PathVariable Long id) {
        return doDelete(id);
    }
}