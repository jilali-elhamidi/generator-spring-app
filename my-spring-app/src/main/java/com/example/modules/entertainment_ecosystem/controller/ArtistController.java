package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ArtistDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.mapper.ArtistMapper;
import com.example.modules.entertainment_ecosystem.service.ArtistService;
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
 * Controller for managing Artist entities.
 */
@RestController
@RequestMapping("/api/artists")
public class ArtistController extends BaseController<Artist, ArtistDto, ArtistSimpleDto> {

    public ArtistController(ArtistService artistService,
                                    ArtistMapper artistMapper) {
        super(artistService, artistMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ArtistDto>> getAllArtists(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ArtistDto>> searchArtists(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Artist.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDto> getArtistById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ArtistDto> createArtist(
            @Valid @RequestBody ArtistDto artistDto,
            UriComponentsBuilder uriBuilder) {

        Artist entity = mapper.toEntity(artistDto);
        Artist saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/artists/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ArtistDto>> createAllArtists(
            @Valid @RequestBody List<ArtistDto> artistDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Artist> entities = mapper.toEntityList(artistDtoList);
        List<Artist> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/artists").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDto> updateArtist(
            @PathVariable Long id,
            @Valid @RequestBody ArtistDto artistDto) {

        Artist entityToUpdate = mapper.toEntity(artistDto);
        Artist updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        return doDelete(id);
    }
}