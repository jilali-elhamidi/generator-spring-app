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

        URI location = uriBuilder
                                .path("/api/moviesoundtracks/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(moviesoundtrackMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MovieSoundtrackDto>> createAllMovieSoundtracks(
            @Valid @RequestBody List<MovieSoundtrackDto> moviesoundtrackDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MovieSoundtrack> entities = moviesoundtrackMapper.toEntityList(moviesoundtrackDtoList);
        List<MovieSoundtrack> savedEntities = moviesoundtrackService.saveAll(entities);

        URI location = uriBuilder.path("/api/moviesoundtracks").build().toUri();

        return ResponseEntity.created(location).body(moviesoundtrackMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieSoundtrackDto> updateMovieSoundtrack(
            @PathVariable Long id,
            @Valid @RequestBody MovieSoundtrackDto moviesoundtrackDto) {


        MovieSoundtrack entityToUpdate = moviesoundtrackMapper.toEntity(moviesoundtrackDto);
        MovieSoundtrack updatedEntity = moviesoundtrackService.update(id, entityToUpdate);

        return ResponseEntity.ok(moviesoundtrackMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieSoundtrack(@PathVariable Long id) {
        boolean deleted = moviesoundtrackService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}