package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PodcastGuestDto;
import com.example.modules.entertainment_ecosystem.model.PodcastGuest;
import com.example.modules.entertainment_ecosystem.mapper.PodcastGuestMapper;
import com.example.modules.entertainment_ecosystem.service.PodcastGuestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/podcastguests")
public class PodcastGuestController {

    private final PodcastGuestService podcastguestService;
    private final PodcastGuestMapper podcastguestMapper;

    public PodcastGuestController(PodcastGuestService podcastguestService,
                                    PodcastGuestMapper podcastguestMapper) {
        this.podcastguestService = podcastguestService;
        this.podcastguestMapper = podcastguestMapper;
    }

    @GetMapping
    public ResponseEntity<List<PodcastGuestDto>> getAllPodcastGuests() {
        List<PodcastGuest> entities = podcastguestService.findAll();
        return ResponseEntity.ok(podcastguestMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodcastGuestDto> getPodcastGuestById(@PathVariable Long id) {
        return podcastguestService.findById(id)
                .map(podcastguestMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PodcastGuestDto> createPodcastGuest(
            @Valid @RequestBody PodcastGuestDto podcastguestDto,
            UriComponentsBuilder uriBuilder) {

        PodcastGuest entity = podcastguestMapper.toEntity(podcastguestDto);
        PodcastGuest saved = podcastguestService.save(entity);
        URI location = uriBuilder.path("/api/podcastguests/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(podcastguestMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<PodcastGuestDto> updatePodcastGuest(
                @PathVariable Long id,
                @Valid @RequestBody PodcastGuestDto podcastguestDto) {

                try {
                // Récupérer l'entité existante avec Optional
                PodcastGuest existing = podcastguestService.findById(id)
                .orElseThrow(() -> new RuntimeException("PodcastGuest not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                podcastguestMapper.updateEntityFromDto(podcastguestDto, existing);

                // Sauvegarde
                PodcastGuest updatedEntity = podcastguestService.save(existing);

                return ResponseEntity.ok(podcastguestMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePodcastGuest(@PathVariable Long id) {
        podcastguestService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}