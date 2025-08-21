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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ForumModeratorService extends BaseService<ForumModerator> {

    protected final ForumModeratorRepository forummoderatorRepository;
    private final UserProfileRepository userRepository;
    private final ForumCategoryRepository moderatedCategoriesRepository;

    public ForumModeratorService(ForumModeratorRepository repository, UserProfileRepository userRepository, ForumCategoryRepository moderatedCategoriesRepository)
    {
        super(repository);
        this.forummoderatorRepository = repository;
        this.userRepository = userRepository;
        this.moderatedCategoriesRepository = moderatedCategoriesRepository;
    }

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


    public ForumModerator update(Long id, ForumModerator forummoderatorRequest) {
        ForumModerator existing = forummoderatorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ForumModerator not found"));

    // Copier les champs simples
        existing.setModeratorSince(forummoderatorRequest.getModeratorSince());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<ForumModerator> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        ForumModerator entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
        if (entity.getModeratedCategories() != null) {
            for (ForumCategory item : new ArrayList<>(entity.getModeratedCategories())) {
                
                item.getModerators().remove(entity); // retire côté inverse
            }
            entity.getModeratedCategories().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
        if (entity.getUser() != null) {
            entity.getUser().setModerator(null);
            entity.setUser(null);
        }
        
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}