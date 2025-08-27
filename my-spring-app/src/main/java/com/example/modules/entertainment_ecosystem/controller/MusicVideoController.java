package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MusicVideoDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicVideoSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MusicVideo;
import com.example.modules.entertainment_ecosystem.mapper.MusicVideoMapper;
import com.example.modules.entertainment_ecosystem.service.MusicVideoService;
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
 * Controller for managing MusicVideo entities.
 */
@RestController
@RequestMapping("/api/musicvideos")
public class MusicVideoController extends BaseController<MusicVideo, MusicVideoDto, MusicVideoSimpleDto> {

    public MusicVideoController(MusicVideoService musicvideoService,
                                    MusicVideoMapper musicvideoMapper) {
        super(musicvideoService, musicvideoMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MusicVideoDto>> getAllMusicVideos(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MusicVideoDto>> searchMusicVideos(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MusicVideo.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicVideoDto> getMusicVideoById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MusicVideoDto> createMusicVideo(
            @Valid @RequestBody MusicVideoDto musicvideoDto,
            UriComponentsBuilder uriBuilder) {

        MusicVideo entity = mapper.toEntity(musicvideoDto);
        MusicVideo saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/musicvideos/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MusicVideoDto>> createAllMusicVideos(
            @Valid @RequestBody List<MusicVideoDto> musicvideoDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MusicVideo> entities = mapper.toEntityList(musicvideoDtoList);
        List<MusicVideo> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/musicvideos").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicVideoDto> updateMusicVideo(
            @PathVariable Long id,
            @Valid @RequestBody MusicVideoDto musicvideoDto) {

        MusicVideo entityToUpdate = mapper.toEntity(musicvideoDto);
        MusicVideo updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusicVideo(@PathVariable Long id) {
        return doDelete(id);
    }
}