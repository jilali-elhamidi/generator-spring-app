package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TVShowStudioDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TVShowStudioSimpleDto;
import com.example.modules.entertainment_ecosystem.model.TVShowStudio;
import com.example.modules.entertainment_ecosystem.mapper.TVShowStudioMapper;
import com.example.modules.entertainment_ecosystem.service.TVShowStudioService;
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
 * Controller for managing TVShowStudio entities.
 */
@RestController
@RequestMapping("/api/tvshowstudios")
public class TVShowStudioController extends BaseController<TVShowStudio, TVShowStudioDto, TVShowStudioSimpleDto> {

    public TVShowStudioController(TVShowStudioService tvshowstudioService,
                                    TVShowStudioMapper tvshowstudioMapper) {
        super(tvshowstudioService, tvshowstudioMapper);
    }

    @GetMapping
    public ResponseEntity<Page<TVShowStudioDto>> getAllTVShowStudios(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TVShowStudioDto>> searchTVShowStudios(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(TVShowStudio.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TVShowStudioDto> getTVShowStudioById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<TVShowStudioDto> createTVShowStudio(
            @Valid @RequestBody TVShowStudioDto tvshowstudioDto,
            UriComponentsBuilder uriBuilder) {

        TVShowStudio entity = mapper.toEntity(tvshowstudioDto);
        TVShowStudio saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/tvshowstudios/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TVShowStudioDto>> createAllTVShowStudios(
            @Valid @RequestBody List<TVShowStudioDto> tvshowstudioDtoList,
            UriComponentsBuilder uriBuilder) {

        List<TVShowStudio> entities = mapper.toEntityList(tvshowstudioDtoList);
        List<TVShowStudio> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/tvshowstudios").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TVShowStudioDto> updateTVShowStudio(
            @PathVariable Long id,
            @Valid @RequestBody TVShowStudioDto tvshowstudioDto) {

        TVShowStudio entityToUpdate = mapper.toEntity(tvshowstudioDto);
        TVShowStudio updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTVShowStudio(@PathVariable Long id) {
        return doDelete(id);
    }
}