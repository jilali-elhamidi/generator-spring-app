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
        URI location = uriBuilder.path("/api/movies/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(movieMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieDto movieDto) {

        try {
            Movie updatedEntity = movieService.update(
                    id,
                    movieMapper.toEntity(movieDto)
            );
            return ResponseEntity.ok(movieMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}