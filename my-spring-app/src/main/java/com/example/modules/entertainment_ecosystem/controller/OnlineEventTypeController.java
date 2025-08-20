package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.OnlineEventTypeDto;
import com.example.modules.entertainment_ecosystem.model.OnlineEventType;
import com.example.modules.entertainment_ecosystem.mapper.OnlineEventTypeMapper;
import com.example.modules.entertainment_ecosystem.service.OnlineEventTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/onlineeventtypes")
public class OnlineEventTypeController {

    private final OnlineEventTypeService onlineeventtypeService;
    private final OnlineEventTypeMapper onlineeventtypeMapper;

    public OnlineEventTypeController(OnlineEventTypeService onlineeventtypeService,
                                    OnlineEventTypeMapper onlineeventtypeMapper) {
        this.onlineeventtypeService = onlineeventtypeService;
        this.onlineeventtypeMapper = onlineeventtypeMapper;
    }

    @GetMapping
    public ResponseEntity<List<OnlineEventTypeDto>> getAllOnlineEventTypes() {
        List<OnlineEventType> entities = onlineeventtypeService.findAll();
        return ResponseEntity.ok(onlineeventtypeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OnlineEventTypeDto> getOnlineEventTypeById(@PathVariable Long id) {
        return onlineeventtypeService.findById(id)
                .map(onlineeventtypeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OnlineEventTypeDto> createOnlineEventType(
            @Valid @RequestBody OnlineEventTypeDto onlineeventtypeDto,
            UriComponentsBuilder uriBuilder) {

        OnlineEventType entity = onlineeventtypeMapper.toEntity(onlineeventtypeDto);
        OnlineEventType saved = onlineeventtypeService.save(entity);

        URI location = uriBuilder
                                .path("/api/onlineeventtypes/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(onlineeventtypeMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OnlineEventTypeDto> updateOnlineEventType(
            @PathVariable Long id,
            @Valid @RequestBody OnlineEventTypeDto onlineeventtypeDto) {


        OnlineEventType entityToUpdate = onlineeventtypeMapper.toEntity(onlineeventtypeDto);
        OnlineEventType updatedEntity = onlineeventtypeService.update(id, entityToUpdate);

        return ResponseEntity.ok(onlineeventtypeMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnlineEventType(@PathVariable Long id) {
        boolean deleted = onlineeventtypeService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}