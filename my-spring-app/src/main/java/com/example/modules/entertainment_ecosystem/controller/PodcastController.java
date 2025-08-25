package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PodcastDto;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.mapper.PodcastMapper;
import com.example.modules.entertainment_ecosystem.service.PodcastService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/podcasts")
public class PodcastController {

    private final PodcastService podcastService;
    private final PodcastMapper podcastMapper;

    public PodcastController(PodcastService podcastService,
                                    PodcastMapper podcastMapper) {
        this.podcastService = podcastService;
        this.podcastMapper = podcastMapper;
    }

    @GetMapping
    public ResponseEntity<List<PodcastDto>> getAllPodcasts() {
        List<Podcast> entities = podcastService.findAll();
        return ResponseEntity.ok(podcastMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodcastDto> getPodcastById(@PathVariable Long id) {
        return podcastService.findById(id)
                .map(podcastMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PodcastDto> createPodcast(
            @Valid @RequestBody PodcastDto podcastDto,
            UriComponentsBuilder uriBuilder) {

        Podcast entity = podcastMapper.toEntity(podcastDto);
        Podcast saved = podcastService.save(entity);

        URI location = uriBuilder
                                .path("/api/podcasts/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(podcastMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PodcastDto>> createAllPodcasts(
            @Valid @RequestBody List<PodcastDto> podcastDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Podcast> entities = podcastMapper.toEntityList(podcastDtoList);
        List<Podcast> savedEntities = podcastService.saveAll(entities);

        URI location = uriBuilder.path("/api/podcasts").build().toUri();

        return ResponseEntity.created(location).body(podcastMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PodcastDto> updatePodcast(
            @PathVariable Long id,
            @Valid @RequestBody PodcastDto podcastDto) {


        Podcast entityToUpdate = podcastMapper.toEntity(podcastDto);
        Podcast updatedEntity = podcastService.update(id, entityToUpdate);

        return ResponseEntity.ok(podcastMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePodcast(@PathVariable Long id) {
        boolean deleted = podcastService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}