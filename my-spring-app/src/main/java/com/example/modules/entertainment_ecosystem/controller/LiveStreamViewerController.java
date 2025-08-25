package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.LiveStreamViewerDto;
import com.example.modules.entertainment_ecosystem.model.LiveStreamViewer;
import com.example.modules.entertainment_ecosystem.mapper.LiveStreamViewerMapper;
import com.example.modules.entertainment_ecosystem.service.LiveStreamViewerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/livestreamviewers")
public class LiveStreamViewerController {

    private final LiveStreamViewerService livestreamviewerService;
    private final LiveStreamViewerMapper livestreamviewerMapper;

    public LiveStreamViewerController(LiveStreamViewerService livestreamviewerService,
                                    LiveStreamViewerMapper livestreamviewerMapper) {
        this.livestreamviewerService = livestreamviewerService;
        this.livestreamviewerMapper = livestreamviewerMapper;
    }

    @GetMapping
    public ResponseEntity<List<LiveStreamViewerDto>> getAllLiveStreamViewers() {
        List<LiveStreamViewer> entities = livestreamviewerService.findAll();
        return ResponseEntity.ok(livestreamviewerMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiveStreamViewerDto> getLiveStreamViewerById(@PathVariable Long id) {
        return livestreamviewerService.findById(id)
                .map(livestreamviewerMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LiveStreamViewerDto> createLiveStreamViewer(
            @Valid @RequestBody LiveStreamViewerDto livestreamviewerDto,
            UriComponentsBuilder uriBuilder) {

        LiveStreamViewer entity = livestreamviewerMapper.toEntity(livestreamviewerDto);
        LiveStreamViewer saved = livestreamviewerService.save(entity);

        URI location = uriBuilder
                                .path("/api/livestreamviewers/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(livestreamviewerMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<LiveStreamViewerDto>> createAllLiveStreamViewers(
            @Valid @RequestBody List<LiveStreamViewerDto> livestreamviewerDtoList,
            UriComponentsBuilder uriBuilder) {

        List<LiveStreamViewer> entities = livestreamviewerMapper.toEntityList(livestreamviewerDtoList);
        List<LiveStreamViewer> savedEntities = livestreamviewerService.saveAll(entities);

        URI location = uriBuilder.path("/api/livestreamviewers").build().toUri();

        return ResponseEntity.created(location).body(livestreamviewerMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LiveStreamViewerDto> updateLiveStreamViewer(
            @PathVariable Long id,
            @Valid @RequestBody LiveStreamViewerDto livestreamviewerDto) {


        LiveStreamViewer entityToUpdate = livestreamviewerMapper.toEntity(livestreamviewerDto);
        LiveStreamViewer updatedEntity = livestreamviewerService.update(id, entityToUpdate);

        return ResponseEntity.ok(livestreamviewerMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLiveStreamViewer(@PathVariable Long id) {
        boolean deleted = livestreamviewerService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}