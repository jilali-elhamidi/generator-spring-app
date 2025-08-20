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
                @RequestBody PodcastEpisodeDto podcastepisodeDto) {

                // Transformer le DTO en entity pour le service
                PodcastEpisode entityToUpdate = podcastepisodeMapper.toEntity(podcastepisodeDto);

                // Appel du service update
                PodcastEpisode updatedEntity = podcastepisodeService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                PodcastEpisodeDto updatedDto = podcastepisodeMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deletePodcastEpisode(@PathVariable Long id) {
                    boolean deleted = podcastepisodeService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}