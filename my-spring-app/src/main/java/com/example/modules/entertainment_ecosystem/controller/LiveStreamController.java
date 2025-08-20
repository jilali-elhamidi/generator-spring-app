package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.LiveStreamDto;
import com.example.modules.entertainment_ecosystem.model.LiveStream;
import com.example.modules.entertainment_ecosystem.mapper.LiveStreamMapper;
import com.example.modules.entertainment_ecosystem.service.LiveStreamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/livestreams")
public class LiveStreamController {

    private final LiveStreamService livestreamService;
    private final LiveStreamMapper livestreamMapper;

    public LiveStreamController(LiveStreamService livestreamService,
                                    LiveStreamMapper livestreamMapper) {
        this.livestreamService = livestreamService;
        this.livestreamMapper = livestreamMapper;
    }

    @GetMapping
    public ResponseEntity<List<LiveStreamDto>> getAllLiveStreams() {
        List<LiveStream> entities = livestreamService.findAll();
        return ResponseEntity.ok(livestreamMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiveStreamDto> getLiveStreamById(@PathVariable Long id) {
        return livestreamService.findById(id)
                .map(livestreamMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LiveStreamDto> createLiveStream(
            @Valid @RequestBody LiveStreamDto livestreamDto,
            UriComponentsBuilder uriBuilder) {

        LiveStream entity = livestreamMapper.toEntity(livestreamDto);
        LiveStream saved = livestreamService.save(entity);

        URI location = uriBuilder
                                .path("/api/livestreams/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(livestreamMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LiveStreamDto> updateLiveStream(
            @PathVariable Long id,
            @Valid @RequestBody LiveStreamDto livestreamDto) {


        LiveStream entityToUpdate = livestreamMapper.toEntity(livestreamDto);
        LiveStream updatedEntity = livestreamService.update(id, entityToUpdate);

        return ResponseEntity.ok(livestreamMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLiveStream(@PathVariable Long id) {
        boolean deleted = livestreamService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}