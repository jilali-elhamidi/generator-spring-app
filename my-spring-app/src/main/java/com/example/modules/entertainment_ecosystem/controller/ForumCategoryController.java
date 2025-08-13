package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ForumCategoryDto;
import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import com.example.modules.entertainment_ecosystem.mapper.ForumCategoryMapper;
import com.example.modules.entertainment_ecosystem.service.ForumCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/forumcategorys")
public class ForumCategoryController {

    private final ForumCategoryService forumcategoryService;
    private final ForumCategoryMapper forumcategoryMapper;

    public ForumCategoryController(ForumCategoryService forumcategoryService,
                                    ForumCategoryMapper forumcategoryMapper) {
        this.forumcategoryService = forumcategoryService;
        this.forumcategoryMapper = forumcategoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<ForumCategoryDto>> getAllForumCategorys() {
        List<ForumCategory> entities = forumcategoryService.findAll();
        return ResponseEntity.ok(forumcategoryMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumCategoryDto> getForumCategoryById(@PathVariable Long id) {
        return forumcategoryService.findById(id)
                .map(forumcategoryMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ForumCategoryDto> createForumCategory(
            @Valid @RequestBody ForumCategoryDto forumcategoryDto,
            UriComponentsBuilder uriBuilder) {

        ForumCategory entity = forumcategoryMapper.toEntity(forumcategoryDto);
        ForumCategory saved = forumcategoryService.save(entity);
        URI location = uriBuilder.path("/api/forumcategorys/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(forumcategoryMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForumCategoryDto> updateForumCategory(
            @PathVariable Long id,
            @Valid @RequestBody ForumCategoryDto forumcategoryDto) {

        try {
            ForumCategory updatedEntity = forumcategoryService.update(
                    id,
                    forumcategoryMapper.toEntity(forumcategoryDto)
            );
            return ResponseEntity.ok(forumcategoryMapper.toDto(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForumCategory(@PathVariable Long id) {
        forumcategoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}