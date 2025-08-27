package com.example.modules.ecommerce.controller;

import com.example.modules.ecommerce.dto.ProductDto;
import com.example.modules.ecommerce.dtosimple.ProductSimpleDto;
import com.example.modules.ecommerce.model.Product;
import com.example.modules.ecommerce.mapper.ProductMapper;
import com.example.modules.ecommerce.service.ProductService;
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
 * Controller for managing Product entities.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController extends BaseController<Product, ProductDto, ProductSimpleDto> {

    public ProductController(ProductService productService,
                                    ProductMapper productMapper) {
        super(productService, productMapper);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(Pageable pageable) {
        return doGetAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchProducts(
            @RequestParam Map<String, String> filters,
            Pageable pageable
    ) {
        return doSearch(Product.class, filters, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return doGetById(id);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @Valid @RequestBody ProductDto productDto,
            UriComponentsBuilder uriBuilder) {

        Product entity = mapper.toEntity(productDto);
        Product saved = service.save(entity);

        URI location = uriBuilder
                .path("/api/products/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ProductDto>> createAllProducts(
            @Valid @RequestBody List<ProductDto> productDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Product> entities = mapper.toEntityList(productDtoList);
        List<Product> savedEntities = service.saveAll(entities);

        URI location = uriBuilder.path("/api/products").build().toUri();

        return ResponseEntity.created(location).body(mapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto productDto) {

        Product entityToUpdate = mapper.toEntity(productDto);
        Product updatedEntity = service.update(id, entityToUpdate);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return doDelete(id);
    }
}