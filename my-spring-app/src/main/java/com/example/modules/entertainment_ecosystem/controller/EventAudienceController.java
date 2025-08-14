package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EventAudienceDto;
import com.example.modules.entertainment_ecosystem.model.EventAudience;
import com.example.modules.entertainment_ecosystem.mapper.EventAudienceMapper;
import com.example.modules.entertainment_ecosystem.service.EventAudienceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/eventaudiences")
public class EventAudienceController {

    private final EventAudienceService eventaudienceService;
    private final EventAudienceMapper eventaudienceMapper;

    public EventAudienceController(EventAudienceService eventaudienceService,
                                    EventAudienceMapper eventaudienceMapper) {
        this.eventaudienceService = eventaudienceService;
        this.eventaudienceMapper = eventaudienceMapper;
    }

    @GetMapping
    public ResponseEntity<List<EventAudienceDto>> getAllEventAudiences() {
        List<EventAudience> entities = eventaudienceService.findAll();
        return ResponseEntity.ok(eventaudienceMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventAudienceDto> getEventAudienceById(@PathVariable Long id) {
        return eventaudienceService.findById(id)
                .map(eventaudienceMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventAudienceDto> createEventAudience(
            @Valid @RequestBody EventAudienceDto eventaudienceDto,
            UriComponentsBuilder uriBuilder) {

        EventAudience entity = eventaudienceMapper.toEntity(eventaudienceDto);
        EventAudience saved = eventaudienceService.save(entity);
        URI location = uriBuilder.path("/api/eventaudiences/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(eventaudienceMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<EventAudienceDto> updateEventAudience(
                @PathVariable Long id,
                @Valid @RequestBody EventAudienceDto eventaudienceDto) {

                try {
                // Récupérer l'entité existante avec Optional
                EventAudience existing = eventaudienceService.findById(id)
                .orElseThrow(() -> new RuntimeException("EventAudience not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                eventaudienceMapper.updateEntityFromDto(eventaudienceDto, existing);

                // Sauvegarde
                EventAudience updatedEntity = eventaudienceService.save(existing);

                return ResponseEntity.ok(eventaudienceMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventAudience(@PathVariable Long id) {
        eventaudienceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}