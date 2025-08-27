package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Achievement;
import com.example.modules.entertainment_ecosystem.repository.AchievementRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class AchievementService extends BaseService<Achievement> {

    protected final AchievementRepository achievementRepository;
    
    protected final UserProfileRepository userRepository;
    

    public AchievementService(AchievementRepository repository, UserProfileRepository userRepository)
    {
        super(repository);
        this.achievementRepository = repository;
        
        this.userRepository = userRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return achievementRepository.save(existing);
}

    // Pagination simple
    public Page<Achievement> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Achievement> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Achievement.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Achievement> saveAll(List<Achievement> achievementList) {
        return super.saveAll(achievementList);
    }

}