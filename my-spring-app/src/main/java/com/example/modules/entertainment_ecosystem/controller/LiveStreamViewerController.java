package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.LiveStreamViewerDto;
import com.example.modules.entertainment_ecosystem.dtosimple.LiveStreamViewerSimpleDto;
import com.example.modules.entertainment_ecosystem.model.LiveStreamViewer;
import com.example.modules.entertainment_ecosystem.mapper.LiveStreamViewerMapper;
import com.example.modules.entertainment_ecosystem.service.LiveStreamViewerService;
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
 * Controller for managing LiveStreamViewer entities.
 */
@RestController
@RequestMapping("/api/livestreamviewers")
public class LiveStreamViewerController extends BaseController<LiveStreamViewer, LiveStreamViewerDto, LiveStreamViewerSimpleDto> {

    public LiveStreamViewerController(LiveStreamViewerService livestreamviewerService,
                                    LiveStreamViewerMapper livestreamviewerMapper) {
        super(livestreamviewerService, livestreamviewerMapper);
    }

    @GetMapping
    public ResponseEntity<Page<LiveStreamViewerDto>> getAllLiveStreamViewers(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<LiveStreamViewerDto>> searchLiveStreamViewers(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(LiveStreamViewer.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiveStreamViewerDto> getLiveStreamViewerById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<LiveStreamViewerDto> createLiveStreamViewer(
            @Valid @RequestBody LiveStreamViewerDto livestreamviewerDto,
            UriComponentsBuilder uriBuilder) {

        LiveStreamViewer entity = mapper.toEntity(livestreamviewerDto);
        LiveStreamViewer saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/livestreamviewers/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<LiveStreamViewerDto>> createAllLiveStreamViewers(
            @Valid @RequestBody List<LiveStreamViewerDto> livestreamviewerDtoList,
            UriComponentsBuilder uriBuilder) {

        List<LiveStreamViewer> entities = mapper.toEntityList(livestreamviewerDtoList);
        List<LiveStreamViewer> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/livestreamviewers").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LiveStreamViewerDto> updateLiveStreamViewer(
            @PathVariable Long id,
            @Valid @RequestBody LiveStreamViewerDto livestreamviewerDto) {

        LiveStreamViewer entityToUpdate = mapper.toEntity(livestreamviewerDto);
        LiveStreamViewer updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLiveStreamViewer(@PathVariable Long id) {
        return doDelete(id);
    }
}