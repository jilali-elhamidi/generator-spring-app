package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ForumModerator;
import com.example.modules.entertainment_ecosystem.repository.ForumModeratorRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import com.example.modules.entertainment_ecosystem.model.ForumCategory;
import com.example.modules.entertainment_ecosystem.repository.ForumCategoryRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ForumModeratorService extends BaseService<ForumModerator> {

    protected final ForumModeratorRepository forummoderatorRepository;
    
    protected final UserProfileRepository userRepository;
    
    protected final ForumCategoryRepository moderatedCategoriesRepository;
    

    public ForumModeratorService(ForumModeratorRepository repository, UserProfileRepository userRepository, ForumCategoryRepository moderatedCategoriesRepository)
    {
        super(repository);
        this.forummoderatorRepository = repository;
        
        this.userRepository = userRepository;
        
        this.moderatedCategoriesRepository = moderatedCategoriesRepository;
        
    }

    @Transactional
    @Override
    public ForumModerator save(ForumModerator forummoderator) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (forummoderator.getModeratedCategories() != null &&
            !forummoderator.getModeratedCategories().isEmpty()) {

            List<ForumCategory> attachedModeratedCategories = new ArrayList<>();
            for (ForumCategory item : forummoderator.getModeratedCategories()) {
                if (item.getId() != null) {
                    ForumCategory existingItem = moderatedCategoriesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumCategory not found with id " + item.getId()));
                    attachedModeratedCategories.add(existingItem);
                } else {

                    ForumCategory newItem = moderatedCategoriesRepository.save(item);
                    attachedModeratedCategories.add(newItem);
                }
            }

            forummoderator.setModeratedCategories(attachedModeratedCategories);

            // côté propriétaire (ForumCategory → ForumModerator)
            attachedModeratedCategories.forEach(it -> it.getModerators().add(forummoderator));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
        if (forummoderator.getUser() != null) {
            if (forummoderator.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(forummoderator.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                        + forummoderator.getUser().getId()));
                forummoderator.setUser(existingUser);
            } else {
                // Nouvel objet → sauvegarde d'abord
                UserProfile newUser = userRepository.save(forummoderator.getUser());
                forummoderator.setUser(newUser);
            }

            forummoderator.getUser().setModerator(forummoderator);
        }
        
    return forummoderatorRepository.save(forummoderator);
}

    @Transactional
    @Override
    public ForumModerator update(Long id, ForumModerator forummoderatorRequest) {
        ForumModerator existing = forummoderatorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ForumModerator not found"));

    // Copier les champs simples
        existing.setModeratorSince(forummoderatorRequest.getModeratorSince());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
        if (forummoderatorRequest.getModeratedCategories() != null) {
            existing.getModeratedCategories().clear();

            List<ForumCategory> moderatedCategoriesList = forummoderatorRequest.getModeratedCategories().stream()
                .map(item -> moderatedCategoriesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("ForumCategory not found")))
                .collect(Collectors.toList());

            existing.getModeratedCategories().addAll(moderatedCategoriesList);

            // Mettre à jour le côté inverse
            moderatedCategoriesList.forEach(it -> {
                if (!it.getModerators().contains(existing)) {
                    it.getModerators().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (forummoderatorRequest.getUser() != null &&forummoderatorRequest.getUser().getId() != null) {

        UserProfile user = userRepository.findById(forummoderatorRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setUser(user);
        user.setModerator(existing);
        }
    
    return forummoderatorRepository.save(existing);
}

    // Pagination simple
    public Page<ForumModerator> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ForumModerator> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ForumModerator.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ForumModerator> saveAll(List<ForumModerator> forummoderatorList) {
        return super.saveAll(forummoderatorList);
    }

}