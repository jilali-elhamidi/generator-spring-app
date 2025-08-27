package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.StreamingPlatformDto;
import com.example.modules.entertainment_ecosystem.dtosimple.StreamingPlatformSimpleDto;
import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.mapper.StreamingPlatformMapper;
import com.example.modules.entertainment_ecosystem.service.StreamingPlatformService;
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
 * Controller for managing StreamingPlatform entities.
 */
@RestController
@RequestMapping("/api/streamingplatforms")
public class StreamingPlatformController extends BaseController<StreamingPlatform, StreamingPlatformDto, StreamingPlatformSimpleDto> {

    public StreamingPlatformController(StreamingPlatformService streamingplatformService,
                                    StreamingPlatformMapper streamingplatformMapper) {
        super(streamingplatformService, streamingplatformMapper);
    }

    @GetMapping
    public ResponseEntity<Page<StreamingPlatformDto>> getAllStreamingPlatforms(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StreamingPlatformDto>> searchStreamingPlatforms(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(StreamingPlatform.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingPlatformDto> getStreamingPlatformById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<StreamingPlatformDto> createStreamingPlatform(
            @Valid @RequestBody StreamingPlatformDto streamingplatformDto,
            UriComponentsBuilder uriBuilder) {

        StreamingPlatform entity = mapper.toEntity(streamingplatformDto);
        StreamingPlatform saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/streamingplatforms/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<StreamingPlatformDto>> createAllStreamingPlatforms(
            @Valid @RequestBody List<StreamingPlatformDto> streamingplatformDtoList,
            UriComponentsBuilder uriBuilder) {

        List<StreamingPlatform> entities = mapper.toEntityList(streamingplatformDtoList);
        List<StreamingPlatform> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/streamingplatforms").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StreamingPlatformDto> updateStreamingPlatform(
            @PathVariable Long id,
            @Valid @RequestBody StreamingPlatformDto streamingplatformDto) {

        StreamingPlatform entityToUpdate = mapper.toEntity(streamingplatformDto);
        StreamingPlatform updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStreamingPlatform(@PathVariable Long id) {
        return doDelete(id);
    }
}