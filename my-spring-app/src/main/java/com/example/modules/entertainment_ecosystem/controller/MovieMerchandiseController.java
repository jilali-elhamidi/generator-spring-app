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
                @RequestBody MovieMerchandiseDto moviemerchandiseDto) {

                // Transformer le DTO en entity pour le service
                MovieMerchandise entityToUpdate = moviemerchandiseMapper.toEntity(moviemerchandiseDto);

                // Appel du service update
                MovieMerchandise updatedEntity = moviemerchandiseService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MovieMerchandiseDto updatedDto = moviemerchandiseMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteMovieMerchandise(@PathVariable Long id) {
                    boolean deleted = moviemerchandiseService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}