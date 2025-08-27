package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Category;
import com.example.modules.ecommerce.repository.CategoryRepository;

import com.example.modules.ecommerce.model.Product;
import com.example.modules.ecommerce.repository.ProductRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class CategoryService extends BaseService<Category> {

    protected final CategoryRepository categoryRepository;
    
    protected final ProductRepository productsRepository;
    

    public CategoryService(CategoryRepository repository, ProductRepository productsRepository)
    {
        super(repository);
        this.categoryRepository = repository;
        
        this.productsRepository = productsRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public Category update(Long id, Category categoryRequest) {
        Category existing = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));

    // Copier les champs simples
        existing.setName(categoryRequest.getName());
        existing.setDescription(categoryRequest.getDescription());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<Category> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Category> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Category.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Category> saveAll(List<Category> categoryList) {
        return super.saveAll(categoryList);
    }

}