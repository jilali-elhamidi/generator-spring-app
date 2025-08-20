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
        URI location = uriBuilder.path("/api/moviefestivals/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(moviefestivalMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MovieFestivalDto> updateMovieFestival(
                @PathVariable Long id,
                @RequestBody MovieFestivalDto moviefestivalDto) {

                // Transformer le DTO en entity pour le service
                MovieFestival entityToUpdate = moviefestivalMapper.toEntity(moviefestivalDto);

                // Appel du service update
                MovieFestival updatedEntity = moviefestivalService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MovieFestivalDto updatedDto = moviefestivalMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteMovieFestival(@PathVariable Long id) {
                    boolean deleted = moviefestivalService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}