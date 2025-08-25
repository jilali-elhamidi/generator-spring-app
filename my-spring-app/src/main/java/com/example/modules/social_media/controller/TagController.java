package com.example.modules.social_media.controller;

import com.example.modules.social_media.dto.TagDto;
import com.example.modules.social_media.model.Tag;
import com.example.modules.social_media.mapper.TagMapper;
import com.example.modules.social_media.service.TagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    public TagController(TagService tagService,
                                    TagMapper tagMapper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        List<Tag> entities = tagService.findAll();
        return ResponseEntity.ok(tagMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable Long id) {
        return tagService.findById(id)
                .map(tagMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TagDto> createTag(
            @Valid @RequestBody TagDto tagDto,
            UriComponentsBuilder uriBuilder) {

        Tag entity = tagMapper.toEntity(tagDto);
        Tag saved = tagService.save(entity);

        URI location = uriBuilder
                                .path("/api/tags/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(tagMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TagDto>> createAllTags(
            @Valid @RequestBody List<TagDto> tagDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Tag> entities = tagMapper.toEntityList(tagDtoList);
        List<Tag> savedEntities = tagService.saveAll(entities);

        URI location = uriBuilder.path("/api/tags").build().toUri();

        return ResponseEntity.created(location).body(tagMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody TagDto tagDto) {


        Tag entityToUpdate = tagMapper.toEntity(tagDto);
        Tag updatedEntity = tagService.update(id, entityToUpdate);

        return ResponseEntity.ok(tagMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        boolean deleted = tagService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}