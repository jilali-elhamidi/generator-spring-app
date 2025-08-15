package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EventTypeDto;
import com.example.modules.entertainment_ecosystem.model.EventType;
import com.example.modules.entertainment_ecosystem.mapper.EventTypeMapper;
import com.example.modules.entertainment_ecosystem.service.EventTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/eventtypes")
public class EventTypeController {

    private final EventTypeService eventtypeService;
    private final EventTypeMapper eventtypeMapper;

    public EventTypeController(EventTypeService eventtypeService,
                                    EventTypeMapper eventtypeMapper) {
        this.eventtypeService = eventtypeService;
        this.eventtypeMapper = eventtypeMapper;
    }

    @GetMapping
    public ResponseEntity<List<EventTypeDto>> getAllEventTypes() {
        List<EventType> entities = eventtypeService.findAll();
        return ResponseEntity.ok(eventtypeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventTypeDto> getEventTypeById(@PathVariable Long id) {
        return eventtypeService.findById(id)
                .map(eventtypeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventTypeDto> createEventType(
            @Valid @RequestBody EventTypeDto eventtypeDto,
            UriComponentsBuilder uriBuilder) {

        EventType entity = eventtypeMapper.toEntity(eventtypeDto);
        EventType saved = eventtypeService.save(entity);
        URI location = uriBuilder.path("/api/eventtypes/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(eventtypeMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<EventTypeDto> updateEventType(
                @PathVariable Long id,
                @RequestBody EventTypeDto eventtypeDto) {

                // Transformer le DTO en entity pour le service
                EventType entityToUpdate = eventtypeMapper.toEntity(eventtypeDto);

                // Appel du service update
                EventType updatedEntity = eventtypeService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                EventTypeDto updatedDto = eventtypeMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteEventType(@PathVariable Long id) {
                    boolean deleted = eventtypeService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}