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
                @RequestBody GenreDto genreDto) {

                // Transformer le DTO en entity pour le service
                Genre entityToUpdate = genreMapper.toEntity(genreDto);

                // Appel du service update
                Genre updatedEntity = genreService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                GenreDto updatedDto = genreMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}