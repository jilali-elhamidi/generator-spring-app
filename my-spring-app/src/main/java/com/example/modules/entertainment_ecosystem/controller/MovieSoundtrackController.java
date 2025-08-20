package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MovieSoundtrackDto;
import com.example.modules.entertainment_ecosystem.model.MovieSoundtrack;
import com.example.modules.entertainment_ecosystem.mapper.MovieSoundtrackMapper;
import com.example.modules.entertainment_ecosystem.service.MovieSoundtrackService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/moviesoundtracks")
public class MovieSoundtrackController {

    private final MovieSoundtrackService moviesoundtrackService;
    private final MovieSoundtrackMapper moviesoundtrackMapper;

    public MovieSoundtrackController(MovieSoundtrackService moviesoundtrackService,
                                    MovieSoundtrackMapper moviesoundtrackMapper) {
        this.moviesoundtrackService = moviesoundtrackService;
        this.moviesoundtrackMapper = moviesoundtrackMapper;
    }

    @GetMapping
    public ResponseEntity<List<MovieSoundtrackDto>> getAllMovieSoundtracks() {
        List<MovieSoundtrack> entities = moviesoundtrackService.findAll();
        return ResponseEntity.ok(moviesoundtrackMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieSoundtrackDto> getMovieSoundtrackById(@PathVariable Long id) {
        return moviesoundtrackService.findById(id)
                .map(moviesoundtrackMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MovieSoundtrackDto> createMovieSoundtrack(
            @Valid @RequestBody MovieSoundtrackDto moviesoundtrackDto,
            UriComponentsBuilder uriBuilder) {

        MovieSoundtrack entity = moviesoundtrackMapper.toEntity(moviesoundtrackDto);
        MovieSoundtrack saved = moviesoundtrackService.save(entity);
        URI location = uriBuilder.path("/api/moviesoundtracks/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(moviesoundtrackMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MovieSoundtrackDto> updateMovieSoundtrack(
                @PathVariable Long id,
                @RequestBody MovieSoundtrackDto moviesoundtrackDto) {

                // Transformer le DTO en entity pour le service
                MovieSoundtrack entityToUpdate = moviesoundtrackMapper.toEntity(moviesoundtrackDto);

                // Appel du service update
                MovieSoundtrack updatedEntity = moviesoundtrackService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MovieSoundtrackDto updatedDto = moviesoundtrackMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteMovieSoundtrack(@PathVariable Long id) {
                    boolean deleted = moviesoundtrackService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}