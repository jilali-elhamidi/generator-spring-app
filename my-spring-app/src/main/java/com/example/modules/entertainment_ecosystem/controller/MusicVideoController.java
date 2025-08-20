package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MusicVideoDto;
import com.example.modules.entertainment_ecosystem.model.MusicVideo;
import com.example.modules.entertainment_ecosystem.mapper.MusicVideoMapper;
import com.example.modules.entertainment_ecosystem.service.MusicVideoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/musicvideos")
public class MusicVideoController {

    private final MusicVideoService musicvideoService;
    private final MusicVideoMapper musicvideoMapper;

    public MusicVideoController(MusicVideoService musicvideoService,
                                    MusicVideoMapper musicvideoMapper) {
        this.musicvideoService = musicvideoService;
        this.musicvideoMapper = musicvideoMapper;
    }

    @GetMapping
    public ResponseEntity<List<MusicVideoDto>> getAllMusicVideos() {
        List<MusicVideo> entities = musicvideoService.findAll();
        return ResponseEntity.ok(musicvideoMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicVideoDto> getMusicVideoById(@PathVariable Long id) {
        return musicvideoService.findById(id)
                .map(musicvideoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MusicVideoDto> createMusicVideo(
            @Valid @RequestBody MusicVideoDto musicvideoDto,
            UriComponentsBuilder uriBuilder) {

        MusicVideo entity = musicvideoMapper.toEntity(musicvideoDto);
        MusicVideo saved = musicvideoService.save(entity);
        URI location = uriBuilder.path("/api/musicvideos/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(musicvideoMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MusicVideoDto> updateMusicVideo(
                @PathVariable Long id,
                @RequestBody MusicVideoDto musicvideoDto) {

                // Transformer le DTO en entity pour le service
                MusicVideo entityToUpdate = musicvideoMapper.toEntity(musicvideoDto);

                // Appel du service update
                MusicVideo updatedEntity = musicvideoService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MusicVideoDto updatedDto = musicvideoMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteMusicVideo(@PathVariable Long id) {
                    boolean deleted = musicvideoService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}