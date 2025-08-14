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
        URI location = uriBuilder.path("/api/reviews/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(reviewMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ReviewDto> updateReview(
                @PathVariable Long id,
                @Valid @RequestBody ReviewDto reviewDto) {

                try {
                // Récupérer l'entité existante avec Optional
                Review existing = reviewService.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                reviewMapper.updateEntityFromDto(reviewDto, existing);

                // Sauvegarde
                Review updatedEntity = reviewService.save(existing);

                return ResponseEntity.ok(reviewMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}