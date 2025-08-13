package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MovieMerchandiseDto;
import com.example.modules.entertainment_ecosystem.model.MovieMerchandise;
import com.example.modules.entertainment_ecosystem.mapper.MovieMerchandiseMapper;
import com.example.modules.entertainment_ecosystem.service.MovieMerchandiseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/moviemerchandises")
public class MovieMerchandiseController {

    private final MovieMerchandiseService moviemerchandiseService;
    private final MovieMerchandiseMapper moviemerchandiseMapper;

    public MovieMerchandiseController(MovieMerchandiseService moviemerchandiseService,
                                    MovieMerchandiseMapper moviemerchandiseMapper) {
        this.moviemerchandiseService = moviemerchandiseService;
        this.moviemerchandiseMapper = moviemerchandiseMapper;
    }

    @GetMapping
    public ResponseEntity<List<MovieMerchandiseDto>> getAllMovieMerchandises() {
        List<MovieMerchandise> entities = moviemerchandiseService.findAll();
        return ResponseEntity.ok(moviemerchandiseMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieMerchandiseDto> getMovieMerchandiseById(@PathVariable Long id) {
        return moviemerchandiseService.findById(id)
                .map(moviemerchandiseMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MovieMerchandiseDto> createMovieMerchandise(
            @Valid @RequestBody MovieMerchandiseDto moviemerchandiseDto,
            UriComponentsBuilder uriBuilder) {

        MovieMerchandise entity = moviemerchandiseMapper.toEntity(moviemerchandiseDto);
        MovieMerchandise saved = moviemerchandiseService.save(entity);
        URI location = uriBuilder.path("/api/moviemerchandises/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(moviemerchandiseMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieMerchandiseDto> updateMovieMerchandise(
            @PathVariable Long id,
            @Valid @RequestBody MovieMerchandiseDto moviemerchandiseDto) {

        try {
            MovieMerchandise updatedEntity = moviemerchandiseService.update(
                    id,
                    moviemerchandiseMapper.toEntity(moviemerchandiseDto)
            );
            return ResponseEntity.ok(moviemerchandiseMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieMerchandise(@PathVariable Long id) {
        moviemerchandiseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}