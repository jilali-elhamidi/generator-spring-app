package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MovieFormatDto;
import com.example.modules.entertainment_ecosystem.model.MovieFormat;
import com.example.modules.entertainment_ecosystem.mapper.MovieFormatMapper;
import com.example.modules.entertainment_ecosystem.service.MovieFormatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/movieformats")
public class MovieFormatController {

    private final MovieFormatService movieformatService;
    private final MovieFormatMapper movieformatMapper;

    public MovieFormatController(MovieFormatService movieformatService,
                                    MovieFormatMapper movieformatMapper) {
        this.movieformatService = movieformatService;
        this.movieformatMapper = movieformatMapper;
    }

    @GetMapping
    public ResponseEntity<List<MovieFormatDto>> getAllMovieFormats() {
        List<MovieFormat> entities = movieformatService.findAll();
        return ResponseEntity.ok(movieformatMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieFormatDto> getMovieFormatById(@PathVariable Long id) {
        return movieformatService.findById(id)
                .map(movieformatMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MovieFormatDto> createMovieFormat(
            @Valid @RequestBody MovieFormatDto movieformatDto,
            UriComponentsBuilder uriBuilder) {

        MovieFormat entity = movieformatMapper.toEntity(movieformatDto);
        MovieFormat saved = movieformatService.save(entity);

        URI location = uriBuilder
                                .path("/api/movieformats/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(movieformatMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MovieFormatDto>> createAllMovieFormats(
            @Valid @RequestBody List<MovieFormatDto> movieformatDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MovieFormat> entities = movieformatMapper.toEntityList(movieformatDtoList);
        List<MovieFormat> savedEntities = movieformatService.saveAll(entities);

        URI location = uriBuilder.path("/api/movieformats").build().toUri();

        return ResponseEntity.created(location).body(movieformatMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieFormatDto> updateMovieFormat(
            @PathVariable Long id,
            @Valid @RequestBody MovieFormatDto movieformatDto) {


        MovieFormat entityToUpdate = movieformatMapper.toEntity(movieformatDto);
        MovieFormat updatedEntity = movieformatService.update(id, entityToUpdate);

        return ResponseEntity.ok(movieformatMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieFormat(@PathVariable Long id) {
        boolean deleted = movieformatService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}