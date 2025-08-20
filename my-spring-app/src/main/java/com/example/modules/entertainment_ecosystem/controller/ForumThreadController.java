package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ForumThreadDto;
import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.mapper.ForumThreadMapper;
import com.example.modules.entertainment_ecosystem.service.ForumThreadService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/forumthreads")
public class ForumThreadController {

    private final ForumThreadService forumthreadService;
    private final ForumThreadMapper forumthreadMapper;

    public ForumThreadController(ForumThreadService forumthreadService,
                                    ForumThreadMapper forumthreadMapper) {
        this.forumthreadService = forumthreadService;
        this.forumthreadMapper = forumthreadMapper;
    }

    @GetMapping
    public ResponseEntity<List<ForumThreadDto>> getAllForumThreads() {
        List<ForumThread> entities = forumthreadService.findAll();
        return ResponseEntity.ok(forumthreadMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumThreadDto> getForumThreadById(@PathVariable Long id) {
        return forumthreadService.findById(id)
                .map(forumthreadMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ForumThreadDto> createForumThread(
            @Valid @RequestBody ForumThreadDto forumthreadDto,
            UriComponentsBuilder uriBuilder) {

        ForumThread entity = forumthreadMapper.toEntity(forumthreadDto);
        ForumThread saved = forumthreadService.save(entity);

        URI location = uriBuilder
                                .path("/api/forumthreads/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(forumthreadMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForumThreadDto> updateForumThread(
            @PathVariable Long id,
            @Valid @RequestBody ForumThreadDto forumthreadDto) {


        ForumThread entityToUpdate = forumthreadMapper.toEntity(forumthreadDto);
        ForumThread updatedEntity = forumthreadService.update(id, entityToUpdate);

        return ResponseEntity.ok(forumthreadMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForumThread(@PathVariable Long id) {
        boolean deleted = forumthreadService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}