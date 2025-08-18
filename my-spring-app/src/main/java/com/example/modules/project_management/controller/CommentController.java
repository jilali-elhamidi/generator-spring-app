package com.example.modules.project_management.controller;

import com.example.modules.project_management.dto.CommentDto;
import com.example.modules.project_management.model.Comment;
import com.example.modules.project_management.mapper.CommentMapper;
import com.example.modules.project_management.service.CommentService;
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
        URI location = uriBuilder.path("/api/comments/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(commentMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<CommentDto> updateComment(
                @PathVariable Long id,
                @RequestBody CommentDto commentDto) {

                // Transformer le DTO en entity pour le service
                Comment entityToUpdate = commentMapper.toEntity(commentDto);

                // Appel du service update
                Comment updatedEntity = commentService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                CommentDto updatedDto = commentMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
                    boolean deleted = commentService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}