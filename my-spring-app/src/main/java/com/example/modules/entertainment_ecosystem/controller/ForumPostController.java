package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ForumPostDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumPostSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.mapper.ForumPostMapper;
import com.example.modules.entertainment_ecosystem.service.ForumPostService;
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
 * Controller for managing ForumPost entities.
 */
@RestController
@RequestMapping("/api/forumposts")
public class ForumPostController extends BaseController<ForumPost, ForumPostDto, ForumPostSimpleDto> {

    public ForumPostController(ForumPostService forumpostService,
                                    ForumPostMapper forumpostMapper) {
        super(forumpostService, forumpostMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ForumPostDto>> getAllForumPosts(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ForumPostDto>> searchForumPosts(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ForumPost.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumPostDto> getForumPostById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ForumPostDto> createForumPost(
            @Valid @RequestBody ForumPostDto forumpostDto,
            UriComponentsBuilder uriBuilder) {

        ForumPost entity = mapper.toEntity(forumpostDto);
        ForumPost saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/forumposts/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ForumPostDto>> createAllForumPosts(
            @Valid @RequestBody List<ForumPostDto> forumpostDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ForumPost> entities = mapper.toEntityList(forumpostDtoList);
        List<ForumPost> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/forumposts").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForumPostDto> updateForumPost(
            @PathVariable Long id,
            @Valid @RequestBody ForumPostDto forumpostDto) {

        ForumPost entityToUpdate = mapper.toEntity(forumpostDto);
        ForumPost updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForumPost(@PathVariable Long id) {
        return doDelete(id);
    }
}