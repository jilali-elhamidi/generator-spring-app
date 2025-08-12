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
        URI location = uriBuilder.path("/api/podcasts/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(podcastMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PodcastDto> updatePodcast(
            @PathVariable Long id,
            @Valid @RequestBody PodcastDto podcastDto) {

        try {
            Podcast updatedEntity = podcastService.update(
                    id,
                    podcastMapper.toEntity(podcastDto)
            );
            return ResponseEntity.ok(podcastMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePodcast(@PathVariable Long id) {
        podcastService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}