package com.example.modules.social_media.controller;

import com.example.modules.social_media.dto.MediaFileDto;
import com.example.modules.social_media.dtosimple.MediaFileSimpleDto;
import com.example.modules.social_media.model.MediaFile;
import com.example.modules.social_media.mapper.MediaFileMapper;
import com.example.modules.social_media.service.MediaFileService;
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
 * Controller for managing MediaFile entities.
 */
@RestController
@RequestMapping("/api/mediafiles")
public class MediaFileController extends BaseController<MediaFile, MediaFileDto, MediaFileSimpleDto> {

    public MediaFileController(MediaFileService mediafileService,
                                    MediaFileMapper mediafileMapper) {
        super(mediafileService, mediafileMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MediaFileDto>> getAllMediaFiles(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MediaFileDto>> searchMediaFiles(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MediaFile.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaFileDto> getMediaFileById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MediaFileDto> createMediaFile(
            @Valid @RequestBody MediaFileDto mediafileDto,
            UriComponentsBuilder uriBuilder) {

        MediaFile entity = mapper.toEntity(mediafileDto);
        MediaFile saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/mediafiles/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MediaFileDto>> createAllMediaFiles(
            @Valid @RequestBody List<MediaFileDto> mediafileDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MediaFile> entities = mapper.toEntityList(mediafileDtoList);
        List<MediaFile> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/mediafiles").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaFileDto> updateMediaFile(
            @PathVariable Long id,
            @Valid @RequestBody MediaFileDto mediafileDto) {

        MediaFile entityToUpdate = mapper.toEntity(mediafileDto);
        MediaFile updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMediaFile(@PathVariable Long id) {
        return doDelete(id);
    }
}