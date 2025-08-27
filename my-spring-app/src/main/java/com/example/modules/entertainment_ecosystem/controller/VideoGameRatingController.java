package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.VideoGameRatingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameRatingSimpleDto;
import com.example.modules.entertainment_ecosystem.model.VideoGameRating;
import com.example.modules.entertainment_ecosystem.mapper.VideoGameRatingMapper;
import com.example.modules.entertainment_ecosystem.service.VideoGameRatingService;
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
 * Controller for managing VideoGameRating entities.
 */
@RestController
@RequestMapping("/api/videogameratings")
public class VideoGameRatingController extends BaseController<VideoGameRating, VideoGameRatingDto, VideoGameRatingSimpleDto> {

    public VideoGameRatingController(VideoGameRatingService videogameratingService,
                                    VideoGameRatingMapper videogameratingMapper) {
        super(videogameratingService, videogameratingMapper);
    }

    @GetMapping
    public ResponseEntity<Page<VideoGameRatingDto>> getAllVideoGameRatings(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<VideoGameRatingDto>> searchVideoGameRatings(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(VideoGameRating.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoGameRatingDto> getVideoGameRatingById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<VideoGameRatingDto> createVideoGameRating(
            @Valid @RequestBody VideoGameRatingDto videogameratingDto,
            UriComponentsBuilder uriBuilder) {

        VideoGameRating entity = mapper.toEntity(videogameratingDto);
        VideoGameRating saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/videogameratings/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<VideoGameRatingDto>> createAllVideoGameRatings(
            @Valid @RequestBody List<VideoGameRatingDto> videogameratingDtoList,
            UriComponentsBuilder uriBuilder) {

        List<VideoGameRating> entities = mapper.toEntityList(videogameratingDtoList);
        List<VideoGameRating> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/videogameratings").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoGameRatingDto> updateVideoGameRating(
            @PathVariable Long id,
            @Valid @RequestBody VideoGameRatingDto videogameratingDto) {

        VideoGameRating entityToUpdate = mapper.toEntity(videogameratingDto);
        VideoGameRating updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoGameRating(@PathVariable Long id) {
        return doDelete(id);
    }
}