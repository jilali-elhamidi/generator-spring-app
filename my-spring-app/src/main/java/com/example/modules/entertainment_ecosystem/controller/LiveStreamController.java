package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.LiveStreamDto;
import com.example.modules.entertainment_ecosystem.dtosimple.LiveStreamSimpleDto;
import com.example.modules.entertainment_ecosystem.model.LiveStream;
import com.example.modules.entertainment_ecosystem.mapper.LiveStreamMapper;
import com.example.modules.entertainment_ecosystem.service.LiveStreamService;
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
 * Controller for managing LiveStream entities.
 */
@RestController
@RequestMapping("/api/livestreams")
public class LiveStreamController extends BaseController<LiveStream, LiveStreamDto, LiveStreamSimpleDto> {

    public LiveStreamController(LiveStreamService livestreamService,
                                    LiveStreamMapper livestreamMapper) {
        super(livestreamService, livestreamMapper);
    }

    @GetMapping
    public ResponseEntity<Page<LiveStreamDto>> getAllLiveStreams(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<LiveStreamDto>> searchLiveStreams(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(LiveStream.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiveStreamDto> getLiveStreamById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<LiveStreamDto> createLiveStream(
            @Valid @RequestBody LiveStreamDto livestreamDto,
            UriComponentsBuilder uriBuilder) {

        LiveStream entity = mapper.toEntity(livestreamDto);
        LiveStream saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/livestreams/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<LiveStreamDto>> createAllLiveStreams(
            @Valid @RequestBody List<LiveStreamDto> livestreamDtoList,
            UriComponentsBuilder uriBuilder) {

        List<LiveStream> entities = mapper.toEntityList(livestreamDtoList);
        List<LiveStream> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/livestreams").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LiveStreamDto> updateLiveStream(
            @PathVariable Long id,
            @Valid @RequestBody LiveStreamDto livestreamDto) {

        LiveStream entityToUpdate = mapper.toEntity(livestreamDto);
        LiveStream updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLiveStream(@PathVariable Long id) {
        return doDelete(id);
    }
}