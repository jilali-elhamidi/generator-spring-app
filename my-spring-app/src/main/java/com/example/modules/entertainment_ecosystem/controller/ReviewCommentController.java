package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ReviewCommentDto;
import com.example.modules.entertainment_ecosystem.model.ReviewComment;
import com.example.modules.entertainment_ecosystem.mapper.ReviewCommentMapper;
import com.example.modules.entertainment_ecosystem.service.ReviewCommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reviewcomments")
public class ReviewCommentController {

    private final ReviewCommentService reviewcommentService;
    private final ReviewCommentMapper reviewcommentMapper;

    public ReviewCommentController(ReviewCommentService reviewcommentService,
                                    ReviewCommentMapper reviewcommentMapper) {
        this.reviewcommentService = reviewcommentService;
        this.reviewcommentMapper = reviewcommentMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReviewCommentDto>> getAllReviewComments() {
        List<ReviewComment> entities = reviewcommentService.findAll();
        return ResponseEntity.ok(reviewcommentMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewCommentDto> getReviewCommentById(@PathVariable Long id) {
        return reviewcommentService.findById(id)
                .map(reviewcommentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReviewCommentDto> createReviewComment(
            @Valid @RequestBody ReviewCommentDto reviewcommentDto,
            UriComponentsBuilder uriBuilder) {

        ReviewComment entity = reviewcommentMapper.toEntity(reviewcommentDto);
        ReviewComment saved = reviewcommentService.save(entity);
        URI location = uriBuilder.path("/api/reviewcomments/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(reviewcommentMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewCommentDto> updateReviewComment(
            @PathVariable Long id,
            @Valid @RequestBody ReviewCommentDto reviewcommentDto) {

        try {
            ReviewComment updatedEntity = reviewcommentService.update(
                    id,
                    reviewcommentMapper.toEntity(reviewcommentDto)
            );
            return ResponseEntity.ok(reviewcommentMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewComment(@PathVariable Long id) {
        reviewcommentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}