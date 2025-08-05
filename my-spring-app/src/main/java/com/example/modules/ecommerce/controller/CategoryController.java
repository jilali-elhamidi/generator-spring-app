package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.model.Category;
import com.example.modules.ecommerce.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorys")
public class CategoryController {

private final CategoryService categoryService;

public CategoryController(CategoryService categoryService) {
this.categoryService = categoryService;
}

@GetMapping
public ResponseEntity<List<Category>> getAllCategorys() {
return ResponseEntity.ok(categoryService.findAll());
}

@GetMapping("/{id}")
public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
return categoryService.findById(id)
.map(ResponseEntity::ok)
.orElse(ResponseEntity.notFound().build());
}

@PostMapping
public ResponseEntity<Category> createCategory(@RequestBody Category category) {
return ResponseEntity.ok(categoryService.save(category));
}

@PutMapping("/{id}")
public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
return ResponseEntity.ok(categoryService.save(category));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    categoryService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
