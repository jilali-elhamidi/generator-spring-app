package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TVShowDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.mapper.TVShowMapper;
import com.example.modules.entertainment_ecosystem.service.TVShowService;
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
 * Controller for managing TVShow entities.
 */
@RestController
@RequestMapping("/api/tvshows")
public class TVShowController extends BaseController<TVShow, TVShowDto, TVShowSimpleDto> {

    public TVShowController(TVShowService tvshowService,
                                    TVShowMapper tvshowMapper) {
        super(tvshowService, tvshowMapper);
    }

    @GetMapping
    public ResponseEntity<Page<TVShowDto>> getAllTVShows(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TVShowDto>> searchTVShows(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(TVShow.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TVShowDto> getTVShowById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<TVShowDto> createTVShow(
            @Valid @RequestBody TVShowDto tvshowDto,
            UriComponentsBuilder uriBuilder) {

        TVShow entity = mapper.toEntity(tvshowDto);
        TVShow saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/tvshows/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TVShowDto>> createAllTVShows(
            @Valid @RequestBody List<TVShowDto> tvshowDtoList,
            UriComponentsBuilder uriBuilder) {

        List<TVShow> entities = mapper.toEntityList(tvshowDtoList);
        List<TVShow> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/tvshows").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TVShowDto> updateTVShow(
            @PathVariable Long id,
            @Valid @RequestBody TVShowDto tvshowDto) {

        TVShow entityToUpdate = mapper.toEntity(tvshowDto);
        TVShow updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTVShow(@PathVariable Long id) {
        return doDelete(id);
    }
}