package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.AudiobookDto;
import com.example.modules.entertainment_ecosystem.model.Audiobook;
import com.example.modules.entertainment_ecosystem.mapper.AudiobookMapper;
import com.example.modules.entertainment_ecosystem.service.AudiobookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/audiobooks")
public class AudiobookController {

    private final AudiobookService audiobookService;
    private final AudiobookMapper audiobookMapper;

    public AudiobookController(AudiobookService audiobookService,
                                    AudiobookMapper audiobookMapper) {
        this.audiobookService = audiobookService;
        this.audiobookMapper = audiobookMapper;
    }

    @GetMapping
    public ResponseEntity<List<AudiobookDto>> getAllAudiobooks() {
        List<Audiobook> entities = audiobookService.findAll();
        return ResponseEntity.ok(audiobookMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AudiobookDto> getAudiobookById(@PathVariable Long id) {
        return audiobookService.findById(id)
                .map(audiobookMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AudiobookDto> createAudiobook(
            @Valid @RequestBody AudiobookDto audiobookDto,
            UriComponentsBuilder uriBuilder) {

        Audiobook entity = audiobookMapper.toEntity(audiobookDto);
        Audiobook saved = audiobookService.save(entity);

        URI location = uriBuilder
                                .path("/api/audiobooks/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(audiobookMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AudiobookDto> updateAudiobook(
            @PathVariable Long id,
            @Valid @RequestBody AudiobookDto audiobookDto) {


        Audiobook entityToUpdate = audiobookMapper.toEntity(audiobookDto);
        Audiobook updatedEntity = audiobookService.update(id, entityToUpdate);

        return ResponseEntity.ok(audiobookMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAudiobook(@PathVariable Long id) {
        boolean deleted = audiobookService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}