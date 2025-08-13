package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.DevelopmentStudio;
import com.example.modules.entertainment_ecosystem.repository.DevelopmentStudioRepository;
import com.example.modules.entertainment_ecosystem.model.VideoGame;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class DevelopmentStudioService extends BaseService<DevelopmentStudio> {

    protected final DevelopmentStudioRepository developmentstudioRepository;

    public DevelopmentStudioService(DevelopmentStudioRepository repository)
    {
        super(repository);
        this.developmentstudioRepository = repository;
    }

    @Override
    public DevelopmentStudio save(DevelopmentStudio developmentstudio) {

        if (developmentstudio.getGames() != null) {
            for (VideoGame item : developmentstudio.getGames()) {
            item.setDeveloperStudio(developmentstudio);
            }
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

        existing.getGames().clear();
        if (developmentstudioRequest.getGames() != null) {
            for (var item : developmentstudioRequest.getGames()) {
            item.setDeveloperStudio(existing);
            existing.getGames().add(item);
            }
        }

    


        return developmentstudioRepository.save(existing);
    }
}