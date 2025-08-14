package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TVShowDto;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.mapper.TVShowMapper;
import com.example.modules.entertainment_ecosystem.service.TVShowService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tvshows")
public class TVShowController {

    private final TVShowService tvshowService;
    private final TVShowMapper tvshowMapper;

    public TVShowController(TVShowService tvshowService,
                                    TVShowMapper tvshowMapper) {
        this.tvshowService = tvshowService;
        this.tvshowMapper = tvshowMapper;
    }

    @GetMapping
    public ResponseEntity<List<TVShowDto>> getAllTVShows() {
        List<TVShow> entities = tvshowService.findAll();
        return ResponseEntity.ok(tvshowMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TVShowDto> getTVShowById(@PathVariable Long id) {
        return tvshowService.findById(id)
                .map(tvshowMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TVShowDto> createTVShow(
            @Valid @RequestBody TVShowDto tvshowDto,
            UriComponentsBuilder uriBuilder) {

        TVShow entity = tvshowMapper.toEntity(tvshowDto);
        TVShow saved = tvshowService.save(entity);
        URI location = uriBuilder.path("/api/tvshows/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(tvshowMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<TVShowDto> updateTVShow(
                @PathVariable Long id,
                @Valid @RequestBody TVShowDto tvshowDto) {

                try {
                // Récupérer l'entité existante avec Optional
                TVShow existing = tvshowService.findById(id)
                .orElseThrow(() -> new RuntimeException("TVShow not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                tvshowMapper.updateEntityFromDto(tvshowDto, existing);

                // Sauvegarde
                TVShow updatedEntity = tvshowService.save(existing);

                return ResponseEntity.ok(tvshowMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTVShow(@PathVariable Long id) {
        tvshowService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}