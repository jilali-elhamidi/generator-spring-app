package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.VideoGameDto;
import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.mapper.VideoGameMapper;
import com.example.modules.entertainment_ecosystem.service.VideoGameService;
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
 * Controller for managing VideoGame entities.
 */
@RestController
@RequestMapping("/api/videogames")
public class VideoGameController extends BaseController<VideoGame, VideoGameDto, VideoGameSimpleDto> {

    public VideoGameController(VideoGameService videogameService,
                                    VideoGameMapper videogameMapper) {
        super(videogameService, videogameMapper);
    }

    @GetMapping
    public ResponseEntity<Page<VideoGameDto>> getAllVideoGames(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<VideoGameDto>> searchVideoGames(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(VideoGame.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoGameDto> getVideoGameById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<VideoGameDto> createVideoGame(
            @Valid @RequestBody VideoGameDto videogameDto,
            UriComponentsBuilder uriBuilder) {

        VideoGame entity = mapper.toEntity(videogameDto);
        VideoGame saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/videogames/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<VideoGameDto>> createAllVideoGames(
            @Valid @RequestBody List<VideoGameDto> videogameDtoList,
            UriComponentsBuilder uriBuilder) {

        List<VideoGame> entities = mapper.toEntityList(videogameDtoList);
        List<VideoGame> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/videogames").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoGameDto> updateVideoGame(
            @PathVariable Long id,
            @Valid @RequestBody VideoGameDto videogameDto) {

        VideoGame entityToUpdate = mapper.toEntity(videogameDto);
        VideoGame updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoGame(@PathVariable Long id) {
        return doDelete(id);
    }
}