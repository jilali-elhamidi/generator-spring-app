package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ReviewLikeDto;
import com.example.modules.entertainment_ecosystem.model.ReviewLike;
import com.example.modules.entertainment_ecosystem.mapper.ReviewLikeMapper;
import com.example.modules.entertainment_ecosystem.service.ReviewLikeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reviewlikes")
public class ReviewLikeController {

    private final ReviewLikeService reviewlikeService;
    private final ReviewLikeMapper reviewlikeMapper;

    public ReviewLikeController(ReviewLikeService reviewlikeService,
                                    ReviewLikeMapper reviewlikeMapper) {
        this.reviewlikeService = reviewlikeService;
        this.reviewlikeMapper = reviewlikeMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReviewLikeDto>> getAllReviewLikes() {
        List<ReviewLike> entities = reviewlikeService.findAll();
        return ResponseEntity.ok(reviewlikeMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewLikeDto> getReviewLikeById(@PathVariable Long id) {
        return reviewlikeService.findById(id)
                .map(reviewlikeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReviewLikeDto> createReviewLike(
            @Valid @RequestBody ReviewLikeDto reviewlikeDto,
            UriComponentsBuilder uriBuilder) {

        ReviewLike entity = reviewlikeMapper.toEntity(reviewlikeDto);
        ReviewLike saved = reviewlikeService.save(entity);

        URI location = uriBuilder
                                .path("/api/reviewlikes/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(reviewlikeMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ReviewLikeDto>> createAllReviewLikes(
            @Valid @RequestBody List<ReviewLikeDto> reviewlikeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ReviewLike> entities = reviewlikeMapper.toEntityList(reviewlikeDtoList);
        List<ReviewLike> savedEntities = reviewlikeService.saveAll(entities);

        URI location = uriBuilder.path("/api/reviewlikes").build().toUri();

        return ResponseEntity.created(location).body(reviewlikeMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewLikeDto> updateReviewLike(
            @PathVariable Long id,
            @Valid @RequestBody ReviewLikeDto reviewlikeDto) {


        ReviewLike entityToUpdate = reviewlikeMapper.toEntity(reviewlikeDto);
        ReviewLike updatedEntity = reviewlikeService.update(id, entityToUpdate);

        return ResponseEntity.ok(reviewlikeMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewLike(@PathVariable Long id) {
        boolean deleted = reviewlikeService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}