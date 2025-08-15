package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EventSponsorshipDto;
import com.example.modules.entertainment_ecosystem.model.EventSponsorship;
import com.example.modules.entertainment_ecosystem.mapper.EventSponsorshipMapper;
import com.example.modules.entertainment_ecosystem.service.EventSponsorshipService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/eventsponsorships")
public class EventSponsorshipController {

    private final EventSponsorshipService eventsponsorshipService;
    private final EventSponsorshipMapper eventsponsorshipMapper;

    public EventSponsorshipController(EventSponsorshipService eventsponsorshipService,
                                    EventSponsorshipMapper eventsponsorshipMapper) {
        this.eventsponsorshipService = eventsponsorshipService;
        this.eventsponsorshipMapper = eventsponsorshipMapper;
    }

    @GetMapping
    public ResponseEntity<List<EventSponsorshipDto>> getAllEventSponsorships() {
        List<EventSponsorship> entities = eventsponsorshipService.findAll();
        return ResponseEntity.ok(eventsponsorshipMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventSponsorshipDto> getEventSponsorshipById(@PathVariable Long id) {
        return eventsponsorshipService.findById(id)
                .map(eventsponsorshipMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventSponsorshipDto> createEventSponsorship(
            @Valid @RequestBody EventSponsorshipDto eventsponsorshipDto,
            UriComponentsBuilder uriBuilder) {

        EventSponsorship entity = eventsponsorshipMapper.toEntity(eventsponsorshipDto);
        EventSponsorship saved = eventsponsorshipService.save(entity);
        URI location = uriBuilder.path("/api/eventsponsorships/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(eventsponsorshipMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<EventSponsorshipDto> updateEventSponsorship(
                @PathVariable Long id,
                @RequestBody EventSponsorshipDto eventsponsorshipDto) {

                // Transformer le DTO en entity pour le service
                EventSponsorship entityToUpdate = eventsponsorshipMapper.toEntity(eventsponsorshipDto);

                // Appel du service update
                EventSponsorship updatedEntity = eventsponsorshipService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                EventSponsorshipDto updatedDto = eventsponsorshipMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventSponsorship(@PathVariable Long id) {
        eventsponsorshipService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}