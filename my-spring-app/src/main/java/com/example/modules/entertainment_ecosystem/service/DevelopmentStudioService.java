package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.DevelopmentStudio;
import com.example.modules.entertainment_ecosystem.repository.DevelopmentStudioRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class DevelopmentStudioService extends BaseService<DevelopmentStudio> {

    protected final DevelopmentStudioRepository developmentstudioRepository;
    private final VideoGameRepository gamesRepository;

    public DevelopmentStudioService(DevelopmentStudioRepository repository,VideoGameRepository gamesRepository)
    {
        super(repository);
        this.developmentstudioRepository = repository;
        this.gamesRepository = gamesRepository;
    }

    @Override
    public DevelopmentStudio save(DevelopmentStudio developmentstudio) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (developmentstudio.getGames() != null) {
            List<VideoGame> managedGames = new ArrayList<>();
            for (VideoGame item : developmentstudio.getGames()) {
            if (item.getId() != null) {
            VideoGame existingItem = gamesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("VideoGame not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setDeveloperStudio(developmentstudio);
            managedGames.add(existingItem);
            } else {
            item.setDeveloperStudio(developmentstudio);
            managedGames.add(item);
            }
            }
            developmentstudio.setGames(managedGames);
            }
        
    

    

        return developmentstudioRepository.save(developmentstudio);
    }


    public DevelopmentStudio update(Long id, DevelopmentStudio developmentstudioRequest) {
        DevelopmentStudio existing = developmentstudioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("DevelopmentStudio not found"));

    // Copier les champs simples
        existing.setName(developmentstudioRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getGames().clear();

        if (developmentstudioRequest.getGames() != null) {
        for (var item : developmentstudioRequest.getGames()) {
        VideoGame existingItem;
        if (item.getId() != null) {
        existingItem = gamesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setDeveloperStudio(existing);

        // Ajouter directement dans la collection existante
        existing.getGames().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return developmentstudioRepository.save(existing);
    }


}