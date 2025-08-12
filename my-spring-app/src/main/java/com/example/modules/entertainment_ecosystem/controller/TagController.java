package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.TagDto;
import com.example.modules.entertainment_ecosystem.model.Tag;
import com.example.modules.entertainment_ecosystem.mapper.TagMapper;
import com.example.modules.entertainment_ecosystem.service.TagService;
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
        URI location = uriBuilder.path("/api/tags/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(tagMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody TagDto tagDto) {

        try {
            Tag updatedEntity = tagService.update(
                    id,
                    tagMapper.toEntity(tagDto)
            );
            return ResponseEntity.ok(tagMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}