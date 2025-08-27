package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameSessionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameSessionSimpleDto;
import com.example.modules.entertainment_ecosystem.model.GameSession;
import com.example.modules.entertainment_ecosystem.mapper.GameSessionMapper;
import com.example.modules.entertainment_ecosystem.service.GameSessionService;
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
 * Controller for managing GameSession entities.
 */
@RestController
@RequestMapping("/api/gamesessions")
public class GameSessionController extends BaseController<GameSession, GameSessionDto, GameSessionSimpleDto> {

    public GameSessionController(GameSessionService gamesessionService,
                                    GameSessionMapper gamesessionMapper) {
        super(gamesessionService, gamesessionMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GameSessionDto>> getAllGameSessions(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GameSessionDto>> searchGameSessions(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(GameSession.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameSessionDto> getGameSessionById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GameSessionDto> createGameSession(
            @Valid @RequestBody GameSessionDto gamesessionDto,
            UriComponentsBuilder uriBuilder) {

        GameSession entity = mapper.toEntity(gamesessionDto);
        GameSession saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/gamesessions/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GameSessionDto>> createAllGameSessions(
            @Valid @RequestBody List<GameSessionDto> gamesessionDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GameSession> entities = mapper.toEntityList(gamesessionDtoList);
        List<GameSession> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/gamesessions").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameSessionDto> updateGameSession(
            @PathVariable Long id,
            @Valid @RequestBody GameSessionDto gamesessionDto) {

        GameSession entityToUpdate = mapper.toEntity(gamesessionDto);
        GameSession updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameSession(@PathVariable Long id) {
        return doDelete(id);
    }
}