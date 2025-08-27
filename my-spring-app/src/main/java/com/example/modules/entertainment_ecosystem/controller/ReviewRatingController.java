package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ReviewRatingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewRatingSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ReviewRating;
import com.example.modules.entertainment_ecosystem.mapper.ReviewRatingMapper;
import com.example.modules.entertainment_ecosystem.service.ReviewRatingService;
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
 * Controller for managing ReviewRating entities.
 */
@RestController
@RequestMapping("/api/reviewratings")
public class ReviewRatingController extends BaseController<ReviewRating, ReviewRatingDto, ReviewRatingSimpleDto> {

    public ReviewRatingController(ReviewRatingService reviewratingService,
                                    ReviewRatingMapper reviewratingMapper) {
        super(reviewratingService, reviewratingMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ReviewRatingDto>> getAllReviewRatings(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReviewRatingDto>> searchReviewRatings(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ReviewRating.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewRatingDto> getReviewRatingById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ReviewRatingDto> createReviewRating(
            @Valid @RequestBody ReviewRatingDto reviewratingDto,
            UriComponentsBuilder uriBuilder) {

        ReviewRating entity = mapper.toEntity(reviewratingDto);
        ReviewRating saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/reviewratings/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ReviewRatingDto>> createAllReviewRatings(
            @Valid @RequestBody List<ReviewRatingDto> reviewratingDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ReviewRating> entities = mapper.toEntityList(reviewratingDtoList);
        List<ReviewRating> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/reviewratings").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewRatingDto> updateReviewRating(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRatingDto reviewratingDto) {

        ReviewRating entityToUpdate = mapper.toEntity(reviewratingDto);
        ReviewRating updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewRating(@PathVariable Long id) {
        return doDelete(id);
    }
}