package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameReviewUpvoteDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewUpvoteSimpleDto;
import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;
import com.example.modules.entertainment_ecosystem.mapper.GameReviewUpvoteMapper;
import com.example.modules.entertainment_ecosystem.service.GameReviewUpvoteService;
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
 * Controller for managing GameReviewUpvote entities.
 */
@RestController
@RequestMapping("/api/gamereviewupvotes")
public class GameReviewUpvoteController extends BaseController<GameReviewUpvote, GameReviewUpvoteDto, GameReviewUpvoteSimpleDto> {

    public GameReviewUpvoteController(GameReviewUpvoteService gamereviewupvoteService,
                                    GameReviewUpvoteMapper gamereviewupvoteMapper) {
        super(gamereviewupvoteService, gamereviewupvoteMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GameReviewUpvoteDto>> getAllGameReviewUpvotes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GameReviewUpvoteDto>> searchGameReviewUpvotes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(GameReviewUpvote.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameReviewUpvoteDto> getGameReviewUpvoteById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GameReviewUpvoteDto> createGameReviewUpvote(
            @Valid @RequestBody GameReviewUpvoteDto gamereviewupvoteDto,
            UriComponentsBuilder uriBuilder) {

        GameReviewUpvote entity = mapper.toEntity(gamereviewupvoteDto);
        GameReviewUpvote saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/gamereviewupvotes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GameReviewUpvoteDto>> createAllGameReviewUpvotes(
            @Valid @RequestBody List<GameReviewUpvoteDto> gamereviewupvoteDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GameReviewUpvote> entities = mapper.toEntityList(gamereviewupvoteDtoList);
        List<GameReviewUpvote> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/gamereviewupvotes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameReviewUpvoteDto> updateGameReviewUpvote(
            @PathVariable Long id,
            @Valid @RequestBody GameReviewUpvoteDto gamereviewupvoteDto) {

        GameReviewUpvote entityToUpdate = mapper.toEntity(gamereviewupvoteDto);
        GameReviewUpvote updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameReviewUpvote(@PathVariable Long id) {
        return doDelete(id);
    }
}