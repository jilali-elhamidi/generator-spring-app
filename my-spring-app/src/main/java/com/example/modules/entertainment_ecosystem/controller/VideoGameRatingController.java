package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.VideoGameRatingDto;
import com.example.modules.entertainment_ecosystem.model.VideoGameRating;
import com.example.modules.entertainment_ecosystem.mapper.VideoGameRatingMapper;
import com.example.modules.entertainment_ecosystem.service.VideoGameRatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/videogameratings")
public class VideoGameRatingController {

    private final VideoGameRatingService videogameratingService;
    private final VideoGameRatingMapper videogameratingMapper;

    public VideoGameRatingController(VideoGameRatingService videogameratingService,
                                    VideoGameRatingMapper videogameratingMapper) {
        this.videogameratingService = videogameratingService;
        this.videogameratingMapper = videogameratingMapper;
    }

    @GetMapping
    public ResponseEntity<List<VideoGameRatingDto>> getAllVideoGameRatings() {
        List<VideoGameRating> entities = videogameratingService.findAll();
        return ResponseEntity.ok(videogameratingMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoGameRatingDto> getVideoGameRatingById(@PathVariable Long id) {
        return videogameratingService.findById(id)
                .map(videogameratingMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VideoGameRatingDto> createVideoGameRating(
            @Valid @RequestBody VideoGameRatingDto videogameratingDto,
            UriComponentsBuilder uriBuilder) {

        VideoGameRating entity = videogameratingMapper.toEntity(videogameratingDto);
        VideoGameRating saved = videogameratingService.save(entity);
        URI location = uriBuilder.path("/api/videogameratings/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(videogameratingMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoGameRatingDto> updateVideoGameRating(
            @PathVariable Long id,
            @Valid @RequestBody VideoGameRatingDto videogameratingDto) {

        try {
            VideoGameRating updatedEntity = videogameratingService.update(
                    id,
                    videogameratingMapper.toEntity(videogameratingDto)
            );
            return ResponseEntity.ok(videogameratingMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoGameRating(@PathVariable Long id) {
        videogameratingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}