package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ReviewDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.mapper.ReviewMapper;
import com.example.modules.entertainment_ecosystem.service.ReviewService;
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
 * Controller for managing Review entities.
 */
@RestController
@RequestMapping("/api/reviews")
public class ReviewController extends BaseController<Review, ReviewDto, ReviewSimpleDto> {

    public ReviewController(ReviewService reviewService,
                                    ReviewMapper reviewMapper) {
        super(reviewService, reviewMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ReviewDto>> getAllReviews(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReviewDto>> searchReviews(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Review.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(
            @Valid @RequestBody ReviewDto reviewDto,
            UriComponentsBuilder uriBuilder) {

        Review entity = mapper.toEntity(reviewDto);
        Review saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/reviews/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ReviewDto>> createAllReviews(
            @Valid @RequestBody List<ReviewDto> reviewDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Review> entities = mapper.toEntityList(reviewDtoList);
        List<Review> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/reviews").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewDto reviewDto) {

        Review entityToUpdate = mapper.toEntity(reviewDto);
        Review updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        return doDelete(id);
    }
}