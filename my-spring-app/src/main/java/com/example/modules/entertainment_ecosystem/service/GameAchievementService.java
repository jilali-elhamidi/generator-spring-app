package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameAchievement;
import com.example.modules.entertainment_ecosystem.repository.GameAchievementRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.UserAchievement;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class GameAchievementService extends BaseService<GameAchievement> {

    protected final GameAchievementRepository gameachievementRepository;
    private final VideoGameRepository gameRepository;
    private final UserProfileRepository earnedByRepository;

    public GameAchievementService(GameAchievementRepository repository,VideoGameRepository gameRepository,UserProfileRepository earnedByRepository)
    {
        super(repository);
        this.gameachievementRepository = repository;
        this.gameRepository = gameRepository;
        this.earnedByRepository = earnedByRepository;
    }

    @Override
    public GameAchievement save(GameAchievement gameachievement) {

        if (gameachievement.getGame() != null && gameachievement.getGame().getId() != null) {
        VideoGame game = gameRepository.findById(gameachievement.getGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        gameachievement.setGame(game);
        }

        if (gameachievement.getUserAchievements() != null) {
            for (UserAchievement item : gameachievement.getUserAchievements()) {
            item.setAchievement(gameachievement);
            }
        }

        return gameachievementRepository.save(gameachievement);
    }


    public GameAchievement update(Long id, GameAchievement gameachievementRequest) {
        GameAchievement existing = gameachievementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameAchievement not found"));

    // Copier les champs simples
        existing.setName(gameachievementRequest.getName());
        existing.setDescription(gameachievementRequest.getDescription());
        existing.setAchievementDate(gameachievementRequest.getAchievementDate());

// Relations ManyToOne : mise à jour conditionnelle

        if (gameachievementRequest.getGame() != null && gameachievementRequest.getGame().getId() != null) {
        VideoGame game = gameRepository.findById(gameachievementRequest.getGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        existing.setGame(game);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (gameachievementRequest.getEarnedBy() != null) {
            existing.getEarnedBy().clear();
            List<UserProfile> earnedByList = gameachievementRequest.getEarnedBy().stream()
                .map(item -> earnedByRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getEarnedBy().addAll(earnedByList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getUserAchievements().clear();
        if (gameachievementRequest.getUserAchievements() != null) {
            for (var item : gameachievementRequest.getUserAchievements()) {
            item.setAchievement(existing);
            existing.getUserAchievements().add(item);
            }
        }

    

    

    


        return gameachievementRepository.save(existing);
    }
}