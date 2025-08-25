package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ReviewDto;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.mapper.ReviewMapper;
import com.example.modules.entertainment_ecosystem.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService,
                                    ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<Review> entities = reviewService.findAll();
        return ResponseEntity.ok(reviewMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        return reviewService.findById(id)
                .map(reviewMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(
            @Valid @RequestBody ReviewDto reviewDto,
            UriComponentsBuilder uriBuilder) {

        Review entity = reviewMapper.toEntity(reviewDto);
        Review saved = reviewService.save(entity);

        URI location = uriBuilder
                                .path("/api/reviews/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(reviewMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ReviewDto>> createAllReviews(
            @Valid @RequestBody List<ReviewDto> reviewDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Review> entities = reviewMapper.toEntityList(reviewDtoList);
        List<Review> savedEntities = reviewService.saveAll(entities);

        URI location = uriBuilder.path("/api/reviews").build().toUri();

        return ResponseEntity.created(location).body(reviewMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewDto reviewDto) {


        Review entityToUpdate = reviewMapper.toEntity(reviewDto);
        Review updatedEntity = reviewService.update(id, entityToUpdate);

        return ResponseEntity.ok(reviewMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        boolean deleted = reviewService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}