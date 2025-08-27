package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GenreDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GenreSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.mapper.GenreMapper;
import com.example.modules.entertainment_ecosystem.service.GenreService;
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
 * Controller for managing Genre entities.
 */
@RestController
@RequestMapping("/api/genres")
public class GenreController extends BaseController<Genre, GenreDto, GenreSimpleDto> {

    public GenreController(GenreService genreService,
                                    GenreMapper genreMapper) {
        super(genreService, genreMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GenreDto>> getAllGenres(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GenreDto>> searchGenres(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Genre.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GenreDto> createGenre(
            @Valid @RequestBody GenreDto genreDto,
            UriComponentsBuilder uriBuilder) {

        Genre entity = mapper.toEntity(genreDto);
        Genre saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/genres/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GenreDto>> createAllGenres(
            @Valid @RequestBody List<GenreDto> genreDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Genre> entities = mapper.toEntityList(genreDtoList);
        List<Genre> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/genres").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreDto> updateGenre(
            @PathVariable Long id,
            @Valid @RequestBody GenreDto genreDto) {

        Genre entityToUpdate = mapper.toEntity(genreDto);
        Genre updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        return doDelete(id);
    }
}