package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.VideoGameDto;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.mapper.VideoGameMapper;
import com.example.modules.entertainment_ecosystem.service.VideoGameService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/videogames")
public class VideoGameController {

    private final VideoGameService videogameService;
    private final VideoGameMapper videogameMapper;

    public VideoGameController(VideoGameService videogameService,
                                    VideoGameMapper videogameMapper) {
        this.videogameService = videogameService;
        this.videogameMapper = videogameMapper;
    }

    @GetMapping
    public ResponseEntity<List<VideoGameDto>> getAllVideoGames() {
        List<VideoGame> entities = videogameService.findAll();
        return ResponseEntity.ok(videogameMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoGameDto> getVideoGameById(@PathVariable Long id) {
        return videogameService.findById(id)
                .map(videogameMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VideoGameDto> createVideoGame(
            @Valid @RequestBody VideoGameDto videogameDto,
            UriComponentsBuilder uriBuilder) {

        VideoGame entity = videogameMapper.toEntity(videogameDto);
        VideoGame saved = videogameService.save(entity);
        URI location = uriBuilder.path("/api/videogames/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(videogameMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<VideoGameDto> updateVideoGame(
                @PathVariable Long id,
                @Valid @RequestBody VideoGameDto videogameDto) {

                try {
                // Récupérer l'entité existante avec Optional
                VideoGame existing = videogameService.findById(id)
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                videogameMapper.updateEntityFromDto(videogameDto, existing);

                // Sauvegarde
                VideoGame updatedEntity = videogameService.save(existing);

                return ResponseEntity.ok(videogameMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoGame(@PathVariable Long id) {
        videogameService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}