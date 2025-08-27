package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ForumThreadDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumThreadSimpleDto;
import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.mapper.ForumThreadMapper;
import com.example.modules.entertainment_ecosystem.service.ForumThreadService;
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
 * Controller for managing ForumThread entities.
 */
@RestController
@RequestMapping("/api/forumthreads")
public class ForumThreadController extends BaseController<ForumThread, ForumThreadDto, ForumThreadSimpleDto> {

    public ForumThreadController(ForumThreadService forumthreadService,
                                    ForumThreadMapper forumthreadMapper) {
        super(forumthreadService, forumthreadMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ForumThreadDto>> getAllForumThreads(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ForumThreadDto>> searchForumThreads(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ForumThread.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumThreadDto> getForumThreadById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ForumThreadDto> createForumThread(
            @Valid @RequestBody ForumThreadDto forumthreadDto,
            UriComponentsBuilder uriBuilder) {

        ForumThread entity = mapper.toEntity(forumthreadDto);
        ForumThread saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/forumthreads/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ForumThreadDto>> createAllForumThreads(
            @Valid @RequestBody List<ForumThreadDto> forumthreadDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ForumThread> entities = mapper.toEntityList(forumthreadDtoList);
        List<ForumThread> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/forumthreads").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForumThreadDto> updateForumThread(
            @PathVariable Long id,
            @Valid @RequestBody ForumThreadDto forumthreadDto) {

        ForumThread entityToUpdate = mapper.toEntity(forumthreadDto);
        ForumThread updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForumThread(@PathVariable Long id) {
        return doDelete(id);
    }
}