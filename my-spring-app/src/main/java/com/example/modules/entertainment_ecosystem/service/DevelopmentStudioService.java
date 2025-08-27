package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.DevelopmentStudio;
import com.example.modules.entertainment_ecosystem.repository.DevelopmentStudioRepository;

import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class DevelopmentStudioService extends BaseService<DevelopmentStudio> {

    protected final DevelopmentStudioRepository developmentstudioRepository;
    
    protected final VideoGameRepository gamesRepository;
    

    public DevelopmentStudioService(DevelopmentStudioRepository repository, VideoGameRepository gamesRepository)
    {
        super(repository);
        this.developmentstudioRepository = repository;
        
        this.gamesRepository = gamesRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public DevelopmentStudio update(Long id, DevelopmentStudio developmentstudioRequest) {
        DevelopmentStudio existing = developmentstudioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("DevelopmentStudio not found"));

    // Copier les champs simples
        existing.setName(developmentstudioRequest.getName());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<DevelopmentStudio> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<DevelopmentStudio> search(Map<String, String> filters, Pageable pageable) {
        return super.search(DevelopmentStudio.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<DevelopmentStudio> saveAll(List<DevelopmentStudio> developmentstudioList) {
        return super.saveAll(developmentstudioList);
    }

}