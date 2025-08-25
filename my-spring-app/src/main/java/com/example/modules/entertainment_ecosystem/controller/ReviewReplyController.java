package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ReviewReplyDto;
import com.example.modules.entertainment_ecosystem.model.ReviewReply;
import com.example.modules.entertainment_ecosystem.mapper.ReviewReplyMapper;
import com.example.modules.entertainment_ecosystem.service.ReviewReplyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reviewreplys")
public class ReviewReplyController {

    private final ReviewReplyService reviewreplyService;
    private final ReviewReplyMapper reviewreplyMapper;

    public ReviewReplyController(ReviewReplyService reviewreplyService,
                                    ReviewReplyMapper reviewreplyMapper) {
        this.reviewreplyService = reviewreplyService;
        this.reviewreplyMapper = reviewreplyMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReviewReplyDto>> getAllReviewReplys() {
        List<ReviewReply> entities = reviewreplyService.findAll();
        return ResponseEntity.ok(reviewreplyMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewReplyDto> getReviewReplyById(@PathVariable Long id) {
        return reviewreplyService.findById(id)
                .map(reviewreplyMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReviewReplyDto> createReviewReply(
            @Valid @RequestBody ReviewReplyDto reviewreplyDto,
            UriComponentsBuilder uriBuilder) {

        ReviewReply entity = reviewreplyMapper.toEntity(reviewreplyDto);
        ReviewReply saved = reviewreplyService.save(entity);

        URI location = uriBuilder
                                .path("/api/reviewreplys/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(reviewreplyMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ReviewReplyDto>> createAllReviewReplys(
            @Valid @RequestBody List<ReviewReplyDto> reviewreplyDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ReviewReply> entities = reviewreplyMapper.toEntityList(reviewreplyDtoList);
        List<ReviewReply> savedEntities = reviewreplyService.saveAll(entities);

        URI location = uriBuilder.path("/api/reviewreplys").build().toUri();

        return ResponseEntity.created(location).body(reviewreplyMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewReplyDto> updateReviewReply(
            @PathVariable Long id,
            @Valid @RequestBody ReviewReplyDto reviewreplyDto) {


        ReviewReply entityToUpdate = reviewreplyMapper.toEntity(reviewreplyDto);
        ReviewReply updatedEntity = reviewreplyService.update(id, entityToUpdate);

        return ResponseEntity.ok(reviewreplyMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewReply(@PathVariable Long id) {
        boolean deleted = reviewreplyService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}