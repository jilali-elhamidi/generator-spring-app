package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContentTagDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentTagSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ContentTag;
import com.example.modules.entertainment_ecosystem.mapper.ContentTagMapper;
import com.example.modules.entertainment_ecosystem.service.ContentTagService;
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
 * Controller for managing ContentTag entities.
 */
@RestController
@RequestMapping("/api/contenttags")
public class ContentTagController extends BaseController<ContentTag, ContentTagDto, ContentTagSimpleDto> {

    public ContentTagController(ContentTagService contenttagService,
                                    ContentTagMapper contenttagMapper) {
        super(contenttagService, contenttagMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ContentTagDto>> getAllContentTags(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ContentTagDto>> searchContentTags(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ContentTag.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentTagDto> getContentTagById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ContentTagDto> createContentTag(
            @Valid @RequestBody ContentTagDto contenttagDto,
            UriComponentsBuilder uriBuilder) {

        ContentTag entity = mapper.toEntity(contenttagDto);
        ContentTag saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/contenttags/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ContentTagDto>> createAllContentTags(
            @Valid @RequestBody List<ContentTagDto> contenttagDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ContentTag> entities = mapper.toEntityList(contenttagDtoList);
        List<ContentTag> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/contenttags").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentTagDto> updateContentTag(
            @PathVariable Long id,
            @Valid @RequestBody ContentTagDto contenttagDto) {

        ContentTag entityToUpdate = mapper.toEntity(contenttagDto);
        ContentTag updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContentTag(@PathVariable Long id) {
        return doDelete(id);
    }
}