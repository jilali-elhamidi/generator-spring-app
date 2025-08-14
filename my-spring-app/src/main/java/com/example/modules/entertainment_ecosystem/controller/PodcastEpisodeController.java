package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PodcastEpisodeDto;
import com.example.modules.entertainment_ecosystem.model.PodcastEpisode;
import com.example.modules.entertainment_ecosystem.mapper.PodcastEpisodeMapper;
import com.example.modules.entertainment_ecosystem.service.PodcastEpisodeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/podcastepisodes")
public class PodcastEpisodeController {

    private final PodcastEpisodeService podcastepisodeService;
    private final PodcastEpisodeMapper podcastepisodeMapper;

    public PodcastEpisodeController(PodcastEpisodeService podcastepisodeService,
                                    PodcastEpisodeMapper podcastepisodeMapper) {
        this.podcastepisodeService = podcastepisodeService;
        this.podcastepisodeMapper = podcastepisodeMapper;
    }

    @GetMapping
    public ResponseEntity<List<PodcastEpisodeDto>> getAllPodcastEpisodes() {
        List<PodcastEpisode> entities = podcastepisodeService.findAll();
        return ResponseEntity.ok(podcastepisodeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodcastEpisodeDto> getPodcastEpisodeById(@PathVariable Long id) {
        return podcastepisodeService.findById(id)
                .map(podcastepisodeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PodcastEpisodeDto> createPodcastEpisode(
            @Valid @RequestBody PodcastEpisodeDto podcastepisodeDto,
            UriComponentsBuilder uriBuilder) {

        PodcastEpisode entity = podcastepisodeMapper.toEntity(podcastepisodeDto);
        PodcastEpisode saved = podcastepisodeService.save(entity);
        URI location = uriBuilder.path("/api/podcastepisodes/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(podcastepisodeMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<PodcastEpisodeDto> updatePodcastEpisode(
                @PathVariable Long id,
                @Valid @RequestBody PodcastEpisodeDto podcastepisodeDto) {

                try {
                // Récupérer l'entité existante avec Optional
                PodcastEpisode existing = podcastepisodeService.findById(id)
                .orElseThrow(() -> new RuntimeException("PodcastEpisode not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                podcastepisodeMapper.updateEntityFromDto(podcastepisodeDto, existing);

                // Sauvegarde
                PodcastEpisode updatedEntity = podcastepisodeService.save(existing);

                return ResponseEntity.ok(podcastepisodeMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePodcastEpisode(@PathVariable Long id) {
        podcastepisodeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}