package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GenreDto;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.mapper.GenreMapper;
import com.example.modules.entertainment_ecosystem.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;
    private final GenreMapper genreMapper;

    public GenreController(GenreService genreService,
                                    GenreMapper genreMapper) {
        this.genreService = genreService;
        this.genreMapper = genreMapper;
    }

    @GetMapping
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        List<Genre> entities = genreService.findAll();
        return ResponseEntity.ok(genreMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable Long id) {
        return genreService.findById(id)
                .map(genreMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GenreDto> createGenre(
            @Valid @RequestBody GenreDto genreDto,
            UriComponentsBuilder uriBuilder) {

        Genre entity = genreMapper.toEntity(genreDto);
        Genre saved = genreService.save(entity);
        URI location = uriBuilder.path("/api/genres/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(genreMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<GenreDto> updateGenre(
                @PathVariable Long id,
                @Valid @RequestBody GenreDto genreDto) {

                try {
                // Récupérer l'entité existante avec Optional
                Genre existing = genreService.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                genreMapper.updateEntityFromDto(genreDto, existing);

                // Sauvegarde
                Genre updatedEntity = genreService.save(existing);

                return ResponseEntity.ok(genreMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}