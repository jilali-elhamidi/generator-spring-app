package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import com.example.modules.entertainment_ecosystem.repository.ForumCategoryRepository;

import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.repository.ForumThreadRepository;

import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import com.example.modules.entertainment_ecosystem.repository.ForumCategoryRepository;

import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import com.example.modules.entertainment_ecosystem.repository.ForumCategoryRepository;

import com.example.modules.entertainment_ecosystem.model.ForumModerator;
import com.example.modules.entertainment_ecosystem.repository.ForumModeratorRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ForumCategoryService extends BaseService<ForumCategory> {

    protected final ForumCategoryRepository forumcategoryRepository;
    
    protected final ForumThreadRepository threadsRepository;
    
    protected final ForumCategoryRepository parentCategoryRepository;
    
    protected final ForumCategoryRepository childCategoriesRepository;
    
    protected final ForumModeratorRepository moderatorsRepository;
    

    public ForumCategoryService(ForumCategoryRepository repository, ForumThreadRepository threadsRepository, ForumCategoryRepository parentCategoryRepository, ForumCategoryRepository childCategoriesRepository, ForumModeratorRepository moderatorsRepository)
    {
        super(repository);
        this.forumcategoryRepository = repository;
        
        this.threadsRepository = threadsRepository;
        
        this.parentCategoryRepository = parentCategoryRepository;
        
        this.childCategoriesRepository = childCategoriesRepository;
        
        this.moderatorsRepository = moderatorsRepository;
        
    }

    @Transactional
    @Override
    public ForumCategory save(ForumCategory forumcategory) {
    // ---------- OneToMany ----------
        if (forumcategory.getThreads() != null) {
            List<ForumThread> managedThreads = new ArrayList<>();
            for (ForumThread item : forumcategory.getThreads()) {
                if (item.getId() != null) {
                    ForumThread existingItem = threadsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumThread not found"));

                     existingItem.setCategory(forumcategory);
                     managedThreads.add(existingItem);
                } else {
                    item.setCategory(forumcategory);
                    managedThreads.add(item);
                }
            }
            forumcategory.setThreads(managedThreads);
        }
    
        if (forumcategory.getChildCategories() != null) {
            List<ForumCategory> managedChildCategories = new ArrayList<>();
            for (ForumCategory item : forumcategory.getChildCategories()) {
                if (item.getId() != null) {
                    ForumCategory existingItem = childCategoriesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumCategory not found"));

                     existingItem.setParentCategory(forumcategory);
                     managedChildCategories.add(existingItem);
                } else {
                    item.setParentCategory(forumcategory);
                    managedChildCategories.add(item);
                }
            }
            forumcategory.setChildCategories(managedChildCategories);
        }
    
    // ---------- ManyToMany ----------
        if (forumcategory.getModerators() != null &&
            !forumcategory.getModerators().isEmpty()) {

            List<ForumModerator> attachedModerators = new ArrayList<>();
            for (ForumModerator item : forumcategory.getModerators()) {
                if (item.getId() != null) {
                    ForumModerator existingItem = moderatorsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumModerator not found with id " + item.getId()));
                    attachedModerators.add(existingItem);
                } else {

                    ForumModerator newItem = moderatorsRepository.save(item);
                    attachedModerators.add(newItem);
                }
            }

            forumcategory.setModerators(attachedModerators);

            // côté propriétaire (ForumModerator → ForumCategory)
            attachedModerators.forEach(it -> it.getModeratedCategories().add(forumcategory));
        }
        
    // ---------- ManyToOne ----------
        if (forumcategory.getParentCategory() != null) {
            if (forumcategory.getParentCategory().getId() != null) {
                ForumCategory existingParentCategory = parentCategoryRepository.findById(
                    forumcategory.getParentCategory().getId()
                ).orElseThrow(() -> new RuntimeException("ForumCategory not found with id "
                    + forumcategory.getParentCategory().getId()));
                forumcategory.setParentCategory(existingParentCategory);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ForumCategory newParentCategory = parentCategoryRepository.save(forumcategory.getParentCategory());
                forumcategory.setParentCategory(newParentCategory);
            }
        }
        
    // ---------- OneToOne ----------
    return forumcategoryRepository.save(forumcategory);
}

    @Transactional
    @Override
    public ForumCategory update(Long id, ForumCategory forumcategoryRequest) {
        ForumCategory existing = forumcategoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ForumCategory not found"));

    // Copier les champs simples
        existing.setName(forumcategoryRequest.getName());

    // ---------- Relations ManyToOne ----------
        if (forumcategoryRequest.getParentCategory() != null &&
            forumcategoryRequest.getParentCategory().getId() != null) {

            ForumCategory existingParentCategory = parentCategoryRepository.findById(
                forumcategoryRequest.getParentCategory().getId()
            ).orElseThrow(() -> new RuntimeException("ForumCategory not found"));

            existing.setParentCategory(existingParentCategory);
        } else {
            existing.setParentCategory(null);
        }
        
    // ---------- Relations ManyToMany ----------
        if (forumcategoryRequest.getModerators() != null) {
            existing.getModerators().clear();

            List<ForumModerator> moderatorsList = forumcategoryRequest.getModerators().stream()
                .map(item -> moderatorsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ForumModerator not found")))
                .collect(Collectors.toList());

            existing.getModerators().addAll(moderatorsList);

            // Mettre à jour le côté inverse
            moderatorsList.forEach(it -> {
                if (!it.getModeratedCategories().contains(existing)) {
                    it.getModeratedCategories().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getThreads().clear();

        if (forumcategoryRequest.getThreads() != null) {
            for (var item : forumcategoryRequest.getThreads()) {
                ForumThread existingItem;
                if (item.getId() != null) {
                    existingItem = threadsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumThread not found"));
                } else {
                existingItem = item;
                }

                existingItem.setCategory(existing);
                existing.getThreads().add(existingItem);
            }
        }
        
        existing.getChildCategories().clear();

        if (forumcategoryRequest.getChildCategories() != null) {
            for (var item : forumcategoryRequest.getChildCategories()) {
                ForumCategory existingItem;
                if (item.getId() != null) {
                    existingItem = childCategoriesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumCategory not found"));
                } else {
                existingItem = item;
                }

                existingItem.setParentCategory(existing);
                existing.getChildCategories().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return forumcategoryRepository.save(existing);
}

    // Pagination simple
    public Page<ForumCategory> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ForumCategory> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ForumCategory.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ForumCategory> saveAll(List<ForumCategory> forumcategoryList) {
        return super.saveAll(forumcategoryList);
    }

}