package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.OnlineEventTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.OnlineEventTypeSimpleDto;
import com.example.modules.entertainment_ecosystem.model.OnlineEventType;
import com.example.modules.entertainment_ecosystem.mapper.OnlineEventTypeMapper;
import com.example.modules.entertainment_ecosystem.service.OnlineEventTypeService;
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
 * Controller for managing OnlineEventType entities.
 */
@RestController
@RequestMapping("/api/onlineeventtypes")
public class OnlineEventTypeController extends BaseController<OnlineEventType, OnlineEventTypeDto, OnlineEventTypeSimpleDto> {

    public OnlineEventTypeController(OnlineEventTypeService onlineeventtypeService,
                                    OnlineEventTypeMapper onlineeventtypeMapper) {
        super(onlineeventtypeService, onlineeventtypeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<OnlineEventTypeDto>> getAllOnlineEventTypes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OnlineEventTypeDto>> searchOnlineEventTypes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(OnlineEventType.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OnlineEventTypeDto> getOnlineEventTypeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<OnlineEventTypeDto> createOnlineEventType(
            @Valid @RequestBody OnlineEventTypeDto onlineeventtypeDto,
            UriComponentsBuilder uriBuilder) {

        OnlineEventType entity = mapper.toEntity(onlineeventtypeDto);
        OnlineEventType saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/onlineeventtypes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<OnlineEventTypeDto>> createAllOnlineEventTypes(
            @Valid @RequestBody List<OnlineEventTypeDto> onlineeventtypeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<OnlineEventType> entities = mapper.toEntityList(onlineeventtypeDtoList);
        List<OnlineEventType> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/onlineeventtypes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OnlineEventTypeDto> updateOnlineEventType(
            @PathVariable Long id,
            @Valid @RequestBody OnlineEventTypeDto onlineeventtypeDto) {

        OnlineEventType entityToUpdate = mapper.toEntity(onlineeventtypeDto);
        OnlineEventType updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnlineEventType(@PathVariable Long id) {
        return doDelete(id);
    }
}