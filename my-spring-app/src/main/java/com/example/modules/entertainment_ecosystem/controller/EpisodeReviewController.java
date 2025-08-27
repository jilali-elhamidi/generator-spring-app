package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EpisodeReviewDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EpisodeReviewSimpleDto;
import com.example.modules.entertainment_ecosystem.model.EpisodeReview;
import com.example.modules.entertainment_ecosystem.mapper.EpisodeReviewMapper;
import com.example.modules.entertainment_ecosystem.service.EpisodeReviewService;
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
 * Controller for managing EpisodeReview entities.
 */
@RestController
@RequestMapping("/api/episodereviews")
public class EpisodeReviewController extends BaseController<EpisodeReview, EpisodeReviewDto, EpisodeReviewSimpleDto> {

    public EpisodeReviewController(EpisodeReviewService episodereviewService,
                                    EpisodeReviewMapper episodereviewMapper) {
        super(episodereviewService, episodereviewMapper);
    }

    @GetMapping
    public ResponseEntity<Page<EpisodeReviewDto>> getAllEpisodeReviews(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EpisodeReviewDto>> searchEpisodeReviews(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(EpisodeReview.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeReviewDto> getEpisodeReviewById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<EpisodeReviewDto> createEpisodeReview(
            @Valid @RequestBody EpisodeReviewDto episodereviewDto,
            UriComponentsBuilder uriBuilder) {

        EpisodeReview entity = mapper.toEntity(episodereviewDto);
        EpisodeReview saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/episodereviews/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EpisodeReviewDto>> createAllEpisodeReviews(
            @Valid @RequestBody List<EpisodeReviewDto> episodereviewDtoList,
            UriComponentsBuilder uriBuilder) {

        List<EpisodeReview> entities = mapper.toEntityList(episodereviewDtoList);
        List<EpisodeReview> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/episodereviews").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EpisodeReviewDto> updateEpisodeReview(
            @PathVariable Long id,
            @Valid @RequestBody EpisodeReviewDto episodereviewDto) {

        EpisodeReview entityToUpdate = mapper.toEntity(episodereviewDto);
        EpisodeReview updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpisodeReview(@PathVariable Long id) {
        return doDelete(id);
    }
}