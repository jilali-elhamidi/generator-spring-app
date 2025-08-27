package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.GameReviewCommentDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewCommentSimpleDto;
import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import com.example.modules.entertainment_ecosystem.mapper.GameReviewCommentMapper;
import com.example.modules.entertainment_ecosystem.service.GameReviewCommentService;
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
 * Controller for managing GameReviewComment entities.
 */
@RestController
@RequestMapping("/api/gamereviewcomments")
public class GameReviewCommentController extends BaseController<GameReviewComment, GameReviewCommentDto, GameReviewCommentSimpleDto> {

    public GameReviewCommentController(GameReviewCommentService gamereviewcommentService,
                                    GameReviewCommentMapper gamereviewcommentMapper) {
        super(gamereviewcommentService, gamereviewcommentMapper);
    }

    @GetMapping
    public ResponseEntity<Page<GameReviewCommentDto>> getAllGameReviewComments(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GameReviewCommentDto>> searchGameReviewComments(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(GameReviewComment.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameReviewCommentDto> getGameReviewCommentById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<GameReviewCommentDto> createGameReviewComment(
            @Valid @RequestBody GameReviewCommentDto gamereviewcommentDto,
            UriComponentsBuilder uriBuilder) {

        GameReviewComment entity = mapper.toEntity(gamereviewcommentDto);
        GameReviewComment saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/gamereviewcomments/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GameReviewCommentDto>> createAllGameReviewComments(
            @Valid @RequestBody List<GameReviewCommentDto> gamereviewcommentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<GameReviewComment> entities = mapper.toEntityList(gamereviewcommentDtoList);
        List<GameReviewComment> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/gamereviewcomments").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameReviewCommentDto> updateGameReviewComment(
            @PathVariable Long id,
            @Valid @RequestBody GameReviewCommentDto gamereviewcommentDto) {

        GameReviewComment entityToUpdate = mapper.toEntity(gamereviewcommentDto);
        GameReviewComment updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameReviewComment(@PathVariable Long id) {
        return doDelete(id);
    }
}