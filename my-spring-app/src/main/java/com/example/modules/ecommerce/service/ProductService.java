package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Product;
import com.example.modules.ecommerce.repository.ProductRepository;

import com.example.modules.ecommerce.model.Category;
import com.example.modules.ecommerce.repository.CategoryRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ProductService extends BaseService<Product> {

    protected final ProductRepository productRepository;
    
    protected final CategoryRepository categoryRepository;
    

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository)
    {
        super(repository);
        this.productRepository = repository;
        
        this.categoryRepository = categoryRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return productRepository.save(existing);
}

    // Pagination simple
    public Page<Product> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Product> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Product.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Product> saveAll(List<Product> productList) {
        return super.saveAll(productList);
    }

}