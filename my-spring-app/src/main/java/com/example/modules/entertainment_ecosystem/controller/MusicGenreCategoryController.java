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

        URI location = uriBuilder
                                .path("/api/musicgenrecategorys/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(musicgenrecategoryMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicGenreCategoryDto> updateMusicGenreCategory(
            @PathVariable Long id,
            @Valid @RequestBody MusicGenreCategoryDto musicgenrecategoryDto) {


        MusicGenreCategory entityToUpdate = musicgenrecategoryMapper.toEntity(musicgenrecategoryDto);
        MusicGenreCategory updatedEntity = musicgenrecategoryService.update(id, entityToUpdate);

        return ResponseEntity.ok(musicgenrecategoryMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusicGenreCategory(@PathVariable Long id) {
        boolean deleted = musicgenrecategoryService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}