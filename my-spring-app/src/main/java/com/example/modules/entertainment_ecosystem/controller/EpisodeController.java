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
            @Valid @RequestBody EpisodeDto episodeDto) {

        try {
            Episode updatedEntity = episodeService.update(
                    id,
                    episodeMapper.toEntity(episodeDto)
            );
            return ResponseEntity.ok(episodeMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpisode(@PathVariable Long id) {
        episodeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}