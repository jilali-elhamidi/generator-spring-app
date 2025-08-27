package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserAchievement;
import com.example.modules.entertainment_ecosystem.repository.UserAchievementRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import com.example.modules.entertainment_ecosystem.model.GameAchievement;
import com.example.modules.entertainment_ecosystem.repository.GameAchievementRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class UserAchievementService extends BaseService<UserAchievement> {

    protected final UserAchievementRepository userachievementRepository;
    
    protected final UserProfileRepository userRepository;
    
    protected final GameAchievementRepository achievementRepository;
    

    public UserAchievementService(UserAchievementRepository repository, UserProfileRepository userRepository, GameAchievementRepository achievementRepository)
    {
        super(repository);
        this.userachievementRepository = repository;
        
        this.userRepository = userRepository;
        
        this.achievementRepository = achievementRepository;
        
    }

    @Transactional
    @Override
    public UserAchievement save(UserAchievement userachievement) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (userachievement.getUser() != null) {
            if (userachievement.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    userachievement.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + userachievement.getUser().getId()));
                userachievement.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(userachievement.getUser());
                userachievement.setUser(newUser);
            }
        }
        
        if (userachievement.getAchievement() != null) {
            if (userachievement.getAchievement().getId() != null) {
                GameAchievement existingAchievement = achievementRepository.findById(
                    userachievement.getAchievement().getId()
                ).orElseThrow(() -> new RuntimeException("GameAchievement not found with id "
                    + userachievement.getAchievement().getId()));
                userachievement.setAchievement(existingAchievement);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                GameAchievement newAchievement = achievementRepository.save(userachievement.getAchievement());
                userachievement.setAchievement(newAchievement);
            }
        }
        
    // ---------- OneToOne ----------
    return userachievementRepository.save(userachievement);
}

    @Transactional
    @Override
    public UserAchievement update(Long id, UserAchievement userachievementRequest) {
        UserAchievement existing = userachievementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserAchievement not found"));

    // Copier les champs simples
        existing.setCompletionDate(userachievementRequest.getCompletionDate());

    // ---------- Relations ManyToOne ----------
        if (userachievementRequest.getUser() != null &&
            userachievementRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                userachievementRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (userachievementRequest.getAchievement() != null &&
            userachievementRequest.getAchievement().getId() != null) {

            GameAchievement existingAchievement = achievementRepository.findById(
                userachievementRequest.getAchievement().getId()
            ).orElseThrow(() -> new RuntimeException("GameAchievement not found"));

            existing.setAchievement(existingAchievement);
        } else {
            existing.setAchievement(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return userachievementRepository.save(existing);
}

    // Pagination simple
    public Page<UserAchievement> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<UserAchievement> search(Map<String, String> filters, Pageable pageable) {
        return super.search(UserAchievement.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<UserAchievement> saveAll(List<UserAchievement> userachievementList) {
        return super.saveAll(userachievementList);
    }

}