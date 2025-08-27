package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameReviewDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewSimpleDto;
import com.example.modules.entertainment_ecosystem.model.GameReview;
import com.example.modules.entertainment_ecosystem.mapper.GameReviewMapper;
import com.example.modules.entertainment_ecosystem.service.GameReviewService;
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
 * Controller for managing GameReview entities.
 */
@RestController
@RequestMapping("/api/gamereviews")
public class GameReviewController extends BaseController<GameReview, GameReviewDto, GameReviewSimpleDto> {

    public GameReviewController(GameReviewService gamereviewService,
                                    GameReviewMapper gamereviewMapper) {
        super(gamereviewService, gamereviewMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GameReviewDto>> getAllGameReviews(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GameReviewDto>> searchGameReviews(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(GameReview.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameReviewDto> getGameReviewById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GameReviewDto> createGameReview(
            @Valid @RequestBody GameReviewDto gamereviewDto,
            UriComponentsBuilder uriBuilder) {

        GameReview entity = mapper.toEntity(gamereviewDto);
        GameReview saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/gamereviews/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GameReviewDto>> createAllGameReviews(
            @Valid @RequestBody List<GameReviewDto> gamereviewDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GameReview> entities = mapper.toEntityList(gamereviewDtoList);
        List<GameReview> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/gamereviews").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameReviewDto> updateGameReview(
            @PathVariable Long id,
            @Valid @RequestBody GameReviewDto gamereviewDto) {

        GameReview entityToUpdate = mapper.toEntity(gamereviewDto);
        GameReview updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameReview(@PathVariable Long id) {
        return doDelete(id);
    }
}