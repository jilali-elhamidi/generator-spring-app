package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.CategoryDto;
import com.example.modules.ecommerce.model.Category;
import com.example.modules.ecommerce.mapper.CategoryMapper;
import com.example.modules.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categorys")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService,
                                    CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategorys() {
        List<Category> entities = categoryService.findAll();
        return ResponseEntity.ok(categoryMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(categoryMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CategoryDto categoryDto,
            UriComponentsBuilder uriBuilder) {

        Category entity = categoryMapper.toEntity(categoryDto);
        Category saved = categoryService.save(entity);

        URI location = uriBuilder
                                .path("/api/categorys/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(categoryMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<CategoryDto>> createAllCategorys(
            @Valid @RequestBody List<CategoryDto> categoryDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Category> entities = categoryMapper.toEntityList(categoryDtoList);
        List<Category> savedEntities = categoryService.saveAll(entities);

        URI location = uriBuilder.path("/api/categorys").build().toUri();

        return ResponseEntity.created(location).body(categoryMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDto categoryDto) {


        Category entityToUpdate = categoryMapper.toEntity(categoryDto);
        Category updatedEntity = categoryService.update(id, entityToUpdate);

        return ResponseEntity.ok(categoryMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        boolean deleted = categoryService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}