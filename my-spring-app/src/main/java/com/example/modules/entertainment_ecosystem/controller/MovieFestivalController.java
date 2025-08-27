package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MovieFestivalDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieFestivalSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MovieFestival;
import com.example.modules.entertainment_ecosystem.mapper.MovieFestivalMapper;
import com.example.modules.entertainment_ecosystem.service.MovieFestivalService;
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
 * Controller for managing MovieFestival entities.
 */
@RestController
@RequestMapping("/api/moviefestivals")
public class MovieFestivalController extends BaseController<MovieFestival, MovieFestivalDto, MovieFestivalSimpleDto> {

    public MovieFestivalController(MovieFestivalService moviefestivalService,
                                    MovieFestivalMapper moviefestivalMapper) {
        super(moviefestivalService, moviefestivalMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MovieFestivalDto>> getAllMovieFestivals(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MovieFestivalDto>> searchMovieFestivals(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MovieFestival.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieFestivalDto> getMovieFestivalById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MovieFestivalDto> createMovieFestival(
            @Valid @RequestBody MovieFestivalDto moviefestivalDto,
            UriComponentsBuilder uriBuilder) {

        MovieFestival entity = mapper.toEntity(moviefestivalDto);
        MovieFestival saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/moviefestivals/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MovieFestivalDto>> createAllMovieFestivals(
            @Valid @RequestBody List<MovieFestivalDto> moviefestivalDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MovieFestival> entities = mapper.toEntityList(moviefestivalDtoList);
        List<MovieFestival> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/moviefestivals").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieFestivalDto> updateMovieFestival(
            @PathVariable Long id,
            @Valid @RequestBody MovieFestivalDto moviefestivalDto) {

        MovieFestival entityToUpdate = mapper.toEntity(moviefestivalDto);
        MovieFestival updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieFestival(@PathVariable Long id) {
        return doDelete(id);
    }
}