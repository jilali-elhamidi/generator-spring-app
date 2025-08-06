package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.model.Product;
import com.example.modules.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
public ResponseEntity<Product> createProduct(
@Valid @RequestBody Product product,
UriComponentsBuilder uriBuilder) {

Product saved = productService.save(product);
URI location = uriBuilder.path("/api/products/{id}")
.buildAndExpand(saved.getId()).toUri();

return ResponseEntity.created(location).body(saved);
}

@PutMapping("/{id}")
public ResponseEntity<Product> updateProduct(
@PathVariable Long id,
@Valid @RequestBody Product productRequest) {

try {
Product updated = productService.update(id, productRequest);
return ResponseEntity.ok(updated);
} catch (RuntimeException e) {
return ResponseEntity.notFound().build();
}
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteById(id);
    return ResponseEntity.noContent().build();
    }
    }
