package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MusicGenreCategoryDto;
import com.example.modules.entertainment_ecosystem.model.MusicGenreCategory;
import com.example.modules.entertainment_ecosystem.mapper.MusicGenreCategoryMapper;
import com.example.modules.entertainment_ecosystem.service.MusicGenreCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/musicgenrecategorys")
public class MusicGenreCategoryController {

    private final MusicGenreCategoryService musicgenrecategoryService;
    private final MusicGenreCategoryMapper musicgenrecategoryMapper;

    public MusicGenreCategoryController(MusicGenreCategoryService musicgenrecategoryService,
                                    MusicGenreCategoryMapper musicgenrecategoryMapper) {
        this.musicgenrecategoryService = musicgenrecategoryService;
        this.musicgenrecategoryMapper = musicgenrecategoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<MusicGenreCategoryDto>> getAllMusicGenreCategorys() {
        List<MusicGenreCategory> entities = musicgenrecategoryService.findAll();
        return ResponseEntity.ok(musicgenrecategoryMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicGenreCategoryDto> getMusicGenreCategoryById(@PathVariable Long id) {
        return musicgenrecategoryService.findById(id)
                .map(musicgenrecategoryMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MusicGenreCategoryDto> createMusicGenreCategory(
            @Valid @RequestBody MusicGenreCategoryDto musicgenrecategoryDto,
            UriComponentsBuilder uriBuilder) {

        MusicGenreCategory entity = musicgenrecategoryMapper.toEntity(musicgenrecategoryDto);
        MusicGenreCategory saved = musicgenrecategoryService.save(entity);
        URI location = uriBuilder.path("/api/musicgenrecategorys/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(musicgenrecategoryMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MusicGenreCategoryDto> updateMusicGenreCategory(
                @PathVariable Long id,
                @RequestBody MusicGenreCategoryDto musicgenrecategoryDto) {

                // Transformer le DTO en entity pour le service
                MusicGenreCategory entityToUpdate = musicgenrecategoryMapper.toEntity(musicgenrecategoryDto);

                // Appel du service update
                MusicGenreCategory updatedEntity = musicgenrecategoryService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MusicGenreCategoryDto updatedDto = musicgenrecategoryMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteMusicGenreCategory(@PathVariable Long id) {
                    boolean deleted = musicgenrecategoryService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}