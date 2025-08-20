package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.OnlineEventDto;
import com.example.modules.entertainment_ecosystem.model.OnlineEvent;
import com.example.modules.entertainment_ecosystem.mapper.OnlineEventMapper;
import com.example.modules.entertainment_ecosystem.service.OnlineEventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/onlineevents")
public class OnlineEventController {

    private final OnlineEventService onlineeventService;
    private final OnlineEventMapper onlineeventMapper;

    public OnlineEventController(OnlineEventService onlineeventService,
                                    OnlineEventMapper onlineeventMapper) {
        this.onlineeventService = onlineeventService;
        this.onlineeventMapper = onlineeventMapper;
    }

    @GetMapping
    public ResponseEntity<List<OnlineEventDto>> getAllOnlineEvents() {
        List<OnlineEvent> entities = onlineeventService.findAll();
        return ResponseEntity.ok(onlineeventMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OnlineEventDto> getOnlineEventById(@PathVariable Long id) {
        return onlineeventService.findById(id)
                .map(onlineeventMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OnlineEventDto> createOnlineEvent(
            @Valid @RequestBody OnlineEventDto onlineeventDto,
            UriComponentsBuilder uriBuilder) {

        OnlineEvent entity = onlineeventMapper.toEntity(onlineeventDto);
        OnlineEvent saved = onlineeventService.save(entity);

        URI location = uriBuilder
                                .path("/api/onlineevents/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(onlineeventMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OnlineEventDto> updateOnlineEvent(
            @PathVariable Long id,
            @Valid @RequestBody OnlineEventDto onlineeventDto) {


        OnlineEvent entityToUpdate = onlineeventMapper.toEntity(onlineeventDto);
        OnlineEvent updatedEntity = onlineeventService.update(id, entityToUpdate);

        return ResponseEntity.ok(onlineeventMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnlineEvent(@PathVariable Long id) {
        boolean deleted = onlineeventService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}