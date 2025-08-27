package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EventTicketTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventTicketTypeSimpleDto;
import com.example.modules.entertainment_ecosystem.model.EventTicketType;
import com.example.modules.entertainment_ecosystem.mapper.EventTicketTypeMapper;
import com.example.modules.entertainment_ecosystem.service.EventTicketTypeService;
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
 * Controller for managing EventTicketType entities.
 */
@RestController
@RequestMapping("/api/eventtickettypes")
public class EventTicketTypeController extends BaseController<EventTicketType, EventTicketTypeDto, EventTicketTypeSimpleDto> {

    public EventTicketTypeController(EventTicketTypeService eventtickettypeService,
                                    EventTicketTypeMapper eventtickettypeMapper) {
        super(eventtickettypeService, eventtickettypeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<EventTicketTypeDto>> getAllEventTicketTypes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EventTicketTypeDto>> searchEventTicketTypes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(EventTicketType.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventTicketTypeDto> getEventTicketTypeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<EventTicketTypeDto> createEventTicketType(
            @Valid @RequestBody EventTicketTypeDto eventtickettypeDto,
            UriComponentsBuilder uriBuilder) {

        EventTicketType entity = mapper.toEntity(eventtickettypeDto);
        EventTicketType saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/eventtickettypes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EventTicketTypeDto>> createAllEventTicketTypes(
            @Valid @RequestBody List<EventTicketTypeDto> eventtickettypeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<EventTicketType> entities = mapper.toEntityList(eventtickettypeDtoList);
        List<EventTicketType> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/eventtickettypes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventTicketTypeDto> updateEventTicketType(
            @PathVariable Long id,
            @Valid @RequestBody EventTicketTypeDto eventtickettypeDto) {

        EventTicketType entityToUpdate = mapper.toEntity(eventtickettypeDto);
        EventTicketType updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventTicketType(@PathVariable Long id) {
        return doDelete(id);
    }
}