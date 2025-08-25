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

        URI location = uriBuilder
                                .path("/api/eventtypes/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(eventtypeMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EventTypeDto>> createAllEventTypes(
            @Valid @RequestBody List<EventTypeDto> eventtypeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<EventType> entities = eventtypeMapper.toEntityList(eventtypeDtoList);
        List<EventType> savedEntities = eventtypeService.saveAll(entities);

        URI location = uriBuilder.path("/api/eventtypes").build().toUri();

        return ResponseEntity.created(location).body(eventtypeMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventTypeDto> updateEventType(
            @PathVariable Long id,
            @Valid @RequestBody EventTypeDto eventtypeDto) {


        EventType entityToUpdate = eventtypeMapper.toEntity(eventtypeDto);
        EventType updatedEntity = eventtypeService.update(id, entityToUpdate);

        return ResponseEntity.ok(eventtypeMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventType(@PathVariable Long id) {
        boolean deleted = eventtypeService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}