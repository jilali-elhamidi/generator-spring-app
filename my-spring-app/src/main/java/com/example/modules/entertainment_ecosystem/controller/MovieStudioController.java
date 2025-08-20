package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MovieStudioDto;
import com.example.modules.entertainment_ecosystem.model.MovieStudio;
import com.example.modules.entertainment_ecosystem.mapper.MovieStudioMapper;
import com.example.modules.entertainment_ecosystem.service.MovieStudioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/moviestudios")
public class MovieStudioController {

    private final MovieStudioService moviestudioService;
    private final MovieStudioMapper moviestudioMapper;

    public MovieStudioController(MovieStudioService moviestudioService,
                                    MovieStudioMapper moviestudioMapper) {
        this.moviestudioService = moviestudioService;
        this.moviestudioMapper = moviestudioMapper;
    }

    @GetMapping
    public ResponseEntity<List<MovieStudioDto>> getAllMovieStudios() {
        List<MovieStudio> entities = moviestudioService.findAll();
        return ResponseEntity.ok(moviestudioMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieStudioDto> getMovieStudioById(@PathVariable Long id) {
        return moviestudioService.findById(id)
                .map(moviestudioMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MovieStudioDto> createMovieStudio(
            @Valid @RequestBody MovieStudioDto moviestudioDto,
            UriComponentsBuilder uriBuilder) {

        MovieStudio entity = moviestudioMapper.toEntity(moviestudioDto);
        MovieStudio saved = moviestudioService.save(entity);
        URI location = uriBuilder.path("/api/moviestudios/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(moviestudioMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MovieStudioDto> updateMovieStudio(
                @PathVariable Long id,
                @RequestBody MovieStudioDto moviestudioDto) {

                // Transformer le DTO en entity pour le service
                MovieStudio entityToUpdate = moviestudioMapper.toEntity(moviestudioDto);

                // Appel du service update
                MovieStudio updatedEntity = moviestudioService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MovieStudioDto updatedDto = moviestudioMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteMovieStudio(@PathVariable Long id) {
                    boolean deleted = moviestudioService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}