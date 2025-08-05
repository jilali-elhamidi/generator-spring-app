package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.model.Product;
import com.example.modules.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

private final ProductService productService;

public ProductController(ProductService productService) {
this.productService = productService;
}

@GetMapping
public ResponseEntity<List<Product>> getAllProducts() {
return ResponseEntity.ok(productService.findAll());
}

@GetMapping("/{id}")
public ResponseEntity<Product> getProductById(@PathVariable Long id) {
return productService.findById(id)
.map(ResponseEntity::ok)
.orElse(ResponseEntity.notFound().build());
}

@PostMapping
public ResponseEntity<Product> createProduct(@RequestBody Product product) {
return ResponseEntity.ok(productService.save(product));
}

@PutMapping("/{id}")
public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
return ResponseEntity.ok(productService.save(product));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
