package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MusicLabelDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicLabelSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MusicLabel;
import com.example.modules.entertainment_ecosystem.mapper.MusicLabelMapper;
import com.example.modules.entertainment_ecosystem.service.MusicLabelService;
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
 * Controller for managing MusicLabel entities.
 */
@RestController
@RequestMapping("/api/musiclabels")
public class MusicLabelController extends BaseController<MusicLabel, MusicLabelDto, MusicLabelSimpleDto> {

    public MusicLabelController(MusicLabelService musiclabelService,
                                    MusicLabelMapper musiclabelMapper) {
        super(musiclabelService, musiclabelMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MusicLabelDto>> getAllMusicLabels(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MusicLabelDto>> searchMusicLabels(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MusicLabel.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicLabelDto> getMusicLabelById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MusicLabelDto> createMusicLabel(
            @Valid @RequestBody MusicLabelDto musiclabelDto,
            UriComponentsBuilder uriBuilder) {

        MusicLabel entity = mapper.toEntity(musiclabelDto);
        MusicLabel saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/musiclabels/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MusicLabelDto>> createAllMusicLabels(
            @Valid @RequestBody List<MusicLabelDto> musiclabelDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MusicLabel> entities = mapper.toEntityList(musiclabelDtoList);
        List<MusicLabel> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/musiclabels").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicLabelDto> updateMusicLabel(
            @PathVariable Long id,
            @Valid @RequestBody MusicLabelDto musiclabelDto) {

        MusicLabel entityToUpdate = mapper.toEntity(musiclabelDto);
        MusicLabel updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusicLabel(@PathVariable Long id) {
        return doDelete(id);
    }
}