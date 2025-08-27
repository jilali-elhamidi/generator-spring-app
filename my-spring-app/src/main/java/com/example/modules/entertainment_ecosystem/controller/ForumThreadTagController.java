package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ForumThreadTagDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumThreadTagSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ForumThreadTag;
import com.example.modules.entertainment_ecosystem.mapper.ForumThreadTagMapper;
import com.example.modules.entertainment_ecosystem.service.ForumThreadTagService;
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
 * Controller for managing ForumThreadTag entities.
 */
@RestController
@RequestMapping("/api/forumthreadtags")
public class ForumThreadTagController extends BaseController<ForumThreadTag, ForumThreadTagDto, ForumThreadTagSimpleDto> {

    public ForumThreadTagController(ForumThreadTagService forumthreadtagService,
                                    ForumThreadTagMapper forumthreadtagMapper) {
        super(forumthreadtagService, forumthreadtagMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ForumThreadTagDto>> getAllForumThreadTags(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ForumThreadTagDto>> searchForumThreadTags(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ForumThreadTag.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumThreadTagDto> getForumThreadTagById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ForumThreadTagDto> createForumThreadTag(
            @Valid @RequestBody ForumThreadTagDto forumthreadtagDto,
            UriComponentsBuilder uriBuilder) {

        ForumThreadTag entity = mapper.toEntity(forumthreadtagDto);
        ForumThreadTag saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/forumthreadtags/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ForumThreadTagDto>> createAllForumThreadTags(
            @Valid @RequestBody List<ForumThreadTagDto> forumthreadtagDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ForumThreadTag> entities = mapper.toEntityList(forumthreadtagDtoList);
        List<ForumThreadTag> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/forumthreadtags").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForumThreadTagDto> updateForumThreadTag(
            @PathVariable Long id,
            @Valid @RequestBody ForumThreadTagDto forumthreadtagDto) {

        ForumThreadTag entityToUpdate = mapper.toEntity(forumthreadtagDto);
        ForumThreadTag updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForumThreadTag(@PathVariable Long id) {
        return doDelete(id);
    }
}