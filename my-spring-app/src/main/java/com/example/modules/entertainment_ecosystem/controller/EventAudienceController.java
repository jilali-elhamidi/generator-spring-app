package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EventAudienceDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventAudienceSimpleDto;
import com.example.modules.entertainment_ecosystem.model.EventAudience;
import com.example.modules.entertainment_ecosystem.mapper.EventAudienceMapper;
import com.example.modules.entertainment_ecosystem.service.EventAudienceService;
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
 * Controller for managing EventAudience entities.
 */
@RestController
@RequestMapping("/api/eventaudiences")
public class EventAudienceController extends BaseController<EventAudience, EventAudienceDto, EventAudienceSimpleDto> {

    public EventAudienceController(EventAudienceService eventaudienceService,
                                    EventAudienceMapper eventaudienceMapper) {
        super(eventaudienceService, eventaudienceMapper);
    }

    @GetMapping
    public ResponseEntity<Page<EventAudienceDto>> getAllEventAudiences(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EventAudienceDto>> searchEventAudiences(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(EventAudience.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventAudienceDto> getEventAudienceById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<EventAudienceDto> createEventAudience(
            @Valid @RequestBody EventAudienceDto eventaudienceDto,
            UriComponentsBuilder uriBuilder) {

        EventAudience entity = mapper.toEntity(eventaudienceDto);
        EventAudience saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/eventaudiences/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EventAudienceDto>> createAllEventAudiences(
            @Valid @RequestBody List<EventAudienceDto> eventaudienceDtoList,
            UriComponentsBuilder uriBuilder) {

        List<EventAudience> entities = mapper.toEntityList(eventaudienceDtoList);
        List<EventAudience> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/eventaudiences").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventAudienceDto> updateEventAudience(
            @PathVariable Long id,
            @Valid @RequestBody EventAudienceDto eventaudienceDto) {

        EventAudience entityToUpdate = mapper.toEntity(eventaudienceDto);
        EventAudience updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventAudience(@PathVariable Long id) {
        return doDelete(id);
    }
}