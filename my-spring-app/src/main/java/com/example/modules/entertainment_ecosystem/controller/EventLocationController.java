package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EventLocationDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventLocationSimpleDto;
import com.example.modules.entertainment_ecosystem.model.EventLocation;
import com.example.modules.entertainment_ecosystem.mapper.EventLocationMapper;
import com.example.modules.entertainment_ecosystem.service.EventLocationService;
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
 * Controller for managing EventLocation entities.
 */
@RestController
@RequestMapping("/api/eventlocations")
public class EventLocationController extends BaseController<EventLocation, EventLocationDto, EventLocationSimpleDto> {

    public EventLocationController(EventLocationService eventlocationService,
                                    EventLocationMapper eventlocationMapper) {
        super(eventlocationService, eventlocationMapper);
    }

    @GetMapping
    public ResponseEntity<Page<EventLocationDto>> getAllEventLocations(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EventLocationDto>> searchEventLocations(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(EventLocation.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventLocationDto> getEventLocationById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<EventLocationDto> createEventLocation(
            @Valid @RequestBody EventLocationDto eventlocationDto,
            UriComponentsBuilder uriBuilder) {

        EventLocation entity = mapper.toEntity(eventlocationDto);
        EventLocation saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/eventlocations/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EventLocationDto>> createAllEventLocations(
            @Valid @RequestBody List<EventLocationDto> eventlocationDtoList,
            UriComponentsBuilder uriBuilder) {

        List<EventLocation> entities = mapper.toEntityList(eventlocationDtoList);
        List<EventLocation> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/eventlocations").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventLocationDto> updateEventLocation(
            @PathVariable Long id,
            @Valid @RequestBody EventLocationDto eventlocationDto) {

        EventLocation entityToUpdate = mapper.toEntity(eventlocationDto);
        EventLocation updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventLocation(@PathVariable Long id) {
        return doDelete(id);
    }
}