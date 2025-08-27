package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.LiveEventDto;
import com.example.modules.entertainment_ecosystem.dtosimple.LiveEventSimpleDto;
import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.mapper.LiveEventMapper;
import com.example.modules.entertainment_ecosystem.service.LiveEventService;
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
 * Controller for managing LiveEvent entities.
 */
@RestController
@RequestMapping("/api/liveevents")
public class LiveEventController extends BaseController<LiveEvent, LiveEventDto, LiveEventSimpleDto> {

    public LiveEventController(LiveEventService liveeventService,
                                    LiveEventMapper liveeventMapper) {
        super(liveeventService, liveeventMapper);
    }

    @GetMapping
    public ResponseEntity<Page<LiveEventDto>> getAllLiveEvents(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<LiveEventDto>> searchLiveEvents(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(LiveEvent.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiveEventDto> getLiveEventById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<LiveEventDto> createLiveEvent(
            @Valid @RequestBody LiveEventDto liveeventDto,
            UriComponentsBuilder uriBuilder) {

        LiveEvent entity = mapper.toEntity(liveeventDto);
        LiveEvent saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/liveevents/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<LiveEventDto>> createAllLiveEvents(
            @Valid @RequestBody List<LiveEventDto> liveeventDtoList,
            UriComponentsBuilder uriBuilder) {

        List<LiveEvent> entities = mapper.toEntityList(liveeventDtoList);
        List<LiveEvent> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/liveevents").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LiveEventDto> updateLiveEvent(
            @PathVariable Long id,
            @Valid @RequestBody LiveEventDto liveeventDto) {

        LiveEvent entityToUpdate = mapper.toEntity(liveeventDto);
        LiveEvent updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLiveEvent(@PathVariable Long id) {
        return doDelete(id);
    }
}