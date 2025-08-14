package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.LiveEventDto;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.mapper.LiveEventMapper;
import com.example.modules.entertainment_ecosystem.service.LiveEventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/liveevents")
public class LiveEventController {

    private final LiveEventService liveeventService;
    private final LiveEventMapper liveeventMapper;

    public LiveEventController(LiveEventService liveeventService,
                                    LiveEventMapper liveeventMapper) {
        this.liveeventService = liveeventService;
        this.liveeventMapper = liveeventMapper;
    }

    @GetMapping
    public ResponseEntity<List<LiveEventDto>> getAllLiveEvents() {
        List<LiveEvent> entities = liveeventService.findAll();
        return ResponseEntity.ok(liveeventMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiveEventDto> getLiveEventById(@PathVariable Long id) {
        return liveeventService.findById(id)
                .map(liveeventMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LiveEventDto> createLiveEvent(
            @Valid @RequestBody LiveEventDto liveeventDto,
            UriComponentsBuilder uriBuilder) {

        LiveEvent entity = liveeventMapper.toEntity(liveeventDto);
        LiveEvent saved = liveeventService.save(entity);
        URI location = uriBuilder.path("/api/liveevents/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(liveeventMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<LiveEventDto> updateLiveEvent(
                @PathVariable Long id,
                @Valid @RequestBody LiveEventDto liveeventDto) {

                try {
                // Récupérer l'entité existante avec Optional
                LiveEvent existing = liveeventService.findById(id)
                .orElseThrow(() -> new RuntimeException("LiveEvent not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                liveeventMapper.updateEntityFromDto(liveeventDto, existing);

                // Sauvegarde
                LiveEvent updatedEntity = liveeventService.save(existing);

                return ResponseEntity.ok(liveeventMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLiveEvent(@PathVariable Long id) {
        liveeventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}