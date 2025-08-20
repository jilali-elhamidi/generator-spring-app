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
        URI location = uriBuilder.path("/api/livestreamviewers/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(livestreamviewerMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<LiveStreamViewerDto> updateLiveStreamViewer(
                @PathVariable Long id,
                @RequestBody LiveStreamViewerDto livestreamviewerDto) {

                // Transformer le DTO en entity pour le service
                LiveStreamViewer entityToUpdate = livestreamviewerMapper.toEntity(livestreamviewerDto);

                // Appel du service update
                LiveStreamViewer updatedEntity = livestreamviewerService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                LiveStreamViewerDto updatedDto = livestreamviewerMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteLiveStreamViewer(@PathVariable Long id) {
                    boolean deleted = livestreamviewerService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}