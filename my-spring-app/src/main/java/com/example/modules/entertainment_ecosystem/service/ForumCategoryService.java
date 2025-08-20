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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ForumCategoryService extends BaseService<ForumCategory> {

    protected final ForumCategoryRepository forumcategoryRepository;
    private final ForumThreadRepository threadsRepository;
    private final ForumCategoryRepository parentCategoryRepository;
    private final ForumCategoryRepository childCategoriesRepository;
    private final ForumModeratorRepository moderatorsRepository;

    public ForumCategoryService(ForumCategoryRepository repository,ForumThreadRepository threadsRepository,ForumCategoryRepository parentCategoryRepository,ForumCategoryRepository childCategoriesRepository,ForumModeratorRepository moderatorsRepository)
    {
        super(repository);
        this.forumcategoryRepository = repository;
        this.threadsRepository = threadsRepository;
        this.parentCategoryRepository = parentCategoryRepository;
        this.childCategoriesRepository = childCategoriesRepository;
        this.moderatorsRepository = moderatorsRepository;
    }

    @Override
    public ForumCategory save(ForumCategory forumcategory) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (forumcategory.getThreads() != null) {
            List<ForumThread> managedThreads = new ArrayList<>();
            for (ForumThread item : forumcategory.getThreads()) {
            if (item.getId() != null) {
            ForumThread existingItem = threadsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ForumThread not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setCategory(forumcategory);
            managedThreads.add(existingItem);
            } else {
            item.setCategory(forumcategory);
            managedThreads.add(item);
            }
            }
            forumcategory.setThreads(managedThreads);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (forumcategory.getChildCategories() != null) {
            List<ForumCategory> managedChildCategories = new ArrayList<>();
            for (ForumCategory item : forumcategory.getChildCategories()) {
            if (item.getId() != null) {
            ForumCategory existingItem = childCategoriesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("ForumCategory not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setParentCategory(forumcategory);
            managedChildCategories.add(existingItem);
            } else {
            item.setParentCategory(forumcategory);
            managedChildCategories.add(item);
            }
            }
            forumcategory.setChildCategories(managedChildCategories);
            }
        
    

    


    

    

    

    
        if (forumcategory.getModerators() != null
        && !forumcategory.getModerators().isEmpty()) {

        List<ForumModerator> attachedModerators = forumcategory.getModerators().stream()
        .map(item -> moderatorsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ForumModerator not found with id " + item.getId())))
        .toList();

        forumcategory.setModerators(attachedModerators);

        // côté propriétaire (ForumModerator → ForumCategory)
        attachedModerators.forEach(it -> it.getModeratedCategories().add(forumcategory));
        }
    

    
    if (forumcategory.getParentCategory() != null
        && forumcategory.getParentCategory().getId() != null) {
        ForumCategory existingParentCategory = parentCategoryRepository.findById(
        forumcategory.getParentCategory().getId()
        ).orElseThrow(() -> new RuntimeException("ForumCategory not found"));
        forumcategory.setParentCategory(existingParentCategory);
        }
    
    
    

        return forumcategoryRepository.save(forumcategory);
    }


    public ForumCategory update(Long id, ForumCategory forumcategoryRequest) {
        ForumCategory existing = forumcategoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ForumCategory not found"));

    // Copier les champs simples
        existing.setName(forumcategoryRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle
        if (forumcategoryRequest.getParentCategory() != null &&
        forumcategoryRequest.getParentCategory().getId() != null) {

        ForumCategory existingParentCategory = parentCategoryRepository.findById(
        forumcategoryRequest.getParentCategory().getId()
        ).orElseThrow(() -> new RuntimeException("ForumCategory not found"));

        existing.setParentCategory(existingParentCategory);
        } else {
        existing.setParentCategory(null);
        }

// Relations ManyToMany : synchronisation sécurisée
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

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getThreads().clear();

        if (forumcategoryRequest.getThreads() != null) {
        for (var item : forumcategoryRequest.getThreads()) {
        ForumThread existingItem;
        if (item.getId() != null) {
        existingItem = threadsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ForumThread not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setCategory(existing);

        // Ajouter directement dans la collection existante
        existing.getThreads().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getChildCategories().clear();

        if (forumcategoryRequest.getChildCategories() != null) {
        for (var item : forumcategoryRequest.getChildCategories()) {
        ForumCategory existingItem;
        if (item.getId() != null) {
        existingItem = childCategoriesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("ForumCategory not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setParentCategory(existing);

        // Ajouter directement dans la collection existante
        existing.getChildCategories().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    


        return forumcategoryRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<ForumCategory> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

ForumCategory entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getThreads() != null) {
        for (var child : entity.getThreads()) {
        
            child.setCategory(null); // retirer la référence inverse
        
        }
        entity.getThreads().clear();
        }
    

    

    
        if (entity.getChildCategories() != null) {
        for (var child : entity.getChildCategories()) {
        
            child.setParentCategory(null); // retirer la référence inverse
        
        }
        entity.getChildCategories().clear();
        }
    

    


// --- Dissocier ManyToMany ---

    

    

    

    
        if (entity.getModerators() != null) {
        for (ForumModerator item : new ArrayList<>(entity.getModerators())) {
        
            item.getModeratedCategories().remove(entity); // retire côté inverse
        
        }
        entity.getModerators().clear(); // puis vide côté courant
        }
    



// --- Dissocier OneToOne ---

    

    

    

    


// --- Dissocier ManyToOne ---

    

    
        if (entity.getParentCategory() != null) {
        entity.setParentCategory(null);
        }
    

    

    


repository.delete(entity);
return true;
}
}