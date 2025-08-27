package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.CategoryDto;
import com.example.modules.ecommerce.dtosimple.CategorySimpleDto;
import com.example.modules.ecommerce.model.Category;
import com.example.modules.ecommerce.mapper.CategoryMapper;
import com.example.modules.ecommerce.service.CategoryService;
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
 * Controller for managing Category entities.
 */
@RestController
@RequestMapping("/api/categorys")
public class CategoryController extends BaseController<Category, CategoryDto, CategorySimpleDto> {

    public CategoryController(CategoryService categoryService,
                                    CategoryMapper categoryMapper) {
        super(categoryService, categoryMapper);
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDto>> getAllCategorys(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CategoryDto>> searchCategorys(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Category.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CategoryDto categoryDto,
            UriComponentsBuilder uriBuilder) {

        Category entity = mapper.toEntity(categoryDto);
        Category saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/categorys/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<CategoryDto>> createAllCategorys(
            @Valid @RequestBody List<CategoryDto> categoryDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Category> entities = mapper.toEntityList(categoryDtoList);
        List<Category> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/categorys").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDto categoryDto) {

        Category entityToUpdate = mapper.toEntity(categoryDto);
        Category updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        return doDelete(id);
    }
}