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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserAchievementService extends BaseService<UserAchievement> {

    protected final UserAchievementRepository userachievementRepository;
    private final UserProfileRepository userRepository;
    private final GameAchievementRepository achievementRepository;

    public UserAchievementService(UserAchievementRepository repository,UserProfileRepository userRepository,GameAchievementRepository achievementRepository)
    {
        super(repository);
        this.userachievementRepository = repository;
        this.userRepository = userRepository;
        this.achievementRepository = achievementRepository;
    }

    @Override
    public UserAchievement save(UserAchievement userachievement) {


    

    


    

    

    if (userachievement.getUser() != null
        && userachievement.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        userachievement.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userachievement.setUser(existingUser);
        }
    
    if (userachievement.getAchievement() != null
        && userachievement.getAchievement().getId() != null) {
        GameAchievement existingAchievement = achievementRepository.findById(
        userachievement.getAchievement().getId()
        ).orElseThrow(() -> new RuntimeException("GameAchievement not found"));
        userachievement.setAchievement(existingAchievement);
        }
    

        return userachievementRepository.save(userachievement);
    }


    public UserAchievement update(Long id, UserAchievement userachievementRequest) {
        UserAchievement existing = userachievementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserAchievement not found"));

    // Copier les champs simples
        existing.setCompletionDate(userachievementRequest.getCompletionDate());

// Relations ManyToOne : mise à jour conditionnelle
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

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return userachievementRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<UserAchievement> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

UserAchievement entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    


// --- Dissocier ManyToMany ---

    

    



// --- Dissocier OneToOne ---

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getUser() != null) {
        entity.setUser(null);
        }
    

    
        if (entity.getAchievement() != null) {
        entity.setAchievement(null);
        }
    


repository.delete(entity);
return true;
}
}