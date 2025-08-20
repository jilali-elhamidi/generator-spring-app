package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MovieFestivalDto;
import com.example.modules.entertainment_ecosystem.model.MovieFestival;
import com.example.modules.entertainment_ecosystem.mapper.MovieFestivalMapper;
import com.example.modules.entertainment_ecosystem.service.MovieFestivalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/moviefestivals")
public class MovieFestivalController {

    private final MovieFestivalService moviefestivalService;
    private final MovieFestivalMapper moviefestivalMapper;

    public MovieFestivalController(MovieFestivalService moviefestivalService,
                                    MovieFestivalMapper moviefestivalMapper) {
        this.moviefestivalService = moviefestivalService;
        this.moviefestivalMapper = moviefestivalMapper;
    }

    @GetMapping
    public ResponseEntity<List<MovieFestivalDto>> getAllMovieFestivals() {
        List<MovieFestival> entities = moviefestivalService.findAll();
        return ResponseEntity.ok(moviefestivalMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieFestivalDto> getMovieFestivalById(@PathVariable Long id) {
        return moviefestivalService.findById(id)
                .map(moviefestivalMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MovieFestivalDto> createMovieFestival(
            @Valid @RequestBody MovieFestivalDto moviefestivalDto,
            UriComponentsBuilder uriBuilder) {

        MovieFestival entity = moviefestivalMapper.toEntity(moviefestivalDto);
        MovieFestival saved = moviefestivalService.save(entity);

        URI location = uriBuilder
                                .path("/api/moviefestivals/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(moviefestivalMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieFestivalDto> updateMovieFestival(
            @PathVariable Long id,
            @Valid @RequestBody MovieFestivalDto moviefestivalDto) {


        MovieFestival entityToUpdate = moviefestivalMapper.toEntity(moviefestivalDto);
        MovieFestival updatedEntity = moviefestivalService.update(id, entityToUpdate);

        return ResponseEntity.ok(moviefestivalMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieFestival(@PathVariable Long id) {
        boolean deleted = moviefestivalService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}