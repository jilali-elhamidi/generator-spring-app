package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TVShowMerchandiseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TVShowMerchandiseSimpleDto;
import com.example.modules.entertainment_ecosystem.model.TVShowMerchandise;
import com.example.modules.entertainment_ecosystem.mapper.TVShowMerchandiseMapper;
import com.example.modules.entertainment_ecosystem.service.TVShowMerchandiseService;
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
 * Controller for managing TVShowMerchandise entities.
 */
@RestController
@RequestMapping("/api/tvshowmerchandises")
public class TVShowMerchandiseController extends BaseController<TVShowMerchandise, TVShowMerchandiseDto, TVShowMerchandiseSimpleDto> {

    public TVShowMerchandiseController(TVShowMerchandiseService tvshowmerchandiseService,
                                    TVShowMerchandiseMapper tvshowmerchandiseMapper) {
        super(tvshowmerchandiseService, tvshowmerchandiseMapper);
    }

    @GetMapping
    public ResponseEntity<Page<TVShowMerchandiseDto>> getAllTVShowMerchandises(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TVShowMerchandiseDto>> searchTVShowMerchandises(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(TVShowMerchandise.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TVShowMerchandiseDto> getTVShowMerchandiseById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<TVShowMerchandiseDto> createTVShowMerchandise(
            @Valid @RequestBody TVShowMerchandiseDto tvshowmerchandiseDto,
            UriComponentsBuilder uriBuilder) {

        TVShowMerchandise entity = mapper.toEntity(tvshowmerchandiseDto);
        TVShowMerchandise saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/tvshowmerchandises/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TVShowMerchandiseDto>> createAllTVShowMerchandises(
            @Valid @RequestBody List<TVShowMerchandiseDto> tvshowmerchandiseDtoList,
            UriComponentsBuilder uriBuilder) {

        List<TVShowMerchandise> entities = mapper.toEntityList(tvshowmerchandiseDtoList);
        List<TVShowMerchandise> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/tvshowmerchandises").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TVShowMerchandiseDto> updateTVShowMerchandise(
            @PathVariable Long id,
            @Valid @RequestBody TVShowMerchandiseDto tvshowmerchandiseDto) {

        TVShowMerchandise entityToUpdate = mapper.toEntity(tvshowmerchandiseDto);
        TVShowMerchandise updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTVShowMerchandise(@PathVariable Long id) {
        return doDelete(id);
    }
}