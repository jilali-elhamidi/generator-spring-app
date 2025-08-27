package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MusicGenreCategoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicGenreCategorySimpleDto;
import com.example.modules.entertainment_ecosystem.model.MusicGenreCategory;
import com.example.modules.entertainment_ecosystem.mapper.MusicGenreCategoryMapper;
import com.example.modules.entertainment_ecosystem.service.MusicGenreCategoryService;
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
 * Controller for managing MusicGenreCategory entities.
 */
@RestController
@RequestMapping("/api/musicgenrecategorys")
public class MusicGenreCategoryController extends BaseController<MusicGenreCategory, MusicGenreCategoryDto, MusicGenreCategorySimpleDto> {

    public MusicGenreCategoryController(MusicGenreCategoryService musicgenrecategoryService,
                                    MusicGenreCategoryMapper musicgenrecategoryMapper) {
        super(musicgenrecategoryService, musicgenrecategoryMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MusicGenreCategoryDto>> getAllMusicGenreCategorys(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MusicGenreCategoryDto>> searchMusicGenreCategorys(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MusicGenreCategory.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicGenreCategoryDto> getMusicGenreCategoryById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MusicGenreCategoryDto> createMusicGenreCategory(
            @Valid @RequestBody MusicGenreCategoryDto musicgenrecategoryDto,
            UriComponentsBuilder uriBuilder) {

        MusicGenreCategory entity = mapper.toEntity(musicgenrecategoryDto);
        MusicGenreCategory saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/musicgenrecategorys/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MusicGenreCategoryDto>> createAllMusicGenreCategorys(
            @Valid @RequestBody List<MusicGenreCategoryDto> musicgenrecategoryDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MusicGenreCategory> entities = mapper.toEntityList(musicgenrecategoryDtoList);
        List<MusicGenreCategory> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/musicgenrecategorys").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicGenreCategoryDto> updateMusicGenreCategory(
            @PathVariable Long id,
            @Valid @RequestBody MusicGenreCategoryDto musicgenrecategoryDto) {

        MusicGenreCategory entityToUpdate = mapper.toEntity(musicgenrecategoryDto);
        MusicGenreCategory updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusicGenreCategory(@PathVariable Long id) {
        return doDelete(id);
    }
}