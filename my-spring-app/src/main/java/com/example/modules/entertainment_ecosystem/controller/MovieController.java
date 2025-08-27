package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MovieDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.mapper.MovieMapper;
import com.example.modules.entertainment_ecosystem.service.MovieService;
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
 * Controller for managing Movie entities.
 */
@RestController
@RequestMapping("/api/movies")
public class MovieController extends BaseController<Movie, MovieDto, MovieSimpleDto> {

    public MovieController(MovieService movieService,
                                    MovieMapper movieMapper) {
        super(movieService, movieMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MovieDto>> getAllMovies(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MovieDto>> searchMovies(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Movie.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(
            @Valid @RequestBody MovieDto movieDto,
            UriComponentsBuilder uriBuilder) {

        Movie entity = mapper.toEntity(movieDto);
        Movie saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/movies/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MovieDto>> createAllMovies(
            @Valid @RequestBody List<MovieDto> movieDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Movie> entities = mapper.toEntityList(movieDtoList);
        List<Movie> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/movies").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieDto movieDto) {

        Movie entityToUpdate = mapper.toEntity(movieDto);
        Movie updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        return doDelete(id);
    }
}