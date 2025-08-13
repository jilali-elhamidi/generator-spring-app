package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TVShowMerchandiseDto;
import com.example.modules.entertainment_ecosystem.model.TVShowMerchandise;
import com.example.modules.entertainment_ecosystem.mapper.TVShowMerchandiseMapper;
import com.example.modules.entertainment_ecosystem.service.TVShowMerchandiseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tvshowmerchandises")
public class TVShowMerchandiseController {

    private final TVShowMerchandiseService tvshowmerchandiseService;
    private final TVShowMerchandiseMapper tvshowmerchandiseMapper;

    public TVShowMerchandiseController(TVShowMerchandiseService tvshowmerchandiseService,
                                    TVShowMerchandiseMapper tvshowmerchandiseMapper) {
        this.tvshowmerchandiseService = tvshowmerchandiseService;
        this.tvshowmerchandiseMapper = tvshowmerchandiseMapper;
    }

    @GetMapping
    public ResponseEntity<List<TVShowMerchandiseDto>> getAllTVShowMerchandises() {
        List<TVShowMerchandise> entities = tvshowmerchandiseService.findAll();
        return ResponseEntity.ok(tvshowmerchandiseMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TVShowMerchandiseDto> getTVShowMerchandiseById(@PathVariable Long id) {
        return tvshowmerchandiseService.findById(id)
                .map(tvshowmerchandiseMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TVShowMerchandiseDto> createTVShowMerchandise(
            @Valid @RequestBody TVShowMerchandiseDto tvshowmerchandiseDto,
            UriComponentsBuilder uriBuilder) {

        TVShowMerchandise entity = tvshowmerchandiseMapper.toEntity(tvshowmerchandiseDto);
        TVShowMerchandise saved = tvshowmerchandiseService.save(entity);
        URI location = uriBuilder.path("/api/tvshowmerchandises/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(tvshowmerchandiseMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TVShowMerchandiseDto> updateTVShowMerchandise(
            @PathVariable Long id,
            @Valid @RequestBody TVShowMerchandiseDto tvshowmerchandiseDto) {

        try {
            TVShowMerchandise updatedEntity = tvshowmerchandiseService.update(
                    id,
                    tvshowmerchandiseMapper.toEntity(tvshowmerchandiseDto)
            );
            return ResponseEntity.ok(tvshowmerchandiseMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTVShowMerchandise(@PathVariable Long id) {
        tvshowmerchandiseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}