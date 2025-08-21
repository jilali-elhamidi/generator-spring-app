package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.GameExpansionPack;
import com.example.modules.entertainment_ecosystem.repository.GameExpansionPackRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.repository.DigitalPurchaseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class GameExpansionPackService extends BaseService<GameExpansionPack> {

    protected final GameExpansionPackRepository gameexpansionpackRepository;
    private final VideoGameRepository baseGameRepository;
    private final DigitalPurchaseRepository purchasesRepository;

    public GameExpansionPackService(GameExpansionPackRepository repository, VideoGameRepository baseGameRepository, DigitalPurchaseRepository purchasesRepository)
    {
        super(repository);
        this.gameexpansionpackRepository = repository;
        this.baseGameRepository = baseGameRepository;
        this.purchasesRepository = purchasesRepository;
    }

    @Override
    public GameExpansionPack save(GameExpansionPack gameexpansionpack) {
    // ---------- OneToMany ----------
        if (gameexpansionpack.getPurchases() != null) {
            List<DigitalPurchase> managedPurchases = new ArrayList<>();
            for (DigitalPurchase item : gameexpansionpack.getPurchases()) {
                if (item.getId() != null) {
                    DigitalPurchase existingItem = purchasesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));

                     existingItem.setExpansionPack(gameexpansionpack);
                     managedPurchases.add(existingItem);
                } else {
                    item.setExpansionPack(gameexpansionpack);
                    managedPurchases.add(item);
                }
            }
            gameexpansionpack.setPurchases(managedPurchases);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (gameexpansionpack.getBaseGame() != null) {
            if (gameexpansionpack.getBaseGame().getId() != null) {
                VideoGame existingBaseGame = baseGameRepository.findById(
                    gameexpansionpack.getBaseGame().getId()
                ).orElseThrow(() -> new RuntimeException("VideoGame not found with id "
                    + gameexpansionpack.getBaseGame().getId()));
                gameexpansionpack.setBaseGame(existingBaseGame);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                VideoGame newBaseGame = baseGameRepository.save(gameexpansionpack.getBaseGame());
                gameexpansionpack.setBaseGame(newBaseGame);
            }
        }
        
    // ---------- OneToOne ----------
    return gameexpansionpackRepository.save(gameexpansionpack);
}


    public GameExpansionPack update(Long id, GameExpansionPack gameexpansionpackRequest) {
        GameExpansionPack existing = gameexpansionpackRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("GameExpansionPack not found"));

    // Copier les champs simples
        existing.setName(gameexpansionpackRequest.getName());
        existing.setReleaseDate(gameexpansionpackRequest.getReleaseDate());
        existing.setPrice(gameexpansionpackRequest.getPrice());

    // ---------- Relations ManyToOne ----------
        if (gameexpansionpackRequest.getBaseGame() != null &&
            gameexpansionpackRequest.getBaseGame().getId() != null) {

            VideoGame existingBaseGame = baseGameRepository.findById(
                gameexpansionpackRequest.getBaseGame().getId()
            ).orElseThrow(() -> new RuntimeException("VideoGame not found"));

            existing.setBaseGame(existingBaseGame);
        } else {
            existing.setBaseGame(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getPurchases().clear();

        if (gameexpansionpackRequest.getPurchases() != null) {
            for (var item : gameexpansionpackRequest.getPurchases()) {
                DigitalPurchase existingItem;
                if (item.getId() != null) {
                    existingItem = purchasesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));
                } else {
                existingItem = item;
                }

                existingItem.setExpansionPack(existing);
                existing.getPurchases().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return gameexpansionpackRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<GameExpansionPack> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        GameExpansionPack entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getPurchases() != null) {
            for (var child : entity.getPurchases()) {
                // retirer la référence inverse
                child.setExpansionPack(null);
            }
            entity.getPurchases().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getBaseGame() != null) {
            entity.setBaseGame(null);
        }
        
        repository.delete(entity);
        return true;
    }
}