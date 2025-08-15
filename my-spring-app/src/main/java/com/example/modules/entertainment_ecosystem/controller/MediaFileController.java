package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MediaFileDto;
import com.example.modules.entertainment_ecosystem.model.MediaFile;
import com.example.modules.entertainment_ecosystem.mapper.MediaFileMapper;
import com.example.modules.entertainment_ecosystem.service.MediaFileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/mediafiles")
public class MediaFileController {

    private final MediaFileService mediafileService;
    private final MediaFileMapper mediafileMapper;

    public MediaFileController(MediaFileService mediafileService,
                                    MediaFileMapper mediafileMapper) {
        this.mediafileService = mediafileService;
        this.mediafileMapper = mediafileMapper;
    }

    @GetMapping
    public ResponseEntity<List<MediaFileDto>> getAllMediaFiles() {
        List<MediaFile> entities = mediafileService.findAll();
        return ResponseEntity.ok(mediafileMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaFileDto> getMediaFileById(@PathVariable Long id) {
        return mediafileService.findById(id)
                .map(mediafileMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MediaFileDto> createMediaFile(
            @Valid @RequestBody MediaFileDto mediafileDto,
            UriComponentsBuilder uriBuilder) {

        MediaFile entity = mediafileMapper.toEntity(mediafileDto);
        MediaFile saved = mediafileService.save(entity);
        URI location = uriBuilder.path("/api/mediafiles/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(mediafileMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MediaFileDto> updateMediaFile(
                @PathVariable Long id,
                @RequestBody MediaFileDto mediafileDto) {

                // Transformer le DTO en entity pour le service
                MediaFile entityToUpdate = mediafileMapper.toEntity(mediafileDto);

                // Appel du service update
                MediaFile updatedEntity = mediafileService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MediaFileDto updatedDto = mediafileMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMediaFile(@PathVariable Long id) {
        mediafileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}