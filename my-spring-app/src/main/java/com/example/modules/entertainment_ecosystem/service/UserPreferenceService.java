package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserPreference;
import com.example.modules.entertainment_ecosystem.repository.UserPreferenceRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserPreferenceService extends BaseService<UserPreference> {

    protected final UserPreferenceRepository userpreferenceRepository;
    private final UserProfileRepository userRepository;

    public UserPreferenceService(UserPreferenceRepository repository, UserProfileRepository userRepository)
    {
        super(repository);
        this.userpreferenceRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public UserPreference save(UserPreference userpreference) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (userpreference.getUser() != null) {
            if (userpreference.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    userpreference.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + userpreference.getUser().getId()));
                userpreference.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(userpreference.getUser());
                userpreference.setUser(newUser);
            }
        }
        
    // ---------- OneToOne ----------
    return userpreferenceRepository.save(userpreference);
}


    public UserPreference update(Long id, UserPreference userpreferenceRequest) {
        UserPreference existing = userpreferenceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserPreference not found"));

    // Copier les champs simples
        existing.setPreferenceName(userpreferenceRequest.getPreferenceName());
        existing.setPreferenceValue(userpreferenceRequest.getPreferenceValue());

    // ---------- Relations ManyToOne ----------
        if (userpreferenceRequest.getUser() != null &&
            userpreferenceRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                userpreferenceRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return userpreferenceRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<UserPreference> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        UserPreference entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getUser() != null) {
            entity.setUser(null);
        }
        
        repository.delete(entity);
        return true;
    }
}