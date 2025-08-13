package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import com.example.modules.entertainment_ecosystem.repository.ForumCategoryRepository;
import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import com.example.modules.entertainment_ecosystem.repository.ForumCategoryRepository;
import com.example.modules.entertainment_ecosystem.model.ForumCategory;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ForumCategoryService extends BaseService<ForumCategory> {

    protected final ForumCategoryRepository forumcategoryRepository;
    private final ForumCategoryRepository parentCategoryRepository;

    public ForumCategoryService(ForumCategoryRepository repository,ForumCategoryRepository parentCategoryRepository)
    {
        super(repository);
        this.forumcategoryRepository = repository;
        this.parentCategoryRepository = parentCategoryRepository;
    }

    @Override
    public ForumCategory save(ForumCategory forumcategory) {

        if (forumcategory.getParentCategory() != null && forumcategory.getParentCategory().getId() != null) {
        ForumCategory parentCategory = parentCategoryRepository.findById(forumcategory.getParentCategory().getId())
                .orElseThrow(() -> new RuntimeException("ForumCategory not found"));
        forumcategory.setParentCategory(parentCategory);
        }

        if (forumcategory.getThreads() != null) {
            for (ForumThread item : forumcategory.getThreads()) {
            item.setCategory(forumcategory);
            }
        }

        if (forumcategory.getChildCategories() != null) {
            for (ForumCategory item : forumcategory.getChildCategories()) {
            item.setParentCategory(forumcategory);
            }
        }

        return forumcategoryRepository.save(forumcategory);
    }


    public ForumCategory update(Long id, ForumCategory forumcategoryRequest) {
        ForumCategory existing = forumcategoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ForumCategory not found"));

    // Copier les champs simples
        existing.setName(forumcategoryRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

        if (forumcategoryRequest.getParentCategory() != null && forumcategoryRequest.getParentCategory().getId() != null) {
        ForumCategory parentCategory = parentCategoryRepository.findById(forumcategoryRequest.getParentCategory().getId())
                .orElseThrow(() -> new RuntimeException("ForumCategory not found"));
        existing.setParentCategory(parentCategory);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getThreads().clear();
        if (forumcategoryRequest.getThreads() != null) {
            for (var item : forumcategoryRequest.getThreads()) {
            item.setCategory(existing);
            existing.getThreads().add(item);
            }
        }

        existing.getChildCategories().clear();
        if (forumcategoryRequest.getChildCategories() != null) {
            for (var item : forumcategoryRequest.getChildCategories()) {
            item.setParentCategory(existing);
            existing.getChildCategories().add(item);
            }
        }

    

    

    


        return forumcategoryRepository.save(existing);
    }
}