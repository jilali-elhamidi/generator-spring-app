package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Product;
import com.example.modules.ecommerce.repository.ProductRepository;
import com.example.modules.ecommerce.model.Category;
import com.example.modules.ecommerce.repository.CategoryRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProductService extends BaseService<Product> {

    protected final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository) {
    super(repository);
    this.productRepository = repository;
    this.categoryRepository = categoryRepository;
}

    @Override
    public Product save(Product product) {
        if (product.getCategory() != null && product.getCategory().getId() != null) {
        Category category = categoryRepository.findById(product.getCategory().getId())
            .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);
    }
    return productRepository.save(product);
    }

    public Product update(Long id, Product productRequest) {
    Product existing = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found"));

// Copier les champs simples
    existing.setName(productRequest.getName());
    existing.setStock(productRequest.getStock());
    existing.setPrice(productRequest.getPrice());
    existing.setDescription(productRequest.getDescription());

// Relations ManyToOne : mise à jour conditionnelle
    if (productRequest.getCategory() != null &&productRequest.getCategory().getId() != null) {
    Category category = categoryRepository.findById(productRequest.getCategory().getId())
        .orElseThrow(() -> new RuntimeException("Category not found"));
    existing.setCategory(category);
    }
    // Sinon on garde la relation existante

// Relations OneToMany : synchronisation sécurisée

    return productRepository.save(existing);
    }
}
