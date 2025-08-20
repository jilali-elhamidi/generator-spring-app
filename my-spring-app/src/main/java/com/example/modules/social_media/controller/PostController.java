package com.example.modules.social_media.controller;

import com.example.modules.social_media.dto.PostDto;
import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.mapper.PostMapper;
import com.example.modules.social_media.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService,
                                    PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<Post> entities = postService.findAll();
        return ResponseEntity.ok(postMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return postService.findById(id)
                .map(postMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody PostDto postDto,
            UriComponentsBuilder uriBuilder) {

        Post entity = postMapper.toEntity(postDto);
        Post saved = postService.save(entity);

        URI location = uriBuilder
                                .path("/api/posts/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(postMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostDto postDto) {


        Post entityToUpdate = postMapper.toEntity(postDto);
        Post updatedEntity = postService.update(id, entityToUpdate);

        return ResponseEntity.ok(postMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        boolean deleted = postService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}