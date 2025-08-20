package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EventTicketTypeDto;
import com.example.modules.entertainment_ecosystem.model.EventTicketType;
import com.example.modules.entertainment_ecosystem.mapper.EventTicketTypeMapper;
import com.example.modules.entertainment_ecosystem.service.EventTicketTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/eventtickettypes")
public class EventTicketTypeController {

    private final EventTicketTypeService eventtickettypeService;
    private final EventTicketTypeMapper eventtickettypeMapper;

    public EventTicketTypeController(EventTicketTypeService eventtickettypeService,
                                    EventTicketTypeMapper eventtickettypeMapper) {
        this.eventtickettypeService = eventtickettypeService;
        this.eventtickettypeMapper = eventtickettypeMapper;
    }

    @GetMapping
    public ResponseEntity<List<EventTicketTypeDto>> getAllEventTicketTypes() {
        List<EventTicketType> entities = eventtickettypeService.findAll();
        return ResponseEntity.ok(eventtickettypeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventTicketTypeDto> getEventTicketTypeById(@PathVariable Long id) {
        return eventtickettypeService.findById(id)
                .map(eventtickettypeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventTicketTypeDto> createEventTicketType(
            @Valid @RequestBody EventTicketTypeDto eventtickettypeDto,
            UriComponentsBuilder uriBuilder) {

        EventTicketType entity = eventtickettypeMapper.toEntity(eventtickettypeDto);
        EventTicketType saved = eventtickettypeService.save(entity);

        URI location = uriBuilder
                                .path("/api/eventtickettypes/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(eventtickettypeMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventTicketTypeDto> updateEventTicketType(
            @PathVariable Long id,
            @Valid @RequestBody EventTicketTypeDto eventtickettypeDto) {


        EventTicketType entityToUpdate = eventtickettypeMapper.toEntity(eventtickettypeDto);
        EventTicketType updatedEntity = eventtickettypeService.update(id, entityToUpdate);

        return ResponseEntity.ok(eventtickettypeMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventTicketType(@PathVariable Long id) {
        boolean deleted = eventtickettypeService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}