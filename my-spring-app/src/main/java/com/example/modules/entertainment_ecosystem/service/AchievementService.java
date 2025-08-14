package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Achievement;
import com.example.modules.entertainment_ecosystem.repository.AchievementRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class AchievementService extends BaseService<Achievement> {

    protected final AchievementRepository achievementRepository;
    private final UserProfileRepository userRepository;

    public AchievementService(AchievementRepository repository,UserProfileRepository userRepository)
    {
        super(repository);
        this.achievementRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Achievement save(Achievement achievement) {


    

    if (achievement.getUser() != null
        && achievement.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        achievement.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        achievement.setUser(existingUser);
        }
    

        return achievementRepository.save(achievement);
    }


    public Achievement update(Long id, Achievement achievementRequest) {
        Achievement existing = achievementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Achievement not found"));

    // Copier les champs simples
        existing.setName(achievementRequest.getName());
        existing.setDescription(achievementRequest.getDescription());
        existing.setAchievementDate(achievementRequest.getAchievementDate());

// Relations ManyToOne : mise à jour conditionnelle
        if (achievementRequest.getUser() != null &&
        achievementRequest.getUser().getId() != null) {

        UserProfile existingUser = userRepository.findById(
        achievementRequest.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setUser(existingUser);
        } else {
        existing.setUser(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


        return achievementRepository.save(existing);
    }


}