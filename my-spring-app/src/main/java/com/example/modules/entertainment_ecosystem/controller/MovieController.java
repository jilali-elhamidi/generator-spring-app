package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MovieDto;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.mapper.MovieMapper;
import com.example.modules.entertainment_ecosystem.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;

    public MovieController(MovieService movieService,
                                    MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        List<Movie> entities = movieService.findAll();
        return ResponseEntity.ok(movieMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        return movieService.findById(id)
                .map(movieMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(
            @Valid @RequestBody MovieDto movieDto,
            UriComponentsBuilder uriBuilder) {

        Movie entity = movieMapper.toEntity(movieDto);
        Movie saved = movieService.save(entity);

        URI location = uriBuilder
                                .path("/api/movies/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(movieMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MovieDto>> createAllMovies(
            @Valid @RequestBody List<MovieDto> movieDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Movie> entities = movieMapper.toEntityList(movieDtoList);
        List<Movie> savedEntities = movieService.saveAll(entities);

        URI location = uriBuilder.path("/api/movies").build().toUri();

        return ResponseEntity.created(location).body(movieMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieDto movieDto) {


        Movie entityToUpdate = movieMapper.toEntity(movieDto);
        Movie updatedEntity = movieService.update(id, entityToUpdate);

        return ResponseEntity.ok(movieMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        boolean deleted = movieService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}