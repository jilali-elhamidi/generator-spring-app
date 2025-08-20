package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContentRatingDto;
import com.example.modules.entertainment_ecosystem.model.ContentRating;
import com.example.modules.entertainment_ecosystem.mapper.ContentRatingMapper;
import com.example.modules.entertainment_ecosystem.service.ContentRatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contentratings")
public class ContentRatingController {

    private final ContentRatingService contentratingService;
    private final ContentRatingMapper contentratingMapper;

    public ContentRatingController(ContentRatingService contentratingService,
                                    ContentRatingMapper contentratingMapper) {
        this.contentratingService = contentratingService;
        this.contentratingMapper = contentratingMapper;
    }

    @GetMapping
    public ResponseEntity<List<ContentRatingDto>> getAllContentRatings() {
        List<ContentRating> entities = contentratingService.findAll();
        return ResponseEntity.ok(contentratingMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentRatingDto> getContentRatingById(@PathVariable Long id) {
        return contentratingService.findById(id)
                .map(contentratingMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContentRatingDto> createContentRating(
            @Valid @RequestBody ContentRatingDto contentratingDto,
            UriComponentsBuilder uriBuilder) {

        ContentRating entity = contentratingMapper.toEntity(contentratingDto);
        ContentRating saved = contentratingService.save(entity);

        URI location = uriBuilder
                                .path("/api/contentratings/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(contentratingMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentRatingDto> updateContentRating(
            @PathVariable Long id,
            @Valid @RequestBody ContentRatingDto contentratingDto) {


        ContentRating entityToUpdate = contentratingMapper.toEntity(contentratingDto);
        ContentRating updatedEntity = contentratingService.update(id, entityToUpdate);

        return ResponseEntity.ok(contentratingMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContentRating(@PathVariable Long id) {
        boolean deleted = contentratingService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}