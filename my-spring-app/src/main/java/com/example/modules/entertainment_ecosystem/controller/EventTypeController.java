package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EventTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventTypeSimpleDto;
import com.example.modules.entertainment_ecosystem.model.EventType;
import com.example.modules.entertainment_ecosystem.mapper.EventTypeMapper;
import com.example.modules.entertainment_ecosystem.service.EventTypeService;
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
 * Controller for managing EventType entities.
 */
@RestController
@RequestMapping("/api/eventtypes")
public class EventTypeController extends BaseController<EventType, EventTypeDto, EventTypeSimpleDto> {

    public EventTypeController(EventTypeService eventtypeService,
                                    EventTypeMapper eventtypeMapper) {
        super(eventtypeService, eventtypeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<EventTypeDto>> getAllEventTypes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EventTypeDto>> searchEventTypes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(EventType.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventTypeDto> getEventTypeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<EventTypeDto> createEventType(
            @Valid @RequestBody EventTypeDto eventtypeDto,
            UriComponentsBuilder uriBuilder) {

        EventType entity = mapper.toEntity(eventtypeDto);
        EventType saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/eventtypes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EventTypeDto>> createAllEventTypes(
            @Valid @RequestBody List<EventTypeDto> eventtypeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<EventType> entities = mapper.toEntityList(eventtypeDtoList);
        List<EventType> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/eventtypes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventTypeDto> updateEventType(
            @PathVariable Long id,
            @Valid @RequestBody EventTypeDto eventtypeDto) {

        EventType entityToUpdate = mapper.toEntity(eventtypeDto);
        EventType updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventType(@PathVariable Long id) {
        return doDelete(id);
    }
}