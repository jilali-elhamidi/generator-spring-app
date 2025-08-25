package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Achievement;
import com.example.modules.entertainment_ecosystem.repository.AchievementRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class AchievementService extends BaseService<Achievement> {

    protected final AchievementRepository achievementRepository;
    private final UserProfileRepository userRepository;

    public AchievementService(AchievementRepository repository, UserProfileRepository userRepository)
    {
        super(repository);
        this.achievementRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Achievement save(Achievement achievement) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (achievement.getUser() != null) {
            if (achievement.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    achievement.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + achievement.getUser().getId()));
                achievement.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(achievement.getUser());
                achievement.setUser(newUser);
            }
        }
        
    // ---------- OneToOne ----------
    return achievementRepository.save(achievement);
}


    public Achievement update(Long id, Achievement achievementRequest) {
        Achievement existing = achievementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Achievement not found"));

    // Copier les champs simples
        existing.setName(achievementRequest.getName());
        existing.setDescription(achievementRequest.getDescription());
        existing.setAchievementDate(achievementRequest.getAchievementDate());

    // ---------- Relations ManyToOne ----------
        if (achievementRequest.getUser() != null &&
            achievementRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                achievementRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return achievementRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Achievement> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Achievement entity = entityOpt.get();
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
    @Transactional
    public List<Achievement> saveAll(List<Achievement> achievementList) {

        return achievementRepository.saveAll(achievementList);
    }

}