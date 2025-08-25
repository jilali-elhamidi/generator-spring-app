package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MusicFormatDto;
import com.example.modules.entertainment_ecosystem.model.MusicFormat;
import com.example.modules.entertainment_ecosystem.mapper.MusicFormatMapper;
import com.example.modules.entertainment_ecosystem.service.MusicFormatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/musicformats")
public class MusicFormatController {

    private final MusicFormatService musicformatService;
    private final MusicFormatMapper musicformatMapper;

    public MusicFormatController(MusicFormatService musicformatService,
                                    MusicFormatMapper musicformatMapper) {
        this.musicformatService = musicformatService;
        this.musicformatMapper = musicformatMapper;
    }

    @GetMapping
    public ResponseEntity<List<MusicFormatDto>> getAllMusicFormats() {
        List<MusicFormat> entities = musicformatService.findAll();
        return ResponseEntity.ok(musicformatMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicFormatDto> getMusicFormatById(@PathVariable Long id) {
        return musicformatService.findById(id)
                .map(musicformatMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MusicFormatDto> createMusicFormat(
            @Valid @RequestBody MusicFormatDto musicformatDto,
            UriComponentsBuilder uriBuilder) {

        MusicFormat entity = musicformatMapper.toEntity(musicformatDto);
        MusicFormat saved = musicformatService.save(entity);

        URI location = uriBuilder
                                .path("/api/musicformats/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(musicformatMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MusicFormatDto>> createAllMusicFormats(
            @Valid @RequestBody List<MusicFormatDto> musicformatDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MusicFormat> entities = musicformatMapper.toEntityList(musicformatDtoList);
        List<MusicFormat> savedEntities = musicformatService.saveAll(entities);

        URI location = uriBuilder.path("/api/musicformats").build().toUri();

        return ResponseEntity.created(location).body(musicformatMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicFormatDto> updateMusicFormat(
            @PathVariable Long id,
            @Valid @RequestBody MusicFormatDto musicformatDto) {


        MusicFormat entityToUpdate = musicformatMapper.toEntity(musicformatDto);
        MusicFormat updatedEntity = musicformatService.update(id, entityToUpdate);

        return ResponseEntity.ok(musicformatMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusicFormat(@PathVariable Long id) {
        boolean deleted = musicformatService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}