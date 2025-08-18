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
        URI location = uriBuilder.path("/api/categorys/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(categoryMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<CategoryDto> updateCategory(
                @PathVariable Long id,
                @RequestBody CategoryDto categoryDto) {

                // Transformer le DTO en entity pour le service
                Category entityToUpdate = categoryMapper.toEntity(categoryDto);

                // Appel du service update
                Category updatedEntity = categoryService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                CategoryDto updatedDto = categoryMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
                    boolean deleted = categoryService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}