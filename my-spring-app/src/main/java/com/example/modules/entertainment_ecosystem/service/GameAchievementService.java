package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameAchievement;
import com.example.modules.entertainment_ecosystem.repository.GameAchievementRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
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
    private final UserProfileRepository earnedByRepository;
    private final UserAchievementRepository userAchievementsRepository;

    public GameAchievementService(GameAchievementRepository repository,VideoGameRepository gameRepository,UserProfileRepository earnedByRepository,UserAchievementRepository userAchievementsRepository)
    {
        super(repository);
        this.gameachievementRepository = repository;
        this.gameRepository = gameRepository;
        this.earnedByRepository = earnedByRepository;
        this.userAchievementsRepository = userAchievementsRepository;
    }

    @Override
    public GameAchievement save(GameAchievement gameachievement) {


    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (gameachievement.getUserAchievements() != null) {
            List<UserAchievement> managedUserAchievements = new ArrayList<>();
            for (UserAchievement item : gameachievement.getUserAchievements()) {
            if (item.getId() != null) {
            UserAchievement existingItem = userAchievementsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserAchievement not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setAchievement(gameachievement);
            managedUserAchievements.add(existingItem);
            } else {
            item.setAchievement(gameachievement);
            managedUserAchievements.add(item);
            }
            }
            gameachievement.setUserAchievements(managedUserAchievements);
            }
        
    

    if (gameachievement.getGame() != null
        && gameachievement.getGame().getId() != null) {
        VideoGame existingGame = gameRepository.findById(
        gameachievement.getGame().getId()
        ).orElseThrow(() -> new RuntimeException("VideoGame not found"));
        gameachievement.setGame(existingGame);
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
        if (gameachievementRequest.getGame() != null &&
        gameachievementRequest.getGame().getId() != null) {

        VideoGame existingGame = gameRepository.findById(
        gameachievementRequest.getGame().getId()
        ).orElseThrow(() -> new RuntimeException("VideoGame not found"));

        existing.setGame(existingGame);
        } else {
        existing.setGame(null);
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
        // Vider la collection existante
        existing.getUserAchievements().clear();

        if (gameachievementRequest.getUserAchievements() != null) {
        for (var item : gameachievementRequest.getUserAchievements()) {
        UserAchievement existingItem;
        if (item.getId() != null) {
        existingItem = userAchievementsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserAchievement not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setAchievement(existing);

        // Ajouter directement dans la collection existante
        existing.getUserAchievements().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    


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
        
            child.setAchievement(null); // retirer la référence inverse
        
        }
        entity.getUserAchievements().clear();
        }
    


// --- Dissocier ManyToMany ---

    

    
        if (entity.getEarnedBy() != null) {
        for (UserProfile item : new ArrayList<>(entity.getEarnedBy())) {
        
        }
        entity.getEarnedBy().clear(); // puis vide côté courant
        }
    

    



// --- Dissocier OneToOne ---

    

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getGame() != null) {
        entity.setGame(null);
        }
    

    

    


repository.delete(entity);
return true;
}
}