package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GamePlatformType;
import com.example.modules.entertainment_ecosystem.repository.GamePlatformTypeRepository;

import com.example.modules.entertainment_ecosystem.model.GamePlatform;
import com.example.modules.entertainment_ecosystem.repository.GamePlatformRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class GamePlatformTypeService extends BaseService<GamePlatformType> {

    protected final GamePlatformTypeRepository gameplatformtypeRepository;
    
    protected final GamePlatformRepository gamePlatformsRepository;
    

    public GamePlatformTypeService(GamePlatformTypeRepository repository, GamePlatformRepository gamePlatformsRepository)
    {
        super(repository);
        this.gameplatformtypeRepository = repository;
        
        this.gamePlatformsRepository = gamePlatformsRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public GamePlatformType update(Long id, GamePlatformType gameplatformtypeRequest) {
        GamePlatformType existing = gameplatformtypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GamePlatformType not found"));

    // Copier les champs simples
        existing.setName(gameplatformtypeRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<GamePlatformType> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<GamePlatformType> search(Map<String, String> filters, Pageable pageable) {
        return super.search(GamePlatformType.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<GamePlatformType> saveAll(List<GamePlatformType> gameplatformtypeList) {
        return super.saveAll(gameplatformtypeList);
    }

}