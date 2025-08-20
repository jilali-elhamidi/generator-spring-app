package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.AudiobookChapterDto;
import com.example.modules.entertainment_ecosystem.model.AudiobookChapter;
import com.example.modules.entertainment_ecosystem.mapper.AudiobookChapterMapper;
import com.example.modules.entertainment_ecosystem.service.AudiobookChapterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/audiobookchapters")
public class AudiobookChapterController {

    private final AudiobookChapterService audiobookchapterService;
    private final AudiobookChapterMapper audiobookchapterMapper;

    public AudiobookChapterController(AudiobookChapterService audiobookchapterService,
                                    AudiobookChapterMapper audiobookchapterMapper) {
        this.audiobookchapterService = audiobookchapterService;
        this.audiobookchapterMapper = audiobookchapterMapper;
    }

    @GetMapping
    public ResponseEntity<List<AudiobookChapterDto>> getAllAudiobookChapters() {
        List<AudiobookChapter> entities = audiobookchapterService.findAll();
        return ResponseEntity.ok(audiobookchapterMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AudiobookChapterDto> getAudiobookChapterById(@PathVariable Long id) {
        return audiobookchapterService.findById(id)
                .map(audiobookchapterMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AudiobookChapterDto> createAudiobookChapter(
            @Valid @RequestBody AudiobookChapterDto audiobookchapterDto,
            UriComponentsBuilder uriBuilder) {

        AudiobookChapter entity = audiobookchapterMapper.toEntity(audiobookchapterDto);
        AudiobookChapter saved = audiobookchapterService.save(entity);
        URI location = uriBuilder.path("/api/audiobookchapters/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(audiobookchapterMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<AudiobookChapterDto> updateAudiobookChapter(
                @PathVariable Long id,
                @RequestBody AudiobookChapterDto audiobookchapterDto) {

                // Transformer le DTO en entity pour le service
                AudiobookChapter entityToUpdate = audiobookchapterMapper.toEntity(audiobookchapterDto);

                // Appel du service update
                AudiobookChapter updatedEntity = audiobookchapterService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                AudiobookChapterDto updatedDto = audiobookchapterMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteAudiobookChapter(@PathVariable Long id) {
                    boolean deleted = audiobookchapterService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}