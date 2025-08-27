package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MovieMerchandiseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieMerchandiseSimpleDto;
import com.example.modules.entertainment_ecosystem.model.MovieMerchandise;
import com.example.modules.entertainment_ecosystem.mapper.MovieMerchandiseMapper;
import com.example.modules.entertainment_ecosystem.service.MovieMerchandiseService;
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
 * Controller for managing MovieMerchandise entities.
 */
@RestController
@RequestMapping("/api/moviemerchandises")
public class MovieMerchandiseController extends BaseController<MovieMerchandise, MovieMerchandiseDto, MovieMerchandiseSimpleDto> {

    public MovieMerchandiseController(MovieMerchandiseService moviemerchandiseService,
                                    MovieMerchandiseMapper moviemerchandiseMapper) {
        super(moviemerchandiseService, moviemerchandiseMapper);
    }

    @GetMapping
    public ResponseEntity<Page<MovieMerchandiseDto>> getAllMovieMerchandises(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MovieMerchandiseDto>> searchMovieMerchandises(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(MovieMerchandise.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieMerchandiseDto> getMovieMerchandiseById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<MovieMerchandiseDto> createMovieMerchandise(
            @Valid @RequestBody MovieMerchandiseDto moviemerchandiseDto,
            UriComponentsBuilder uriBuilder) {

        MovieMerchandise entity = mapper.toEntity(moviemerchandiseDto);
        MovieMerchandise saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/moviemerchandises/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MovieMerchandiseDto>> createAllMovieMerchandises(
            @Valid @RequestBody List<MovieMerchandiseDto> moviemerchandiseDtoList,
            UriComponentsBuilder uriBuilder) {

        List<MovieMerchandise> entities = mapper.toEntityList(moviemerchandiseDtoList);
        List<MovieMerchandise> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/moviemerchandises").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieMerchandiseDto> updateMovieMerchandise(
            @PathVariable Long id,
            @Valid @RequestBody MovieMerchandiseDto moviemerchandiseDto) {

        MovieMerchandise entityToUpdate = mapper.toEntity(moviemerchandiseDto);
        MovieMerchandise updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieMerchandise(@PathVariable Long id) {
        return doDelete(id);
    }
}