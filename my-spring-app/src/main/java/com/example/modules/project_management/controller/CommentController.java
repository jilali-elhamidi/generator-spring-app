package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.CommentDto;
import com.example.modules.project_management.dtosimple.CommentSimpleDto;
import com.example.modules.project_management.model.Comment;
import com.example.modules.project_management.mapper.CommentMapper;
import com.example.modules.project_management.service.CommentService;
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
 * Controller for managing Comment entities.
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController extends BaseController<Comment, CommentDto, CommentSimpleDto> {

    public CommentController(CommentService commentService,
                                    CommentMapper commentMapper) {
        super(commentService, commentMapper);
    }

    @GetMapping
    public ResponseEntity<Page<CommentDto>> getAllComments(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CommentDto>> searchComments(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Comment.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @Valid @RequestBody CommentDto commentDto,
            UriComponentsBuilder uriBuilder) {

        Comment entity = mapper.toEntity(commentDto);
        Comment saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/comments/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<CommentDto>> createAllComments(
            @Valid @RequestBody List<CommentDto> commentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Comment> entities = mapper.toEntityList(commentDtoList);
        List<Comment> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/comments").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentDto commentDto) {

        Comment entityToUpdate = mapper.toEntity(commentDto);
        Comment updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        return doDelete(id);
    }
}