package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PodcastEpisodeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PodcastEpisodeSimpleDto;
import com.example.modules.entertainment_ecosystem.model.PodcastEpisode;
import com.example.modules.entertainment_ecosystem.mapper.PodcastEpisodeMapper;
import com.example.modules.entertainment_ecosystem.service.PodcastEpisodeService;
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
 * Controller for managing PodcastEpisode entities.
 */
@RestController
@RequestMapping("/api/podcastepisodes")
public class PodcastEpisodeController extends BaseController<PodcastEpisode, PodcastEpisodeDto, PodcastEpisodeSimpleDto> {

    public PodcastEpisodeController(PodcastEpisodeService podcastepisodeService,
                                    PodcastEpisodeMapper podcastepisodeMapper) {
        super(podcastepisodeService, podcastepisodeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<PodcastEpisodeDto>> getAllPodcastEpisodes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PodcastEpisodeDto>> searchPodcastEpisodes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(PodcastEpisode.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodcastEpisodeDto> getPodcastEpisodeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<PodcastEpisodeDto> createPodcastEpisode(
            @Valid @RequestBody PodcastEpisodeDto podcastepisodeDto,
            UriComponentsBuilder uriBuilder) {

        PodcastEpisode entity = mapper.toEntity(podcastepisodeDto);
        PodcastEpisode saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/podcastepisodes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PodcastEpisodeDto>> createAllPodcastEpisodes(
            @Valid @RequestBody List<PodcastEpisodeDto> podcastepisodeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<PodcastEpisode> entities = mapper.toEntityList(podcastepisodeDtoList);
        List<PodcastEpisode> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/podcastepisodes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PodcastEpisodeDto> updatePodcastEpisode(
            @PathVariable Long id,
            @Valid @RequestBody PodcastEpisodeDto podcastepisodeDto) {

        PodcastEpisode entityToUpdate = mapper.toEntity(podcastepisodeDto);
        PodcastEpisode updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePodcastEpisode(@PathVariable Long id) {
        return doDelete(id);
    }
}