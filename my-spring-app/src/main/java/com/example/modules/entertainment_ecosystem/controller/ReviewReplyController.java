package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ReviewReplyDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewReplySimpleDto;
import com.example.modules.entertainment_ecosystem.model.ReviewReply;
import com.example.modules.entertainment_ecosystem.mapper.ReviewReplyMapper;
import com.example.modules.entertainment_ecosystem.service.ReviewReplyService;
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
 * Controller for managing ReviewReply entities.
 */
@RestController
@RequestMapping("/api/reviewreplys")
public class ReviewReplyController extends BaseController<ReviewReply, ReviewReplyDto, ReviewReplySimpleDto> {

    public ReviewReplyController(ReviewReplyService reviewreplyService,
                                    ReviewReplyMapper reviewreplyMapper) {
        super(reviewreplyService, reviewreplyMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ReviewReplyDto>> getAllReviewReplys(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReviewReplyDto>> searchReviewReplys(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ReviewReply.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewReplyDto> getReviewReplyById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ReviewReplyDto> createReviewReply(
            @Valid @RequestBody ReviewReplyDto reviewreplyDto,
            UriComponentsBuilder uriBuilder) {

        ReviewReply entity = mapper.toEntity(reviewreplyDto);
        ReviewReply saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/reviewreplys/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ReviewReplyDto>> createAllReviewReplys(
            @Valid @RequestBody List<ReviewReplyDto> reviewreplyDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ReviewReply> entities = mapper.toEntityList(reviewreplyDtoList);
        List<ReviewReply> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/reviewreplys").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewReplyDto> updateReviewReply(
            @PathVariable Long id,
            @Valid @RequestBody ReviewReplyDto reviewreplyDto) {

        ReviewReply entityToUpdate = mapper.toEntity(reviewreplyDto);
        ReviewReply updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewReply(@PathVariable Long id) {
        return doDelete(id);
    }
}