package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.DevelopmentStudio;
import com.example.modules.entertainment_ecosystem.repository.DevelopmentStudioRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class DevelopmentStudioService extends BaseService<DevelopmentStudio> {

    protected final DevelopmentStudioRepository developmentstudioRepository;
    private final VideoGameRepository gamesRepository;

    public DevelopmentStudioService(DevelopmentStudioRepository repository, VideoGameRepository gamesRepository)
    {
        super(repository);
        this.developmentstudioRepository = repository;
        this.gamesRepository = gamesRepository;
    }

    @Override
    public DevelopmentStudio save(DevelopmentStudio developmentstudio) {
    // ---------- OneToMany ----------
        if (developmentstudio.getGames() != null) {
            List<VideoGame> managedGames = new ArrayList<>();
            for (VideoGame item : developmentstudio.getGames()) {
                if (item.getId() != null) {
                    VideoGame existingItem = gamesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("VideoGame not found"));

                     existingItem.setDeveloperStudio(developmentstudio);
                     managedGames.add(existingItem);
                } else {
                    item.setDeveloperStudio(developmentstudio);
                    managedGames.add(item);
                }
            }
            developmentstudio.setGames(managedGames);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return developmentstudioRepository.save(developmentstudio);
}


    public DevelopmentStudio update(Long id, DevelopmentStudio developmentstudioRequest) {
        DevelopmentStudio existing = developmentstudioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("DevelopmentStudio not found"));

    // Copier les champs simples
        existing.setName(developmentstudioRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getGames().clear();

        if (developmentstudioRequest.getGames() != null) {
            for (var item : developmentstudioRequest.getGames()) {
                VideoGame existingItem;
                if (item.getId() != null) {
                    existingItem = gamesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("VideoGame not found"));
                } else {
                existingItem = item;
                }

                existingItem.setDeveloperStudio(existing);
                existing.getGames().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return developmentstudioRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<DevelopmentStudio> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        DevelopmentStudio entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getGames() != null) {
            for (var child : entity.getGames()) {
                // retirer la référence inverse
                child.setDeveloperStudio(null);
            }
            entity.getGames().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}