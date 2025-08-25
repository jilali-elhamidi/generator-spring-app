package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Product;
import com.example.modules.ecommerce.repository.ProductRepository;
import com.example.modules.ecommerce.model.Category;
import com.example.modules.ecommerce.repository.CategoryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ProductService extends BaseService<Product> {

    protected final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository)
    {
        super(repository);
        this.productRepository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product save(Product product) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (product.getCategory() != null) {
            if (product.getCategory().getId() != null) {
                Category existingCategory = categoryRepository.findById(
                    product.getCategory().getId()
                ).orElseThrow(() -> new RuntimeException("Category not found with id "
                    + product.getCategory().getId()));
                product.setCategory(existingCategory);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Category newCategory = categoryRepository.save(product.getCategory());
                product.setCategory(newCategory);
            }
        }
        
    // ---------- OneToOne ----------
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

    // ---------- Relations ManyToOne ----------
        if (productRequest.getCategory() != null &&
            productRequest.getCategory().getId() != null) {

            Category existingCategory = categoryRepository.findById(
                productRequest.getCategory().getId()
            ).orElseThrow(() -> new RuntimeException("Category not found"));

            existing.setCategory(existingCategory);
        } else {
            existing.setCategory(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return productRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Product> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Product entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getCategory() != null) {
            entity.setCategory(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Product> saveAll(List<Product> productList) {

        return productRepository.saveAll(productList);
    }

}