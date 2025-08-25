package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MusicTrackDto;
import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.mapper.MusicTrackMapper;
import com.example.modules.entertainment_ecosystem.service.MusicTrackService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/musictracks")
public class MusicTrackController {

    private final MusicTrackService musictrackService;
    private final MusicTrackMapper musictrackMapper;

    public MusicTrackController(MusicTrackService musictrackService,
                                    MusicTrackMapper musictrackMapper) {
        this.musictrackService = musictrackService;
        this.musictrackMapper = musictrackMapper;
    }

    @GetMapping
    public ResponseEntity<List<MusicTrackDto>> getAllMusicTracks() {
        List<MusicTrack> entities = musictrackService.findAll();
        return ResponseEntity.ok(musictrackMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicTrackDto> getMusicTrackById(@PathVariable Long id) {
        return musictrackService.findById(id)
                .map(musictrackMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MusicTrackDto> createMusicTrack(
            @Valid @RequestBody MusicTrackDto musictrackDto,
            UriComponentsBuilder uriBuilder) {

        MusicTrack entity = musictrackMapper.toEntity(musictrackDto);
        MusicTrack saved = musictrackService.save(entity);

        URI location = uriBuilder
                                .path("/api/musictracks/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(musictrackMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MusicTrackDto>> createAllMusicTracks(
            @Valid @RequestBody List<MusicTrackDto> musictrackDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MusicTrack> entities = musictrackMapper.toEntityList(musictrackDtoList);
        List<MusicTrack> savedEntities = musictrackService.saveAll(entities);

        URI location = uriBuilder.path("/api/musictracks").build().toUri();

        return ResponseEntity.created(location).body(musictrackMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicTrackDto> updateMusicTrack(
            @PathVariable Long id,
            @Valid @RequestBody MusicTrackDto musictrackDto) {


        MusicTrack entityToUpdate = musictrackMapper.toEntity(musictrackDto);
        MusicTrack updatedEntity = musictrackService.update(id, entityToUpdate);

        return ResponseEntity.ok(musictrackMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusicTrack(@PathVariable Long id) {
        boolean deleted = musictrackService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}