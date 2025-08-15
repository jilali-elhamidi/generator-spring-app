package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ReviewRatingDto;
import com.example.modules.entertainment_ecosystem.model.ReviewRating;
import com.example.modules.entertainment_ecosystem.mapper.ReviewRatingMapper;
import com.example.modules.entertainment_ecosystem.service.ReviewRatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reviewratings")
public class ReviewRatingController {

    private final ReviewRatingService reviewratingService;
    private final ReviewRatingMapper reviewratingMapper;

    public ReviewRatingController(ReviewRatingService reviewratingService,
                                    ReviewRatingMapper reviewratingMapper) {
        this.reviewratingService = reviewratingService;
        this.reviewratingMapper = reviewratingMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReviewRatingDto>> getAllReviewRatings() {
        List<ReviewRating> entities = reviewratingService.findAll();
        return ResponseEntity.ok(reviewratingMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewRatingDto> getReviewRatingById(@PathVariable Long id) {
        return reviewratingService.findById(id)
                .map(reviewratingMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReviewRatingDto> createReviewRating(
            @Valid @RequestBody ReviewRatingDto reviewratingDto,
            UriComponentsBuilder uriBuilder) {

        ReviewRating entity = reviewratingMapper.toEntity(reviewratingDto);
        ReviewRating saved = reviewratingService.save(entity);
        URI location = uriBuilder.path("/api/reviewratings/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(reviewratingMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ReviewRatingDto> updateReviewRating(
                @PathVariable Long id,
                @RequestBody ReviewRatingDto reviewratingDto) {

                // Transformer le DTO en entity pour le service
                ReviewRating entityToUpdate = reviewratingMapper.toEntity(reviewratingDto);

                // Appel du service update
                ReviewRating updatedEntity = reviewratingService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                ReviewRatingDto updatedDto = reviewratingMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewRating(@PathVariable Long id) {
        reviewratingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}