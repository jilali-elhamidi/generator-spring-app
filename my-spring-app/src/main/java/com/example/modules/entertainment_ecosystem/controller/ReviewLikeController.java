package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ReviewLikeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewLikeSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ReviewLike;
import com.example.modules.entertainment_ecosystem.mapper.ReviewLikeMapper;
import com.example.modules.entertainment_ecosystem.service.ReviewLikeService;
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
 * Controller for managing ReviewLike entities.
 */
@RestController
@RequestMapping("/api/reviewlikes")
public class ReviewLikeController extends BaseController<ReviewLike, ReviewLikeDto, ReviewLikeSimpleDto> {

    public ReviewLikeController(ReviewLikeService reviewlikeService,
                                    ReviewLikeMapper reviewlikeMapper) {
        super(reviewlikeService, reviewlikeMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ReviewLikeDto>> getAllReviewLikes(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReviewLikeDto>> searchReviewLikes(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ReviewLike.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewLikeDto> getReviewLikeById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ReviewLikeDto> createReviewLike(
            @Valid @RequestBody ReviewLikeDto reviewlikeDto,
            UriComponentsBuilder uriBuilder) {

        ReviewLike entity = mapper.toEntity(reviewlikeDto);
        ReviewLike saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/reviewlikes/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ReviewLikeDto>> createAllReviewLikes(
            @Valid @RequestBody List<ReviewLikeDto> reviewlikeDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ReviewLike> entities = mapper.toEntityList(reviewlikeDtoList);
        List<ReviewLike> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/reviewlikes").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewLikeDto> updateReviewLike(
            @PathVariable Long id,
            @Valid @RequestBody ReviewLikeDto reviewlikeDto) {

        ReviewLike entityToUpdate = mapper.toEntity(reviewlikeDto);
        ReviewLike updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewLike(@PathVariable Long id) {
        return doDelete(id);
    }
}