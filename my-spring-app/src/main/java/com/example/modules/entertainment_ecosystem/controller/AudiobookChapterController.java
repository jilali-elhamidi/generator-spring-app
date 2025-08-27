package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.AudiobookChapterDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AudiobookChapterSimpleDto;
import com.example.modules.entertainment_ecosystem.model.AudiobookChapter;
import com.example.modules.entertainment_ecosystem.mapper.AudiobookChapterMapper;
import com.example.modules.entertainment_ecosystem.service.AudiobookChapterService;
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
 * Controller for managing AudiobookChapter entities.
 */
@RestController
@RequestMapping("/api/audiobookchapters")
public class AudiobookChapterController extends BaseController<AudiobookChapter, AudiobookChapterDto, AudiobookChapterSimpleDto> {

    public AudiobookChapterController(AudiobookChapterService audiobookchapterService,
                                    AudiobookChapterMapper audiobookchapterMapper) {
        super(audiobookchapterService, audiobookchapterMapper);
    }

    @GetMapping
    public ResponseEntity<Page<AudiobookChapterDto>> getAllAudiobookChapters(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AudiobookChapterDto>> searchAudiobookChapters(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(AudiobookChapter.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AudiobookChapterDto> getAudiobookChapterById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<AudiobookChapterDto> createAudiobookChapter(
            @Valid @RequestBody AudiobookChapterDto audiobookchapterDto,
            UriComponentsBuilder uriBuilder) {

        AudiobookChapter entity = mapper.toEntity(audiobookchapterDto);
        AudiobookChapter saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/audiobookchapters/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AudiobookChapterDto>> createAllAudiobookChapters(
            @Valid @RequestBody List<AudiobookChapterDto> audiobookchapterDtoList,
            UriComponentsBuilder uriBuilder) {

        List<AudiobookChapter> entities = mapper.toEntityList(audiobookchapterDtoList);
        List<AudiobookChapter> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/audiobookchapters").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AudiobookChapterDto> updateAudiobookChapter(
            @PathVariable Long id,
            @Valid @RequestBody AudiobookChapterDto audiobookchapterDto) {

        AudiobookChapter entityToUpdate = mapper.toEntity(audiobookchapterDto);
        AudiobookChapter updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAudiobookChapter(@PathVariable Long id) {
        return doDelete(id);
    }
}