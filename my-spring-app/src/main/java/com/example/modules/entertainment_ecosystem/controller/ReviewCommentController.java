package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ReviewCommentDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewCommentSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ReviewComment;
import com.example.modules.entertainment_ecosystem.mapper.ReviewCommentMapper;
import com.example.modules.entertainment_ecosystem.service.ReviewCommentService;
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
 * Controller for managing ReviewComment entities.
 */
@RestController
@RequestMapping("/api/reviewcomments")
public class ReviewCommentController extends BaseController<ReviewComment, ReviewCommentDto, ReviewCommentSimpleDto> {

    public ReviewCommentController(ReviewCommentService reviewcommentService,
                                    ReviewCommentMapper reviewcommentMapper) {
        super(reviewcommentService, reviewcommentMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ReviewCommentDto>> getAllReviewComments(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReviewCommentDto>> searchReviewComments(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ReviewComment.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewCommentDto> getReviewCommentById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ReviewCommentDto> createReviewComment(
            @Valid @RequestBody ReviewCommentDto reviewcommentDto,
            UriComponentsBuilder uriBuilder) {

        ReviewComment entity = mapper.toEntity(reviewcommentDto);
        ReviewComment saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/reviewcomments/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ReviewCommentDto>> createAllReviewComments(
            @Valid @RequestBody List<ReviewCommentDto> reviewcommentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ReviewComment> entities = mapper.toEntityList(reviewcommentDtoList);
        List<ReviewComment> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/reviewcomments").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewCommentDto> updateReviewComment(
            @PathVariable Long id,
            @Valid @RequestBody ReviewCommentDto reviewcommentDto) {

        ReviewComment entityToUpdate = mapper.toEntity(reviewcommentDto);
        ReviewComment updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewComment(@PathVariable Long id) {
        return doDelete(id);
    }
}