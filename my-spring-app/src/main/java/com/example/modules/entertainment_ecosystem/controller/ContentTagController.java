package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ContentTagDto;
import com.example.modules.entertainment_ecosystem.model.ContentTag;
import com.example.modules.entertainment_ecosystem.mapper.ContentTagMapper;
import com.example.modules.entertainment_ecosystem.service.ContentTagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contenttags")
public class ContentTagController {

    private final ContentTagService contenttagService;
    private final ContentTagMapper contenttagMapper;

    public ContentTagController(ContentTagService contenttagService,
                                    ContentTagMapper contenttagMapper) {
        this.contenttagService = contenttagService;
        this.contenttagMapper = contenttagMapper;
    }

    @GetMapping
    public ResponseEntity<List<ContentTagDto>> getAllContentTags() {
        List<ContentTag> entities = contenttagService.findAll();
        return ResponseEntity.ok(contenttagMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentTagDto> getContentTagById(@PathVariable Long id) {
        return contenttagService.findById(id)
                .map(contenttagMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContentTagDto> createContentTag(
            @Valid @RequestBody ContentTagDto contenttagDto,
            UriComponentsBuilder uriBuilder) {

        ContentTag entity = contenttagMapper.toEntity(contenttagDto);
        ContentTag saved = contenttagService.save(entity);
        URI location = uriBuilder.path("/api/contenttags/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(contenttagMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<ContentTagDto> updateContentTag(
                @PathVariable Long id,
                @Valid @RequestBody ContentTagDto contenttagDto) {

                try {
                // Récupérer l'entité existante avec Optional
                ContentTag existing = contenttagService.findById(id)
                .orElseThrow(() -> new RuntimeException("ContentTag not found"));

                // Appliquer les champs simples du DTO à l'entité existante
                contenttagMapper.updateEntityFromDto(contenttagDto, existing);

                // Sauvegarde
                ContentTag updatedEntity = contenttagService.save(existing);

                return ResponseEntity.ok(contenttagMapper.toDto(updatedEntity));
                } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
                }
                }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContentTag(@PathVariable Long id) {
        contenttagService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}