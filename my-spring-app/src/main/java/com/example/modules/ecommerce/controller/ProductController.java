package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.ProductDto;
import com.example.modules.ecommerce.model.Product;
import com.example.modules.ecommerce.mapper.ProductMapper;
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
private final ProductMapper productMapper;

public ProductController(ProductService productService, ProductMapper productMapper) {
this.productService = productService;
this.productMapper = productMapper;
}

@GetMapping
public ResponseEntity<List<ProductDto>> getAllProducts() {
    List<Product> entities = productService.findAll();
    return ResponseEntity.ok(productMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return productService.findById(id)
        .map(productMapper::toDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<ProductDto> createProduct(
            @Valid @RequestBody ProductDto productDto,
            UriComponentsBuilder uriBuilder) {

            Product entity = productMapper.toEntity(productDto);
            Product saved = productService.save(entity);
            URI location = uriBuilder.path("/api/products/{id}")
            .buildAndExpand(saved.getId()).toUri();
            return ResponseEntity.created(location).body(productMapper.toDto(saved));
            }

            @PutMapping("/{id}")
            public ResponseEntity<ProductDto> updateProduct(
                @PathVariable Long id,
                @Valid @RequestBody ProductDto productDto) {

                try {
                Product updatedEntity = productService.update(id, productMapper.toEntity(productDto));
                return ResponseEntity.ok(productMapper.toDto(updatedEntity));
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