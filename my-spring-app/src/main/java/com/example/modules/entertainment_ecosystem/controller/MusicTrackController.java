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
        URI location = uriBuilder.path("/api/musictracks/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(musictrackMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MusicTrackDto> updateMusicTrack(
                @PathVariable Long id,
                @RequestBody MusicTrackDto musictrackDto) {

                // Transformer le DTO en entity pour le service
                MusicTrack entityToUpdate = musictrackMapper.toEntity(musictrackDto);

                // Appel du service update
                MusicTrack updatedEntity = musictrackService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MusicTrackDto updatedDto = musictrackMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusicTrack(@PathVariable Long id) {
        musictrackService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}