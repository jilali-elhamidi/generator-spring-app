package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ReviewableContentDto;
import com.example.modules.entertainment_ecosystem.model.ReviewableContent;
import com.example.modules.entertainment_ecosystem.mapper.ReviewableContentMapper;
import com.example.modules.entertainment_ecosystem.service.ReviewableContentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reviewablecontents")
public class ReviewableContentController {

    private final ReviewableContentService reviewablecontentService;
    private final ReviewableContentMapper reviewablecontentMapper;

    public ReviewableContentController(ReviewableContentService reviewablecontentService,
                                    ReviewableContentMapper reviewablecontentMapper) {
        this.reviewablecontentService = reviewablecontentService;
        this.reviewablecontentMapper = reviewablecontentMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReviewableContentDto>> getAllReviewableContents() {
        List<ReviewableContent> entities = reviewablecontentService.findAll();
        return ResponseEntity.ok(reviewablecontentMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewableContentDto> getReviewableContentById(@PathVariable Long id) {
        return reviewablecontentService.findById(id)
                .map(reviewablecontentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReviewableContentDto> createReviewableContent(
            @Valid @RequestBody ReviewableContentDto reviewablecontentDto,
            UriComponentsBuilder uriBuilder) {

        ReviewableContent entity = reviewablecontentMapper.toEntity(reviewablecontentDto);
        ReviewableContent saved = reviewablecontentService.save(entity);
        URI location = uriBuilder.path("/api/reviewablecontents/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(reviewablecontentMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewableContentDto> updateReviewableContent(
            @PathVariable Long id,
            @Valid @RequestBody ReviewableContentDto reviewablecontentDto) {

        try {
            ReviewableContent updatedEntity = reviewablecontentService.update(
                    id,
                    reviewablecontentMapper.toEntity(reviewablecontentDto)
            );
            return ResponseEntity.ok(reviewablecontentMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewableContent(@PathVariable Long id) {
        reviewablecontentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}