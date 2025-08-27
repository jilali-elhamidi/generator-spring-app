package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MovieFormatDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieFormatSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MovieFormat;
import com.example.modules.entertainment_ecosystem.mapper.MovieFormatMapper;
import com.example.modules.entertainment_ecosystem.service.MovieFormatService;
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
 * Controller for managing MovieFormat entities.
 */
@RestController
@RequestMapping("/api/movieformats")
public class MovieFormatController extends BaseController<MovieFormat, MovieFormatDto, MovieFormatSimpleDto> {

    public MovieFormatController(MovieFormatService movieformatService,
                                    MovieFormatMapper movieformatMapper) {
        super(movieformatService, movieformatMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MovieFormatDto>> getAllMovieFormats(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MovieFormatDto>> searchMovieFormats(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MovieFormat.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieFormatDto> getMovieFormatById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MovieFormatDto> createMovieFormat(
            @Valid @RequestBody MovieFormatDto movieformatDto,
            UriComponentsBuilder uriBuilder) {

        MovieFormat entity = mapper.toEntity(movieformatDto);
        MovieFormat saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/movieformats/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MovieFormatDto>> createAllMovieFormats(
            @Valid @RequestBody List<MovieFormatDto> movieformatDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MovieFormat> entities = mapper.toEntityList(movieformatDtoList);
        List<MovieFormat> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/movieformats").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieFormatDto> updateMovieFormat(
            @PathVariable Long id,
            @Valid @RequestBody MovieFormatDto movieformatDto) {

        MovieFormat entityToUpdate = mapper.toEntity(movieformatDto);
        MovieFormat updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieFormat(@PathVariable Long id) {
        return doDelete(id);
    }
}