package com.example.modules.social_media.controller;

import com.example.modules.social_media.dto.PostDto;
import com.example.modules.social_media.dtosimple.PostSimpleDto;
import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.mapper.PostMapper;
import com.example.modules.social_media.service.PostService;
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
 * Controller for managing Post entities.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController extends BaseController<Post, PostDto, PostSimpleDto> {

    public PostController(PostService postService,
                                    PostMapper postMapper) {
        super(postService, postMapper);
    }

    @GetMapping
    public ResponseEntity<Page<PostDto>> getAllPosts(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PostDto>> searchPosts(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Post.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody PostDto postDto,
            UriComponentsBuilder uriBuilder) {

        Post entity = mapper.toEntity(postDto);
        Post saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/posts/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PostDto>> createAllPosts(
            @Valid @RequestBody List<PostDto> postDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Post> entities = mapper.toEntityList(postDtoList);
        List<Post> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/posts").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostDto postDto) {

        Post entityToUpdate = mapper.toEntity(postDto);
        Post updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        return doDelete(id);
    }
}