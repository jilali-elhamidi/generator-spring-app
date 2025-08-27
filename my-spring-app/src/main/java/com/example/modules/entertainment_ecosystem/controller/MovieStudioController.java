package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MovieStudioDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieStudioSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MovieStudio;
import com.example.modules.entertainment_ecosystem.mapper.MovieStudioMapper;
import com.example.modules.entertainment_ecosystem.service.MovieStudioService;
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
 * Controller for managing MovieStudio entities.
 */
@RestController
@RequestMapping("/api/moviestudios")
public class MovieStudioController extends BaseController<MovieStudio, MovieStudioDto, MovieStudioSimpleDto> {

    public MovieStudioController(MovieStudioService moviestudioService,
                                    MovieStudioMapper moviestudioMapper) {
        super(moviestudioService, moviestudioMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MovieStudioDto>> getAllMovieStudios(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MovieStudioDto>> searchMovieStudios(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MovieStudio.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieStudioDto> getMovieStudioById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MovieStudioDto> createMovieStudio(
            @Valid @RequestBody MovieStudioDto moviestudioDto,
            UriComponentsBuilder uriBuilder) {

        MovieStudio entity = mapper.toEntity(moviestudioDto);
        MovieStudio saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/moviestudios/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MovieStudioDto>> createAllMovieStudios(
            @Valid @RequestBody List<MovieStudioDto> moviestudioDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MovieStudio> entities = mapper.toEntityList(moviestudioDtoList);
        List<MovieStudio> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/moviestudios").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieStudioDto> updateMovieStudio(
            @PathVariable Long id,
            @Valid @RequestBody MovieStudioDto moviestudioDto) {

        MovieStudio entityToUpdate = mapper.toEntity(moviestudioDto);
        MovieStudio updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieStudio(@PathVariable Long id) {
        return doDelete(id);
    }
}