package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PodcastDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PodcastSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.mapper.PodcastMapper;
import com.example.modules.entertainment_ecosystem.service.PodcastService;
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
 * Controller for managing Podcast entities.
 */
@RestController
@RequestMapping("/api/podcasts")
public class PodcastController extends BaseController<Podcast, PodcastDto, PodcastSimpleDto> {

    public PodcastController(PodcastService podcastService,
                                    PodcastMapper podcastMapper) {
        super(podcastService, podcastMapper);
    }

    @GetMapping
    public ResponseEntity<Page<PodcastDto>> getAllPodcasts(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PodcastDto>> searchPodcasts(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Podcast.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodcastDto> getPodcastById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<PodcastDto> createPodcast(
            @Valid @RequestBody PodcastDto podcastDto,
            UriComponentsBuilder uriBuilder) {

        Podcast entity = mapper.toEntity(podcastDto);
        Podcast saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/podcasts/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PodcastDto>> createAllPodcasts(
            @Valid @RequestBody List<PodcastDto> podcastDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Podcast> entities = mapper.toEntityList(podcastDtoList);
        List<Podcast> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/podcasts").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PodcastDto> updatePodcast(
            @PathVariable Long id,
            @Valid @RequestBody PodcastDto podcastDto) {

        Podcast entityToUpdate = mapper.toEntity(podcastDto);
        Podcast updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePodcast(@PathVariable Long id) {
        return doDelete(id);
    }
}