package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserPreference;
import com.example.modules.entertainment_ecosystem.repository.UserPreferenceRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class UserPreferenceService extends BaseService<UserPreference> {

    protected final UserPreferenceRepository userpreferenceRepository;
    private final UserProfileRepository userRepository;

    public UserPreferenceService(UserPreferenceRepository repository,UserProfileRepository userRepository)
    {
        super(repository);
        this.userpreferenceRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public UserPreference save(UserPreference userpreference) {

        if (userpreference.getUser() != null && userpreference.getUser().getId() != null) {
        UserProfile user = userRepository.findById(userpreference.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userpreference.setUser(user);
        }

        return userpreferenceRepository.save(userpreference);
    }


    public UserPreference update(Long id, UserPreference userpreferenceRequest) {
        UserPreference existing = userpreferenceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserPreference not found"));

    // Copier les champs simples
        existing.setPreferenceName(userpreferenceRequest.getPreferenceName());
        existing.setPreferenceValue(userpreferenceRequest.getPreferenceValue());

// Relations ManyToOne : mise à jour conditionnelle

        if (userpreferenceRequest.getUser() != null && userpreferenceRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(userpreferenceRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


        return userpreferenceRepository.save(existing);
    }
}