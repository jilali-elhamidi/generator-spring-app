package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GamePlaySessionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GamePlaySessionSimpleDto;
import com.example.modules.entertainment_ecosystem.model.GamePlaySession;
import com.example.modules.entertainment_ecosystem.mapper.GamePlaySessionMapper;
import com.example.modules.entertainment_ecosystem.service.GamePlaySessionService;
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
 * Controller for managing GamePlaySession entities.
 */
@RestController
@RequestMapping("/api/gameplaysessions")
public class GamePlaySessionController extends BaseController<GamePlaySession, GamePlaySessionDto, GamePlaySessionSimpleDto> {

    public GamePlaySessionController(GamePlaySessionService gameplaysessionService,
                                    GamePlaySessionMapper gameplaysessionMapper) {
        super(gameplaysessionService, gameplaysessionMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GamePlaySessionDto>> getAllGamePlaySessions(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GamePlaySessionDto>> searchGamePlaySessions(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(GamePlaySession.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GamePlaySessionDto> getGamePlaySessionById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GamePlaySessionDto> createGamePlaySession(
            @Valid @RequestBody GamePlaySessionDto gameplaysessionDto,
            UriComponentsBuilder uriBuilder) {

        GamePlaySession entity = mapper.toEntity(gameplaysessionDto);
        GamePlaySession saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/gameplaysessions/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GamePlaySessionDto>> createAllGamePlaySessions(
            @Valid @RequestBody List<GamePlaySessionDto> gameplaysessionDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GamePlaySession> entities = mapper.toEntityList(gameplaysessionDtoList);
        List<GamePlaySession> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/gameplaysessions").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GamePlaySessionDto> updateGamePlaySession(
            @PathVariable Long id,
            @Valid @RequestBody GamePlaySessionDto gameplaysessionDto) {

        GamePlaySession entityToUpdate = mapper.toEntity(gameplaysessionDto);
        GamePlaySession updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGamePlaySession(@PathVariable Long id) {
        return doDelete(id);
    }
}