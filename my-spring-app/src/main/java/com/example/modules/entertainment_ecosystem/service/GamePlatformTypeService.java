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

    public GamePlatformTypeService(GamePlatformTypeRepository repository,GamePlatformRepository gamePlatformsRepository)
    {
        super(repository);
        this.gameplatformtypeRepository = repository;
        this.gamePlatformsRepository = gamePlatformsRepository;
    }

    @Override
    public GamePlatformType save(GamePlatformType gameplatformtype) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (gameplatformtype.getGamePlatforms() != null) {
            List<GamePlatform> managedGamePlatforms = new ArrayList<>();
            for (GamePlatform item : gameplatformtype.getGamePlatforms()) {
            if (item.getId() != null) {
            GamePlatform existingItem = gamePlatformsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("GamePlatform not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setType(gameplatformtype);
            managedGamePlatforms.add(existingItem);
            } else {
            item.setType(gameplatformtype);
            managedGamePlatforms.add(item);
            }
            }
            gameplatformtype.setGamePlatforms(managedGamePlatforms);
            }
        
    


    

    

        return gameplatformtypeRepository.save(gameplatformtype);
    }


    public GamePlatformType update(Long id, GamePlatformType gameplatformtypeRequest) {
        GamePlatformType existing = gameplatformtypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GamePlatformType not found"));

    // Copier les champs simples
        existing.setName(gameplatformtypeRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getGamePlatforms().clear();

        if (gameplatformtypeRequest.getGamePlatforms() != null) {
        for (var item : gameplatformtypeRequest.getGamePlatforms()) {
        GamePlatform existingItem;
        if (item.getId() != null) {
        existingItem = gamePlatformsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("GamePlatform not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setType(existing);

        // Ajouter directement dans la collection existante
        existing.getGamePlatforms().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


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
        
            child.setType(null); // retirer la référence inverse
        
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