package com.example.modules.social_media.controller;

import com.example.modules.social_media.dto.MediaFileDto;
import com.example.modules.social_media.model.MediaFile;
import com.example.modules.social_media.mapper.MediaFileMapper;
import com.example.modules.social_media.service.MediaFileService;
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
            @Valid @RequestBody MediaFileDto mediafileDto) {

        try {
            MediaFile updatedEntity = mediafileService.update(
                    id,
                    mediafileMapper.toEntity(mediafileDto)
            );
            return ResponseEntity.ok(mediafileMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMediaFile(@PathVariable Long id) {
        mediafileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}