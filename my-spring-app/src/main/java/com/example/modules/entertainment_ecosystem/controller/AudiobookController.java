package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.AudiobookDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AudiobookSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Audiobook;
import com.example.modules.entertainment_ecosystem.mapper.AudiobookMapper;
import com.example.modules.entertainment_ecosystem.service.AudiobookService;
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
 * Controller for managing Audiobook entities.
 */
@RestController
@RequestMapping("/api/audiobooks")
public class AudiobookController extends BaseController<Audiobook, AudiobookDto, AudiobookSimpleDto> {

    public AudiobookController(AudiobookService audiobookService,
                                    AudiobookMapper audiobookMapper) {
        super(audiobookService, audiobookMapper);
    }

    @GetMapping
    public ResponseEntity<Page<AudiobookDto>> getAllAudiobooks(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AudiobookDto>> searchAudiobooks(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Audiobook.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AudiobookDto> getAudiobookById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<AudiobookDto> createAudiobook(
            @Valid @RequestBody AudiobookDto audiobookDto,
            UriComponentsBuilder uriBuilder) {

        Audiobook entity = mapper.toEntity(audiobookDto);
        Audiobook saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/audiobooks/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AudiobookDto>> createAllAudiobooks(
            @Valid @RequestBody List<AudiobookDto> audiobookDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Audiobook> entities = mapper.toEntityList(audiobookDtoList);
        List<Audiobook> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/audiobooks").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AudiobookDto> updateAudiobook(
            @PathVariable Long id,
            @Valid @RequestBody AudiobookDto audiobookDto) {

        Audiobook entityToUpdate = mapper.toEntity(audiobookDto);
        Audiobook updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAudiobook(@PathVariable Long id) {
        return doDelete(id);
    }
}