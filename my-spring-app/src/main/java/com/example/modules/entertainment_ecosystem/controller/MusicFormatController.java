package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MusicFormatDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicFormatSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MusicFormat;
import com.example.modules.entertainment_ecosystem.mapper.MusicFormatMapper;
import com.example.modules.entertainment_ecosystem.service.MusicFormatService;
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
 * Controller for managing MusicFormat entities.
 */
@RestController
@RequestMapping("/api/musicformats")
public class MusicFormatController extends BaseController<MusicFormat, MusicFormatDto, MusicFormatSimpleDto> {

    public MusicFormatController(MusicFormatService musicformatService,
                                    MusicFormatMapper musicformatMapper) {
        super(musicformatService, musicformatMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MusicFormatDto>> getAllMusicFormats(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MusicFormatDto>> searchMusicFormats(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MusicFormat.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicFormatDto> getMusicFormatById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MusicFormatDto> createMusicFormat(
            @Valid @RequestBody MusicFormatDto musicformatDto,
            UriComponentsBuilder uriBuilder) {

        MusicFormat entity = mapper.toEntity(musicformatDto);
        MusicFormat saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/musicformats/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MusicFormatDto>> createAllMusicFormats(
            @Valid @RequestBody List<MusicFormatDto> musicformatDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MusicFormat> entities = mapper.toEntityList(musicformatDtoList);
        List<MusicFormat> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/musicformats").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicFormatDto> updateMusicFormat(
            @PathVariable Long id,
            @Valid @RequestBody MusicFormatDto musicformatDto) {

        MusicFormat entityToUpdate = mapper.toEntity(musicformatDto);
        MusicFormat updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusicFormat(@PathVariable Long id) {
        return doDelete(id);
    }
}