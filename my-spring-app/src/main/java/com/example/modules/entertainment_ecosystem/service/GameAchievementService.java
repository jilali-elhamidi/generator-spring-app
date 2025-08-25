package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameAchievement;
import com.example.modules.entertainment_ecosystem.repository.GameAchievementRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.UserAchievement;
import com.example.modules.entertainment_ecosystem.repository.UserAchievementRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class GameAchievementService extends BaseService<GameAchievement> {

    protected final GameAchievementRepository gameachievementRepository;
    private final VideoGameRepository gameRepository;
    private final UserAchievementRepository userAchievementsRepository;

    public GameAchievementService(GameAchievementRepository repository, VideoGameRepository gameRepository, UserAchievementRepository userAchievementsRepository)
    {
        super(repository);
        this.gameachievementRepository = repository;
        this.gameRepository = gameRepository;
        this.userAchievementsRepository = userAchievementsRepository;
    }

    @Override
    public GameAchievement save(GameAchievement gameachievement) {
    // ---------- OneToMany ----------
        if (gameachievement.getUserAchievements() != null) {
            List<UserAchievement> managedUserAchievements = new ArrayList<>();
            for (UserAchievement item : gameachievement.getUserAchievements()) {
                if (item.getId() != null) {
                    UserAchievement existingItem = userAchievementsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserAchievement not found"));

                     existingItem.setAchievement(gameachievement);
                     managedUserAchievements.add(existingItem);
                } else {
                    item.setAchievement(gameachievement);
                    managedUserAchievements.add(item);
                }
            }
            gameachievement.setUserAchievements(managedUserAchievements);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (gameachievement.getGame() != null) {
            if (gameachievement.getGame().getId() != null) {
                VideoGame existingGame = gameRepository.findById(
                    gameachievement.getGame().getId()
                ).orElseThrow(() -> new RuntimeException("VideoGame not found with id "
                    + gameachievement.getGame().getId()));
                gameachievement.setGame(existingGame);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                VideoGame newGame = gameRepository.save(gameachievement.getGame());
                gameachievement.setGame(newGame);
            }
        }
        
    // ---------- OneToOne ----------
    return gameachievementRepository.save(gameachievement);
}


    public GameAchievement update(Long id, GameAchievement gameachievementRequest) {
        GameAchievement existing = gameachievementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameAchievement not found"));

    // Copier les champs simples
        existing.setName(gameachievementRequest.getName());
        existing.setDescription(gameachievementRequest.getDescription());
        existing.setAchievementDate(gameachievementRequest.getAchievementDate());

    // ---------- Relations ManyToOne ----------
        if (gameachievementRequest.getGame() != null &&
            gameachievementRequest.getGame().getId() != null) {

            VideoGame existingGame = gameRepository.findById(
                gameachievementRequest.getGame().getId()
            ).orElseThrow(() -> new RuntimeException("VideoGame not found"));

            existing.setGame(existingGame);
        } else {
            existing.setGame(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getUserAchievements().clear();

        if (gameachievementRequest.getUserAchievements() != null) {
            for (var item : gameachievementRequest.getUserAchievements()) {
                UserAchievement existingItem;
                if (item.getId() != null) {
                    existingItem = userAchievementsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserAchievement not found"));
                } else {
                existingItem = item;
                }

                existingItem.setAchievement(existing);
                existing.getUserAchievements().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return gameachievementRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<GameAchievement> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        GameAchievement entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getUserAchievements() != null) {
            for (var child : entity.getUserAchievements()) {
                // retirer la référence inverse
                child.setAchievement(null);
            }
            entity.getUserAchievements().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getGame() != null) {
            entity.setGame(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<GameAchievement> saveAll(List<GameAchievement> gameachievementList) {

        return gameachievementRepository.saveAll(gameachievementList);
    }

}