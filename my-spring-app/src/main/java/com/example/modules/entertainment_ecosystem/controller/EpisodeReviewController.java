package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.EpisodeReviewDto;
import com.example.modules.entertainment_ecosystem.model.EpisodeReview;
import com.example.modules.entertainment_ecosystem.mapper.EpisodeReviewMapper;
import com.example.modules.entertainment_ecosystem.service.EpisodeReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/episodereviews")
public class EpisodeReviewController {

    private final EpisodeReviewService episodereviewService;
    private final EpisodeReviewMapper episodereviewMapper;

    public EpisodeReviewController(EpisodeReviewService episodereviewService,
                                    EpisodeReviewMapper episodereviewMapper) {
        this.episodereviewService = episodereviewService;
        this.episodereviewMapper = episodereviewMapper;
    }

    @GetMapping
    public ResponseEntity<List<EpisodeReviewDto>> getAllEpisodeReviews() {
        List<EpisodeReview> entities = episodereviewService.findAll();
        return ResponseEntity.ok(episodereviewMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeReviewDto> getEpisodeReviewById(@PathVariable Long id) {
        return episodereviewService.findById(id)
                .map(episodereviewMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EpisodeReviewDto> createEpisodeReview(
            @Valid @RequestBody EpisodeReviewDto episodereviewDto,
            UriComponentsBuilder uriBuilder) {

        EpisodeReview entity = episodereviewMapper.toEntity(episodereviewDto);
        EpisodeReview saved = episodereviewService.save(entity);

        URI location = uriBuilder
                                .path("/api/episodereviews/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(episodereviewMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EpisodeReviewDto>> createAllEpisodeReviews(
            @Valid @RequestBody List<EpisodeReviewDto> episodereviewDtoList,
            UriComponentsBuilder uriBuilder) {

        List<EpisodeReview> entities = episodereviewMapper.toEntityList(episodereviewDtoList);
        List<EpisodeReview> savedEntities = episodereviewService.saveAll(entities);

        URI location = uriBuilder.path("/api/episodereviews").build().toUri();

        return ResponseEntity.created(location).body(episodereviewMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EpisodeReviewDto> updateEpisodeReview(
            @PathVariable Long id,
            @Valid @RequestBody EpisodeReviewDto episodereviewDto) {


        EpisodeReview entityToUpdate = episodereviewMapper.toEntity(episodereviewDto);
        EpisodeReview updatedEntity = episodereviewService.update(id, entityToUpdate);

        return ResponseEntity.ok(episodereviewMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpisodeReview(@PathVariable Long id) {
        boolean deleted = episodereviewService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}