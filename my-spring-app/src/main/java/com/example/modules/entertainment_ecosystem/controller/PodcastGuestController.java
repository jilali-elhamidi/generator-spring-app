package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.PodcastGuestDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PodcastGuestSimpleDto;
import com.example.modules.entertainment_ecosystem.model.PodcastGuest;
import com.example.modules.entertainment_ecosystem.mapper.PodcastGuestMapper;
import com.example.modules.entertainment_ecosystem.service.PodcastGuestService;
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
 * Controller for managing PodcastGuest entities.
 */
@RestController
@RequestMapping("/api/podcastguests")
public class PodcastGuestController extends BaseController<PodcastGuest, PodcastGuestDto, PodcastGuestSimpleDto> {

    public PodcastGuestController(PodcastGuestService podcastguestService,
                                    PodcastGuestMapper podcastguestMapper) {
        super(podcastguestService, podcastguestMapper);
    }

    @GetMapping
    public ResponseEntity<Page<PodcastGuestDto>> getAllPodcastGuests(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PodcastGuestDto>> searchPodcastGuests(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(PodcastGuest.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodcastGuestDto> getPodcastGuestById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<PodcastGuestDto> createPodcastGuest(
            @Valid @RequestBody PodcastGuestDto podcastguestDto,
            UriComponentsBuilder uriBuilder) {

        PodcastGuest entity = mapper.toEntity(podcastguestDto);
        PodcastGuest saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/podcastguests/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PodcastGuestDto>> createAllPodcastGuests(
            @Valid @RequestBody List<PodcastGuestDto> podcastguestDtoList,
            UriComponentsBuilder uriBuilder) {

        List<PodcastGuest> entities = mapper.toEntityList(podcastguestDtoList);
        List<PodcastGuest> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/podcastguests").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PodcastGuestDto> updatePodcastGuest(
            @PathVariable Long id,
            @Valid @RequestBody PodcastGuestDto podcastguestDto) {

        PodcastGuest entityToUpdate = mapper.toEntity(podcastguestDto);
        PodcastGuest updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePodcastGuest(@PathVariable Long id) {
        return doDelete(id);
    }
}