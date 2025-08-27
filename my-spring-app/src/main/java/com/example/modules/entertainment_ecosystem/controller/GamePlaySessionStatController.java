package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GamePlaySessionStatDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GamePlaySessionStatSimpleDto;
import com.example.modules.entertainment_ecosystem.model.GamePlaySessionStat;
import com.example.modules.entertainment_ecosystem.mapper.GamePlaySessionStatMapper;
import com.example.modules.entertainment_ecosystem.service.GamePlaySessionStatService;
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
 * Controller for managing GamePlaySessionStat entities.
 */
@RestController
@RequestMapping("/api/gameplaysessionstats")
public class GamePlaySessionStatController extends BaseController<GamePlaySessionStat, GamePlaySessionStatDto, GamePlaySessionStatSimpleDto> {

    public GamePlaySessionStatController(GamePlaySessionStatService gameplaysessionstatService,
                                    GamePlaySessionStatMapper gameplaysessionstatMapper) {
        super(gameplaysessionstatService, gameplaysessionstatMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GamePlaySessionStatDto>> getAllGamePlaySessionStats(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GamePlaySessionStatDto>> searchGamePlaySessionStats(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(GamePlaySessionStat.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GamePlaySessionStatDto> getGamePlaySessionStatById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GamePlaySessionStatDto> createGamePlaySessionStat(
            @Valid @RequestBody GamePlaySessionStatDto gameplaysessionstatDto,
            UriComponentsBuilder uriBuilder) {

        GamePlaySessionStat entity = mapper.toEntity(gameplaysessionstatDto);
        GamePlaySessionStat saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/gameplaysessionstats/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GamePlaySessionStatDto>> createAllGamePlaySessionStats(
            @Valid @RequestBody List<GamePlaySessionStatDto> gameplaysessionstatDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GamePlaySessionStat> entities = mapper.toEntityList(gameplaysessionstatDtoList);
        List<GamePlaySessionStat> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/gameplaysessionstats").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GamePlaySessionStatDto> updateGamePlaySessionStat(
            @PathVariable Long id,
            @Valid @RequestBody GamePlaySessionStatDto gameplaysessionstatDto) {

        GamePlaySessionStat entityToUpdate = mapper.toEntity(gameplaysessionstatDto);
        GamePlaySessionStat updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGamePlaySessionStat(@PathVariable Long id) {
        return doDelete(id);
    }
}