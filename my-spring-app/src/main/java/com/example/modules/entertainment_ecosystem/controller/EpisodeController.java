package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EpisodeDto;
import com.example.modules.entertainment_ecosystem.model.Episode;
import com.example.modules.entertainment_ecosystem.mapper.EpisodeMapper;
import com.example.modules.entertainment_ecosystem.service.EpisodeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/episodes")
public class EpisodeController {

    private final EpisodeService episodeService;
    private final EpisodeMapper episodeMapper;

    public EpisodeController(EpisodeService episodeService,
                                    EpisodeMapper episodeMapper) {
        this.episodeService = episodeService;
        this.episodeMapper = episodeMapper;
    }

    @GetMapping
    public ResponseEntity<List<EpisodeDto>> getAllEpisodes() {
        List<Episode> entities = episodeService.findAll();
        return ResponseEntity.ok(episodeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeDto> getEpisodeById(@PathVariable Long id) {
        return episodeService.findById(id)
                .map(episodeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EpisodeDto> createEpisode(
            @Valid @RequestBody EpisodeDto episodeDto,
            UriComponentsBuilder uriBuilder) {

        Episode entity = episodeMapper.toEntity(episodeDto);
        Episode saved = episodeService.save(entity);
        URI location = uriBuilder.path("/api/episodes/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(episodeMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<EpisodeDto> updateEpisode(
                @PathVariable Long id,
                @RequestBody EpisodeDto episodeDto) {

                // Transformer le DTO en entity pour le service
                Episode entityToUpdate = episodeMapper.toEntity(episodeDto);

                // Appel du service update
                Episode updatedEntity = episodeService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                EpisodeDto updatedDto = episodeMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteEpisode(@PathVariable Long id) {
                    boolean deleted = episodeService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}