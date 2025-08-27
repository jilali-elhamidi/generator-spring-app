package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContentRatingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ContentRating;
import com.example.modules.entertainment_ecosystem.mapper.ContentRatingMapper;
import com.example.modules.entertainment_ecosystem.service.ContentRatingService;
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
 * Controller for managing ContentRating entities.
 */
@RestController
@RequestMapping("/api/contentratings")
public class ContentRatingController extends BaseController<ContentRating, ContentRatingDto, ContentRatingSimpleDto> {

    public ContentRatingController(ContentRatingService contentratingService,
                                    ContentRatingMapper contentratingMapper) {
        super(contentratingService, contentratingMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ContentRatingDto>> getAllContentRatings(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ContentRatingDto>> searchContentRatings(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ContentRating.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentRatingDto> getContentRatingById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ContentRatingDto> createContentRating(
            @Valid @RequestBody ContentRatingDto contentratingDto,
            UriComponentsBuilder uriBuilder) {

        ContentRating entity = mapper.toEntity(contentratingDto);
        ContentRating saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/contentratings/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ContentRatingDto>> createAllContentRatings(
            @Valid @RequestBody List<ContentRatingDto> contentratingDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ContentRating> entities = mapper.toEntityList(contentratingDtoList);
        List<ContentRating> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/contentratings").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentRatingDto> updateContentRating(
            @PathVariable Long id,
            @Valid @RequestBody ContentRatingDto contentratingDto) {

        ContentRating entityToUpdate = mapper.toEntity(contentratingDto);
        ContentRating updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContentRating(@PathVariable Long id) {
        return doDelete(id);
    }
}