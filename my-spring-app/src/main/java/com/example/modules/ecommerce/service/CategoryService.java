package com.example.modules.ecommerce.service;

import com.example.core.service.BaseService;
import com.example.modules.ecommerce.model.Category;
import com.example.modules.ecommerce.repository.CategoryRepository;
import com.example.modules.ecommerce.model.Product;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class CategoryService extends BaseService<Category> {

    protected final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository repository)
    {
        super(repository);
        this.categoryRepository = repository;
    }

    @Override
    public Category save(Category category) {

        if (category.getProducts() != null) {
            for (Product item : category.getProducts()) {
            item.setCategory(category);
            }
        }

        return categoryRepository.save(category);
    }


    public Category update(Long id, Category categoryRequest) {
        Category existing = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));

    // Copier les champs simples
        existing.setName(categoryRequest.getName());
        existing.setDescription(categoryRequest.getDescription());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getProducts().clear();
        if (categoryRequest.getProducts() != null) {
            for (var item : categoryRequest.getProducts()) {
            item.setCategory(existing);
            existing.getProducts().add(item);
            }
        }

        return categoryRepository.save(existing);
    }
}