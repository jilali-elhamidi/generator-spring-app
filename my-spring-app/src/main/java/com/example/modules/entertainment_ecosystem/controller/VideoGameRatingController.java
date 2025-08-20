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
                @RequestBody VideoGameRatingDto videogameratingDto) {

                // Transformer le DTO en entity pour le service
                VideoGameRating entityToUpdate = videogameratingMapper.toEntity(videogameratingDto);

                // Appel du service update
                VideoGameRating updatedEntity = videogameratingService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                VideoGameRatingDto updatedDto = videogameratingMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteVideoGameRating(@PathVariable Long id) {
                    boolean deleted = videogameratingService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}