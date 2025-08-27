package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MusicTrackDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicTrackSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.mapper.MusicTrackMapper;
import com.example.modules.entertainment_ecosystem.service.MusicTrackService;
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
 * Controller for managing MusicTrack entities.
 */
@RestController
@RequestMapping("/api/musictracks")
public class MusicTrackController extends BaseController<MusicTrack, MusicTrackDto, MusicTrackSimpleDto> {

    public MusicTrackController(MusicTrackService musictrackService,
                                    MusicTrackMapper musictrackMapper) {
        super(musictrackService, musictrackMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MusicTrackDto>> getAllMusicTracks(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MusicTrackDto>> searchMusicTracks(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MusicTrack.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicTrackDto> getMusicTrackById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MusicTrackDto> createMusicTrack(
            @Valid @RequestBody MusicTrackDto musictrackDto,
            UriComponentsBuilder uriBuilder) {

        MusicTrack entity = mapper.toEntity(musictrackDto);
        MusicTrack saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/musictracks/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MusicTrackDto>> createAllMusicTracks(
            @Valid @RequestBody List<MusicTrackDto> musictrackDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MusicTrack> entities = mapper.toEntityList(musictrackDtoList);
        List<MusicTrack> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/musictracks").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicTrackDto> updateMusicTrack(
            @PathVariable Long id,
            @Valid @RequestBody MusicTrackDto musictrackDto) {

        MusicTrack entityToUpdate = mapper.toEntity(musictrackDto);
        MusicTrack updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusicTrack(@PathVariable Long id) {
        return doDelete(id);
    }
}