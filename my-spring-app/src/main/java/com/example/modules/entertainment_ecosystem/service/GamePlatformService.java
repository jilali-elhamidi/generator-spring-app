package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GamePlatform;
import com.example.modules.entertainment_ecosystem.repository.GamePlatformRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class GamePlatformService extends BaseService<GamePlatform> {

    protected final GamePlatformRepository gameplatformRepository;
    private final VideoGameRepository videoGamesRepository;

    public GamePlatformService(GamePlatformRepository repository,VideoGameRepository videoGamesRepository)
    {
        super(repository);
        this.gameplatformRepository = repository;
        this.videoGamesRepository = videoGamesRepository;
    }

    @Override
    public GamePlatform save(GamePlatform gameplatform) {


    

    

    
        if (gameplatform.getVideoGames() != null) {
        List<VideoGame> managedVideoGames = new ArrayList<>();
        for (VideoGame item : gameplatform.getVideoGames()) {
        if (item.getId() != null) {
        VideoGame existingItem = videoGamesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        managedVideoGames.add(existingItem);
        } else {
        managedVideoGames.add(item);
        }
        }
        gameplatform.setVideoGames(managedVideoGames);
        }
    


        return gameplatformRepository.save(gameplatform);
    }


    public GamePlatform update(Long id, GamePlatform gameplatformRequest) {
        GamePlatform existing = gameplatformRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GamePlatform not found"));

    // Copier les champs simples
        existing.setName(gameplatformRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (gameplatformRequest.getVideoGames() != null) {
            existing.getVideoGames().clear();
            List<VideoGame> videoGamesList = gameplatformRequest.getVideoGames().stream()
                .map(item -> videoGamesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("VideoGame not found")))
                .collect(Collectors.toList());
        existing.getVideoGames().addAll(videoGamesList);
        }

// Relations OneToMany : synchronisation sécurisée

    


        return gameplatformRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<GamePlatform> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

GamePlatform entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    
        if (entity.getVideoGames() != null) {
        entity.getVideoGames().clear();
        }
    


// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}