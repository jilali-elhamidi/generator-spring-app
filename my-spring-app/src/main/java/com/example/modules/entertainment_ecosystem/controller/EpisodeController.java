package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EpisodeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EpisodeSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Episode;
import com.example.modules.entertainment_ecosystem.mapper.EpisodeMapper;
import com.example.modules.entertainment_ecosystem.service.EpisodeService;
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
 * Controller for managing Episode entities.
 */
@RestController
@RequestMapping("/api/episodes")
public class EpisodeController extends BaseController<Episode, EpisodeDto, EpisodeSimpleDto> {

    public EpisodeController(EpisodeService episodeService,
                                    EpisodeMapper episodeMapper) {
        super(episodeService, episodeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<EpisodeDto>> getAllEpisodes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EpisodeDto>> searchEpisodes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Episode.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeDto> getEpisodeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<EpisodeDto> createEpisode(
            @Valid @RequestBody EpisodeDto episodeDto,
            UriComponentsBuilder uriBuilder) {

        Episode entity = mapper.toEntity(episodeDto);
        Episode saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/episodes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EpisodeDto>> createAllEpisodes(
            @Valid @RequestBody List<EpisodeDto> episodeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Episode> entities = mapper.toEntityList(episodeDtoList);
        List<Episode> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/episodes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EpisodeDto> updateEpisode(
            @PathVariable Long id,
            @Valid @RequestBody EpisodeDto episodeDto) {

        Episode entityToUpdate = mapper.toEntity(episodeDto);
        Episode updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpisode(@PathVariable Long id) {
        return doDelete(id);
    }
}