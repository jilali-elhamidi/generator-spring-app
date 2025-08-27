package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameReviewDownvoteDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewDownvoteSimpleDto;
import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;
import com.example.modules.entertainment_ecosystem.mapper.GameReviewDownvoteMapper;
import com.example.modules.entertainment_ecosystem.service.GameReviewDownvoteService;
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
 * Controller for managing GameReviewDownvote entities.
 */
@RestController
@RequestMapping("/api/gamereviewdownvotes")
public class GameReviewDownvoteController extends BaseController<GameReviewDownvote, GameReviewDownvoteDto, GameReviewDownvoteSimpleDto> {

    public GameReviewDownvoteController(GameReviewDownvoteService gamereviewdownvoteService,
                                    GameReviewDownvoteMapper gamereviewdownvoteMapper) {
        super(gamereviewdownvoteService, gamereviewdownvoteMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GameReviewDownvoteDto>> getAllGameReviewDownvotes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GameReviewDownvoteDto>> searchGameReviewDownvotes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(GameReviewDownvote.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameReviewDownvoteDto> getGameReviewDownvoteById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GameReviewDownvoteDto> createGameReviewDownvote(
            @Valid @RequestBody GameReviewDownvoteDto gamereviewdownvoteDto,
            UriComponentsBuilder uriBuilder) {

        GameReviewDownvote entity = mapper.toEntity(gamereviewdownvoteDto);
        GameReviewDownvote saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/gamereviewdownvotes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GameReviewDownvoteDto>> createAllGameReviewDownvotes(
            @Valid @RequestBody List<GameReviewDownvoteDto> gamereviewdownvoteDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GameReviewDownvote> entities = mapper.toEntityList(gamereviewdownvoteDtoList);
        List<GameReviewDownvote> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/gamereviewdownvotes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameReviewDownvoteDto> updateGameReviewDownvote(
            @PathVariable Long id,
            @Valid @RequestBody GameReviewDownvoteDto gamereviewdownvoteDto) {

        GameReviewDownvote entityToUpdate = mapper.toEntity(gamereviewdownvoteDto);
        GameReviewDownvote updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameReviewDownvote(@PathVariable Long id) {
        return doDelete(id);
    }
}