package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.OnlineEventDto;
import com.example.modules.entertainment_ecosystem.dtosimple.OnlineEventSimpleDto;
import com.example.modules.entertainment_ecosystem.model.OnlineEvent;
import com.example.modules.entertainment_ecosystem.mapper.OnlineEventMapper;
import com.example.modules.entertainment_ecosystem.service.OnlineEventService;
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
 * Controller for managing OnlineEvent entities.
 */
@RestController
@RequestMapping("/api/onlineevents")
public class OnlineEventController extends BaseController<OnlineEvent, OnlineEventDto, OnlineEventSimpleDto> {

    public OnlineEventController(OnlineEventService onlineeventService,
                                    OnlineEventMapper onlineeventMapper) {
        super(onlineeventService, onlineeventMapper);
    }

    @GetMapping
    public ResponseEntity<Page<OnlineEventDto>> getAllOnlineEvents(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OnlineEventDto>> searchOnlineEvents(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(OnlineEvent.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OnlineEventDto> getOnlineEventById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<OnlineEventDto> createOnlineEvent(
            @Valid @RequestBody OnlineEventDto onlineeventDto,
            UriComponentsBuilder uriBuilder) {

        OnlineEvent entity = mapper.toEntity(onlineeventDto);
        OnlineEvent saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/onlineevents/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<OnlineEventDto>> createAllOnlineEvents(
            @Valid @RequestBody List<OnlineEventDto> onlineeventDtoList,
            UriComponentsBuilder uriBuilder) {

        List<OnlineEvent> entities = mapper.toEntityList(onlineeventDtoList);
        List<OnlineEvent> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/onlineevents").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OnlineEventDto> updateOnlineEvent(
            @PathVariable Long id,
            @Valid @RequestBody OnlineEventDto onlineeventDto) {

        OnlineEvent entityToUpdate = mapper.toEntity(onlineeventDto);
        OnlineEvent updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnlineEvent(@PathVariable Long id) {
        return doDelete(id);
    }
}