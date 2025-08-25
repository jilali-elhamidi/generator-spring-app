package com.example.modules.social_media.controller;

import com.example.modules.social_media.dto.CommentDto;
import com.example.modules.social_media.model.Comment;
import com.example.modules.social_media.mapper.CommentMapper;
import com.example.modules.social_media.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService,
                                    CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<Comment> entities = commentService.findAll();
        return ResponseEntity.ok(commentMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        return commentService.findById(id)
                .map(commentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @Valid @RequestBody CommentDto commentDto,
            UriComponentsBuilder uriBuilder) {

        Comment entity = commentMapper.toEntity(commentDto);
        Comment saved = commentService.save(entity);

        URI location = uriBuilder
                                .path("/api/comments/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(commentMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<CommentDto>> createAllComments(
            @Valid @RequestBody List<CommentDto> commentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Comment> entities = commentMapper.toEntityList(commentDtoList);
        List<Comment> savedEntities = commentService.saveAll(entities);

        URI location = uriBuilder.path("/api/comments").build().toUri();

        return ResponseEntity.created(location).body(commentMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentDto commentDto) {


        Comment entityToUpdate = commentMapper.toEntity(commentDto);
        Comment updatedEntity = commentService.update(id, entityToUpdate);

        return ResponseEntity.ok(commentMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        boolean deleted = commentService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}