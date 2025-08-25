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

        URI location = uriBuilder
                                .path("/api/mediafiles/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(mediafileMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MediaFileDto>> createAllMediaFiles(
            @Valid @RequestBody List<MediaFileDto> mediafileDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MediaFile> entities = mediafileMapper.toEntityList(mediafileDtoList);
        List<MediaFile> savedEntities = mediafileService.saveAll(entities);

        URI location = uriBuilder.path("/api/mediafiles").build().toUri();

        return ResponseEntity.created(location).body(mediafileMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaFileDto> updateMediaFile(
            @PathVariable Long id,
            @Valid @RequestBody MediaFileDto mediafileDto) {


        MediaFile entityToUpdate = mediafileMapper.toEntity(mediafileDto);
        MediaFile updatedEntity = mediafileService.update(id, entityToUpdate);

        return ResponseEntity.ok(mediafileMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMediaFile(@PathVariable Long id) {
        boolean deleted = mediafileService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}