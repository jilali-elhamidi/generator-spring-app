package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GamePlatformType;
import com.example.modules.entertainment_ecosystem.repository.GamePlatformTypeRepository;
import com.example.modules.entertainment_ecosystem.model.GamePlatform;
import com.example.modules.entertainment_ecosystem.repository.GamePlatformRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class GamePlatformTypeService extends BaseService<GamePlatformType> {

    protected final GamePlatformTypeRepository gameplatformtypeRepository;
    private final GamePlatformRepository gamePlatformsRepository;

    public GamePlatformTypeService(GamePlatformTypeRepository repository, GamePlatformRepository gamePlatformsRepository)
    {
        super(repository);
        this.gameplatformtypeRepository = repository;
        this.gamePlatformsRepository = gamePlatformsRepository;
    }

    @Override
    public GamePlatformType save(GamePlatformType gameplatformtype) {
    // ---------- OneToMany ----------
        if (gameplatformtype.getGamePlatforms() != null) {
            List<GamePlatform> managedGamePlatforms = new ArrayList<>();
            for (GamePlatform item : gameplatformtype.getGamePlatforms()) {
                if (item.getId() != null) {
                    GamePlatform existingItem = gamePlatformsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GamePlatform not found"));

                     existingItem.setType(gameplatformtype);
                     managedGamePlatforms.add(existingItem);
                } else {
                    item.setType(gameplatformtype);
                    managedGamePlatforms.add(item);
                }
            }
            gameplatformtype.setGamePlatforms(managedGamePlatforms);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return gameplatformtypeRepository.save(gameplatformtype);
}


    public GamePlatformType update(Long id, GamePlatformType gameplatformtypeRequest) {
        GamePlatformType existing = gameplatformtypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GamePlatformType not found"));

    // Copier les champs simples
        existing.setName(gameplatformtypeRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getGamePlatforms().clear();

        if (gameplatformtypeRequest.getGamePlatforms() != null) {
            for (var item : gameplatformtypeRequest.getGamePlatforms()) {
                GamePlatform existingItem;
                if (item.getId() != null) {
                    existingItem = gamePlatformsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("GamePlatform not found"));
                } else {
                existingItem = item;
                }

                existingItem.setType(existing);
                existing.getGamePlatforms().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return gameplatformtypeRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<GamePlatformType> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        GamePlatformType entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getGamePlatforms() != null) {
            for (var child : entity.getGamePlatforms()) {
                // retirer la référence inverse
                child.setType(null);
            }
            entity.getGamePlatforms().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}