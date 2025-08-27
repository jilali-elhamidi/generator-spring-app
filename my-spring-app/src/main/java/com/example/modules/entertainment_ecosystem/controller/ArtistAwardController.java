package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ArtistAwardDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistAwardSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ArtistAward;
import com.example.modules.entertainment_ecosystem.mapper.ArtistAwardMapper;
import com.example.modules.entertainment_ecosystem.service.ArtistAwardService;
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
 * Controller for managing ArtistAward entities.
 */
@RestController
@RequestMapping("/api/artistawards")
public class ArtistAwardController extends BaseController<ArtistAward, ArtistAwardDto, ArtistAwardSimpleDto> {

    public ArtistAwardController(ArtistAwardService artistawardService,
                                    ArtistAwardMapper artistawardMapper) {
        super(artistawardService, artistawardMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ArtistAwardDto>> getAllArtistAwards(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ArtistAwardDto>> searchArtistAwards(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ArtistAward.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistAwardDto> getArtistAwardById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ArtistAwardDto> createArtistAward(
            @Valid @RequestBody ArtistAwardDto artistawardDto,
            UriComponentsBuilder uriBuilder) {

        ArtistAward entity = mapper.toEntity(artistawardDto);
        ArtistAward saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/artistawards/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ArtistAwardDto>> createAllArtistAwards(
            @Valid @RequestBody List<ArtistAwardDto> artistawardDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ArtistAward> entities = mapper.toEntityList(artistawardDtoList);
        List<ArtistAward> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/artistawards").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistAwardDto> updateArtistAward(
            @PathVariable Long id,
            @Valid @RequestBody ArtistAwardDto artistawardDto) {

        ArtistAward entityToUpdate = mapper.toEntity(artistawardDto);
        ArtistAward updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtistAward(@PathVariable Long id) {
        return doDelete(id);
    }
}