package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.StreamingPlatformDto;
import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.mapper.StreamingPlatformMapper;
import com.example.modules.entertainment_ecosystem.service.StreamingPlatformService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/streamingplatforms")
public class StreamingPlatformController {

    private final StreamingPlatformService streamingplatformService;
    private final StreamingPlatformMapper streamingplatformMapper;

    public StreamingPlatformController(StreamingPlatformService streamingplatformService,
                                    StreamingPlatformMapper streamingplatformMapper) {
        this.streamingplatformService = streamingplatformService;
        this.streamingplatformMapper = streamingplatformMapper;
    }

    @GetMapping
    public ResponseEntity<List<StreamingPlatformDto>> getAllStreamingPlatforms() {
        List<StreamingPlatform> entities = streamingplatformService.findAll();
        return ResponseEntity.ok(streamingplatformMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingPlatformDto> getStreamingPlatformById(@PathVariable Long id) {
        return streamingplatformService.findById(id)
                .map(streamingplatformMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StreamingPlatformDto> createStreamingPlatform(
            @Valid @RequestBody StreamingPlatformDto streamingplatformDto,
            UriComponentsBuilder uriBuilder) {

        StreamingPlatform entity = streamingplatformMapper.toEntity(streamingplatformDto);
        StreamingPlatform saved = streamingplatformService.save(entity);

        URI location = uriBuilder
                                .path("/api/streamingplatforms/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(streamingplatformMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StreamingPlatformDto> updateStreamingPlatform(
            @PathVariable Long id,
            @Valid @RequestBody StreamingPlatformDto streamingplatformDto) {


        StreamingPlatform entityToUpdate = streamingplatformMapper.toEntity(streamingplatformDto);
        StreamingPlatform updatedEntity = streamingplatformService.update(id, entityToUpdate);

        return ResponseEntity.ok(streamingplatformMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStreamingPlatform(@PathVariable Long id) {
        boolean deleted = streamingplatformService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}