package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Category;
import com.example.modules.ecommerce.repository.CategoryRepository;
import com.example.modules.ecommerce.model.Product;
import com.example.modules.ecommerce.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class CategoryService extends BaseService<Category> {

    protected final CategoryRepository categoryRepository;
    private final ProductRepository productsRepository;

    public CategoryService(CategoryRepository repository, ProductRepository productsRepository)
    {
        super(repository);
        this.categoryRepository = repository;
        this.productsRepository = productsRepository;
    }

    @Override
    public Category save(Category category) {
    // ---------- OneToMany ----------
        if (category.getProducts() != null) {
            List<Product> managedProducts = new ArrayList<>();
            for (Product item : category.getProducts()) {
                if (item.getId() != null) {
                    Product existingItem = productsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                     existingItem.setCategory(category);
                     managedProducts.add(existingItem);
                } else {
                    item.setCategory(category);
                    managedProducts.add(item);
                }
            }
            category.setProducts(managedProducts);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return categoryRepository.save(category);
}


    public Category update(Long id, Category categoryRequest) {
        Category existing = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));

    // Copier les champs simples
        existing.setName(categoryRequest.getName());
        existing.setDescription(categoryRequest.getDescription());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getProducts().clear();

        if (categoryRequest.getProducts() != null) {
            for (var item : categoryRequest.getProducts()) {
                Product existingItem;
                if (item.getId() != null) {
                    existingItem = productsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                } else {
                existingItem = item;
                }

                existingItem.setCategory(existing);
                existing.getProducts().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return categoryRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Category> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Category entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getProducts() != null) {
            for (var child : entity.getProducts()) {
                // retirer la référence inverse
                child.setCategory(null);
            }
            entity.getProducts().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}