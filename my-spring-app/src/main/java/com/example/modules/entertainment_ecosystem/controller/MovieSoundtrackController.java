package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MovieSoundtrackDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieSoundtrackSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MovieSoundtrack;
import com.example.modules.entertainment_ecosystem.mapper.MovieSoundtrackMapper;
import com.example.modules.entertainment_ecosystem.service.MovieSoundtrackService;
import com.example.core.controller.BaseController;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing MovieSoundtrack entities.
 */
@RestController
@RequestMapping("/api/moviesoundtracks")
public class MovieSoundtrackController extends BaseController<MovieSoundtrack, MovieSoundtrackDto, MovieSoundtrackSimpleDto> {

    public MovieSoundtrackController(MovieSoundtrackService moviesoundtrackService,
                                    MovieSoundtrackMapper moviesoundtrackMapper) {
        super(moviesoundtrackService, moviesoundtrackMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MovieSoundtrackDto>> getAllMovieSoundtracks(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MovieSoundtrackDto>> searchMovieSoundtracks(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MovieSoundtrack.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieSoundtrackDto> getMovieSoundtrackById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MovieSoundtrackDto> createMovieSoundtrack(
            @Valid @RequestBody MovieSoundtrackDto moviesoundtrackDto,
            UriComponentsBuilder uriBuilder) {

        MovieSoundtrack entity = mapper.toEntity(moviesoundtrackDto);
        MovieSoundtrack saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/moviesoundtracks/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MovieSoundtrackDto>> createAllMovieSoundtracks(
            @Valid @RequestBody List<MovieSoundtrackDto> moviesoundtrackDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MovieSoundtrack> entities = mapper.toEntityList(moviesoundtrackDtoList);
        List<MovieSoundtrack> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/moviesoundtracks").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieSoundtrackDto> updateMovieSoundtrack(
            @PathVariable Long id,
            @Valid @RequestBody MovieSoundtrackDto moviesoundtrackDto) {

        MovieSoundtrack entityToUpdate = mapper.toEntity(moviesoundtrackDto);
        MovieSoundtrack updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieSoundtrack(@PathVariable Long id) {
        return doDelete(id);
    }
}