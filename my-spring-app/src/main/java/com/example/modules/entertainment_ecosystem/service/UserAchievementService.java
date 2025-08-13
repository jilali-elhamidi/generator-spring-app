package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserAchievement;
import com.example.modules.entertainment_ecosystem.repository.UserAchievementRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.GameAchievement;
import com.example.modules.entertainment_ecosystem.repository.GameAchievementRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

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

        if (userachievement.getUser() != null && userachievement.getUser().getId() != null) {
        UserProfile user = userRepository.findById(userachievement.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        userachievement.setUser(user);
        }

        if (userachievement.getAchievement() != null && userachievement.getAchievement().getId() != null) {
        GameAchievement achievement = achievementRepository.findById(userachievement.getAchievement().getId())
                .orElseThrow(() -> new RuntimeException("GameAchievement not found"));
        userachievement.setAchievement(achievement);
        }

        return userachievementRepository.save(userachievement);
    }


    public UserAchievement update(Long id, UserAchievement userachievementRequest) {
        UserAchievement existing = userachievementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserAchievement not found"));

    // Copier les champs simples
        existing.setCompletionDate(userachievementRequest.getCompletionDate());

// Relations ManyToOne : mise à jour conditionnelle

        if (userachievementRequest.getUser() != null && userachievementRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(userachievementRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

        if (userachievementRequest.getAchievement() != null && userachievementRequest.getAchievement().getId() != null) {
        GameAchievement achievement = achievementRepository.findById(userachievementRequest.getAchievement().getId())
                .orElseThrow(() -> new RuntimeException("GameAchievement not found"));
        existing.setAchievement(achievement);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return userachievementRepository.save(existing);
    }
}