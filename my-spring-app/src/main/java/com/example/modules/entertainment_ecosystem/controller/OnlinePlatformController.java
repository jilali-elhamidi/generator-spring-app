package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.OnlinePlatformDto;
import com.example.modules.entertainment_ecosystem.dtosimple.OnlinePlatformSimpleDto;
import com.example.modules.entertainment_ecosystem.model.OnlinePlatform;
import com.example.modules.entertainment_ecosystem.mapper.OnlinePlatformMapper;
import com.example.modules.entertainment_ecosystem.service.OnlinePlatformService;
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
 * Controller for managing OnlinePlatform entities.
 */
@RestController
@RequestMapping("/api/onlineplatforms")
public class OnlinePlatformController extends BaseController<OnlinePlatform, OnlinePlatformDto, OnlinePlatformSimpleDto> {

    public OnlinePlatformController(OnlinePlatformService onlineplatformService,
                                    OnlinePlatformMapper onlineplatformMapper) {
        super(onlineplatformService, onlineplatformMapper);
    }

    @GetMapping
    public ResponseEntity<Page<OnlinePlatformDto>> getAllOnlinePlatforms(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OnlinePlatformDto>> searchOnlinePlatforms(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(OnlinePlatform.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OnlinePlatformDto> getOnlinePlatformById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<OnlinePlatformDto> createOnlinePlatform(
            @Valid @RequestBody OnlinePlatformDto onlineplatformDto,
            UriComponentsBuilder uriBuilder) {

        OnlinePlatform entity = mapper.toEntity(onlineplatformDto);
        OnlinePlatform saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/onlineplatforms/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<OnlinePlatformDto>> createAllOnlinePlatforms(
            @Valid @RequestBody List<OnlinePlatformDto> onlineplatformDtoList,
            UriComponentsBuilder uriBuilder) {

        List<OnlinePlatform> entities = mapper.toEntityList(onlineplatformDtoList);
        List<OnlinePlatform> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/onlineplatforms").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OnlinePlatformDto> updateOnlinePlatform(
            @PathVariable Long id,
            @Valid @RequestBody OnlinePlatformDto onlineplatformDto) {

        OnlinePlatform entityToUpdate = mapper.toEntity(onlineplatformDto);
        OnlinePlatform updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnlinePlatform(@PathVariable Long id) {
        return doDelete(id);
    }
}