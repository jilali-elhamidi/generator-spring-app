package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ForumPostDto;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.mapper.ForumPostMapper;
import com.example.modules.entertainment_ecosystem.service.ForumPostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/forumposts")
public class ForumPostController {

    private final ForumPostService forumpostService;
    private final ForumPostMapper forumpostMapper;

    public ForumPostController(ForumPostService forumpostService,
                                    ForumPostMapper forumpostMapper) {
        this.forumpostService = forumpostService;
        this.forumpostMapper = forumpostMapper;
    }

    @GetMapping
    public ResponseEntity<List<ForumPostDto>> getAllForumPosts() {
        List<ForumPost> entities = forumpostService.findAll();
        return ResponseEntity.ok(forumpostMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumPostDto> getForumPostById(@PathVariable Long id) {
        return forumpostService.findById(id)
                .map(forumpostMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ForumPostDto> createForumPost(
            @Valid @RequestBody ForumPostDto forumpostDto,
            UriComponentsBuilder uriBuilder) {

        ForumPost entity = forumpostMapper.toEntity(forumpostDto);
        ForumPost saved = forumpostService.save(entity);
        URI location = uriBuilder.path("/api/forumposts/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(forumpostMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForumPostDto> updateForumPost(
            @PathVariable Long id,
            @Valid @RequestBody ForumPostDto forumpostDto) {

        try {
            ForumPost updatedEntity = forumpostService.update(
                    id,
                    forumpostMapper.toEntity(forumpostDto)
            );
            return ResponseEntity.ok(forumpostMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForumPost(@PathVariable Long id) {
        forumpostService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}