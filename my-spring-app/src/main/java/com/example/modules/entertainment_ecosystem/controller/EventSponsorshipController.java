package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EventSponsorshipDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventSponsorshipSimpleDto;
import com.example.modules.entertainment_ecosystem.model.EventSponsorship;
import com.example.modules.entertainment_ecosystem.mapper.EventSponsorshipMapper;
import com.example.modules.entertainment_ecosystem.service.EventSponsorshipService;
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
 * Controller for managing EventSponsorship entities.
 */
@RestController
@RequestMapping("/api/eventsponsorships")
public class EventSponsorshipController extends BaseController<EventSponsorship, EventSponsorshipDto, EventSponsorshipSimpleDto> {

    public EventSponsorshipController(EventSponsorshipService eventsponsorshipService,
                                    EventSponsorshipMapper eventsponsorshipMapper) {
        super(eventsponsorshipService, eventsponsorshipMapper);
    }

    @GetMapping
    public ResponseEntity<Page<EventSponsorshipDto>> getAllEventSponsorships(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EventSponsorshipDto>> searchEventSponsorships(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(EventSponsorship.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventSponsorshipDto> getEventSponsorshipById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<EventSponsorshipDto> createEventSponsorship(
            @Valid @RequestBody EventSponsorshipDto eventsponsorshipDto,
            UriComponentsBuilder uriBuilder) {

        EventSponsorship entity = mapper.toEntity(eventsponsorshipDto);
        EventSponsorship saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/eventsponsorships/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EventSponsorshipDto>> createAllEventSponsorships(
            @Valid @RequestBody List<EventSponsorshipDto> eventsponsorshipDtoList,
            UriComponentsBuilder uriBuilder) {

        List<EventSponsorship> entities = mapper.toEntityList(eventsponsorshipDtoList);
        List<EventSponsorship> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/eventsponsorships").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventSponsorshipDto> updateEventSponsorship(
            @PathVariable Long id,
            @Valid @RequestBody EventSponsorshipDto eventsponsorshipDto) {

        EventSponsorship entityToUpdate = mapper.toEntity(eventsponsorshipDto);
        EventSponsorship updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventSponsorship(@PathVariable Long id) {
        return doDelete(id);
    }
}