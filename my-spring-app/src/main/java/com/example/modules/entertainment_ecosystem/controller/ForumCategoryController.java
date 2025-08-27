package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ForumCategoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumCategorySimpleDto;
import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import com.example.modules.entertainment_ecosystem.mapper.ForumCategoryMapper;
import com.example.modules.entertainment_ecosystem.service.ForumCategoryService;
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
 * Controller for managing ForumCategory entities.
 */
@RestController
@RequestMapping("/api/forumcategorys")
public class ForumCategoryController extends BaseController<ForumCategory, ForumCategoryDto, ForumCategorySimpleDto> {

    public ForumCategoryController(ForumCategoryService forumcategoryService,
                                    ForumCategoryMapper forumcategoryMapper) {
        super(forumcategoryService, forumcategoryMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ForumCategoryDto>> getAllForumCategorys(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ForumCategoryDto>> searchForumCategorys(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(ForumCategory.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumCategoryDto> getForumCategoryById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ForumCategoryDto> createForumCategory(
            @Valid @RequestBody ForumCategoryDto forumcategoryDto,
            UriComponentsBuilder uriBuilder) {

        ForumCategory entity = mapper.toEntity(forumcategoryDto);
        ForumCategory saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/forumcategorys/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ForumCategoryDto>> createAllForumCategorys(
            @Valid @RequestBody List<ForumCategoryDto> forumcategoryDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ForumCategory> entities = mapper.toEntityList(forumcategoryDtoList);
        List<ForumCategory> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/forumcategorys").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForumCategoryDto> updateForumCategory(
            @PathVariable Long id,
            @Valid @RequestBody ForumCategoryDto forumcategoryDto) {

        ForumCategory entityToUpdate = mapper.toEntity(forumcategoryDto);
        ForumCategory updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForumCategory(@PathVariable Long id) {
        return doDelete(id);
    }
}