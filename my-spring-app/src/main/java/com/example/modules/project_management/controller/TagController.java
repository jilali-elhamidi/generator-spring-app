package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.TagDto;
import com.example.modules.project_management.dtosimple.TagSimpleDto;
import com.example.modules.project_management.model.Tag;
import com.example.modules.project_management.mapper.TagMapper;
import com.example.modules.project_management.service.TagService;
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
 * Controller for managing Tag entities.
 */
@RestController
@RequestMapping("/api/tags")
public class TagController extends BaseController<Tag, TagDto, TagSimpleDto> {

    public TagController(TagService tagService,
                                    TagMapper tagMapper) {
        super(tagService, tagMapper);
    }

    @GetMapping
    public ResponseEntity<Page<TagDto>> getAllTags(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TagDto>> searchTags(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Tag.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<TagDto> createTag(
            @Valid @RequestBody TagDto tagDto,
            UriComponentsBuilder uriBuilder) {

        Tag entity = mapper.toEntity(tagDto);
        Tag saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/tags/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TagDto>> createAllTags(
            @Valid @RequestBody List<TagDto> tagDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Tag> entities = mapper.toEntityList(tagDtoList);
        List<Tag> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/tags").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody TagDto tagDto) {

        Tag entityToUpdate = mapper.toEntity(tagDto);
        Tag updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        return doDelete(id);
    }
}