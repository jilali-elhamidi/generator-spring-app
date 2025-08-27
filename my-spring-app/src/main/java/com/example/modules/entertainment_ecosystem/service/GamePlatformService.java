package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GamePlatform;
import com.example.modules.entertainment_ecosystem.repository.GamePlatformRepository;

import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;

import com.example.modules.entertainment_ecosystem.model.GamePlatformType;
import com.example.modules.entertainment_ecosystem.repository.GamePlatformTypeRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class GamePlatformService extends BaseService<GamePlatform> {

    protected final GamePlatformRepository gameplatformRepository;
    
    protected final VideoGameRepository videoGamesRepository;
    
    protected final GamePlatformTypeRepository typeRepository;
    

    public GamePlatformService(GamePlatformRepository repository, VideoGameRepository videoGamesRepository, GamePlatformTypeRepository typeRepository)
    {
        super(repository);
        this.gameplatformRepository = repository;
        
        this.videoGamesRepository = videoGamesRepository;
        
        this.typeRepository = typeRepository;
        
    }

    @Transactional
    @Override
    public GamePlatform save(GamePlatform gameplatform) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (gameplatform.getVideoGames() != null &&
            !gameplatform.getVideoGames().isEmpty()) {

            List<VideoGame> attachedVideoGames = new ArrayList<>();
            for (VideoGame item : gameplatform.getVideoGames()) {
                if (item.getId() != null) {
                    VideoGame existingItem = videoGamesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("VideoGame not found with id " + item.getId()));
                    attachedVideoGames.add(existingItem);
                } else {

                    VideoGame newItem = videoGamesRepository.save(item);
                    attachedVideoGames.add(newItem);
                }
            }

            gameplatform.setVideoGames(attachedVideoGames);

            // côté propriétaire (VideoGame → GamePlatform)
            attachedVideoGames.forEach(it -> it.getPlatforms().add(gameplatform));
        }
        
    // ---------- ManyToOne ----------
        if (gameplatform.getType() != null) {
            if (gameplatform.getType().getId() != null) {
                GamePlatformType existingType = typeRepository.findById(
                    gameplatform.getType().getId()
                ).orElseThrow(() -> new RuntimeException("GamePlatformType not found with id "
                    + gameplatform.getType().getId()));
                gameplatform.setType(existingType);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                GamePlatformType newType = typeRepository.save(gameplatform.getType());
                gameplatform.setType(newType);
            }
        }
        
    // ---------- OneToOne ----------
    return gameplatformRepository.save(gameplatform);
}

    @Transactional
    @Override
    public GamePlatform update(Long id, GamePlatform gameplatformRequest) {
        GamePlatform existing = gameplatformRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GamePlatform not found"));

    // Copier les champs simples
        existing.setName(gameplatformRequest.getName());

    // ---------- Relations ManyToOne ----------
        if (gameplatformRequest.getType() != null &&
            gameplatformRequest.getType().getId() != null) {

            GamePlatformType existingType = typeRepository.findById(
                gameplatformRequest.getType().getId()
            ).orElseThrow(() -> new RuntimeException("GamePlatformType not found"));

            existing.setType(existingType);
        } else {
            existing.setType(null);
        }
        
    // ---------- Relations ManyToMany ----------
        if (gameplatformRequest.getVideoGames() != null) {
            existing.getVideoGames().clear();

            List<VideoGame> videoGamesList = gameplatformRequest.getVideoGames().stream()
                .map(item -> videoGamesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("VideoGame not found")))
                .collect(Collectors.toList());

            existing.getVideoGames().addAll(videoGamesList);

            // Mettre à jour le côté inverse
            videoGamesList.forEach(it -> {
                if (!it.getPlatforms().contains(existing)) {
                    it.getPlatforms().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return gameplatformRepository.save(existing);
}

    // Pagination simple
    public Page<GamePlatform> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<GamePlatform> search(Map<String, String> filters, Pageable pageable) {
        return super.search(GamePlatform.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<GamePlatform> saveAll(List<GamePlatform> gameplatformList) {
        return super.saveAll(gameplatformList);
    }

}