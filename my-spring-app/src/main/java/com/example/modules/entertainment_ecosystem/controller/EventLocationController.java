package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EventLocationDto;
import com.example.modules.entertainment_ecosystem.model.EventLocation;
import com.example.modules.entertainment_ecosystem.mapper.EventLocationMapper;
import com.example.modules.entertainment_ecosystem.service.EventLocationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/eventlocations")
public class EventLocationController {

    private final EventLocationService eventlocationService;
    private final EventLocationMapper eventlocationMapper;

    public EventLocationController(EventLocationService eventlocationService,
                                    EventLocationMapper eventlocationMapper) {
        this.eventlocationService = eventlocationService;
        this.eventlocationMapper = eventlocationMapper;
    }

    @GetMapping
    public ResponseEntity<List<EventLocationDto>> getAllEventLocations() {
        List<EventLocation> entities = eventlocationService.findAll();
        return ResponseEntity.ok(eventlocationMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventLocationDto> getEventLocationById(@PathVariable Long id) {
        return eventlocationService.findById(id)
                .map(eventlocationMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventLocationDto> createEventLocation(
            @Valid @RequestBody EventLocationDto eventlocationDto,
            UriComponentsBuilder uriBuilder) {

        EventLocation entity = eventlocationMapper.toEntity(eventlocationDto);
        EventLocation saved = eventlocationService.save(entity);

        URI location = uriBuilder
                                .path("/api/eventlocations/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(eventlocationMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventLocationDto> updateEventLocation(
            @PathVariable Long id,
            @Valid @RequestBody EventLocationDto eventlocationDto) {


        EventLocation entityToUpdate = eventlocationMapper.toEntity(eventlocationDto);
        EventLocation updatedEntity = eventlocationService.update(id, entityToUpdate);

        return ResponseEntity.ok(eventlocationMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventLocation(@PathVariable Long id) {
        boolean deleted = eventlocationService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}