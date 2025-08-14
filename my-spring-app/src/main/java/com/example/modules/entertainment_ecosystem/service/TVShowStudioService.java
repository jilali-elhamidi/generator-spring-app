package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.TVShowStudio;
import com.example.modules.entertainment_ecosystem.repository.TVShowStudioRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class TVShowStudioService extends BaseService<TVShowStudio> {

    protected final TVShowStudioRepository tvshowstudioRepository;
    private final TVShowRepository tvShowsRepository;

    public TVShowStudioService(TVShowStudioRepository repository,TVShowRepository tvShowsRepository)
    {
        super(repository);
        this.tvshowstudioRepository = repository;
        this.tvShowsRepository = tvShowsRepository;
    }

    @Override
    public TVShowStudio save(TVShowStudio tvshowstudio) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (tvshowstudio.getTvShows() != null) {
            List<TVShow> managedTvShows = new ArrayList<>();
            for (TVShow item : tvshowstudio.getTvShows()) {
            if (item.getId() != null) {
            TVShow existingItem = tvShowsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("TVShow not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setTvShowStudio(tvshowstudio);
            managedTvShows.add(existingItem);
            } else {
            item.setTvShowStudio(tvshowstudio);
            managedTvShows.add(item);
            }
            }
            tvshowstudio.setTvShows(managedTvShows);
            }
        
    

    

        return tvshowstudioRepository.save(tvshowstudio);
    }


    public TVShowStudio update(Long id, TVShowStudio tvshowstudioRequest) {
        TVShowStudio existing = tvshowstudioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TVShowStudio not found"));

    // Copier les champs simples
        existing.setName(tvshowstudioRequest.getName());
        existing.setLocation(tvshowstudioRequest.getLocation());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        existing.getTvShows().clear();

        if (tvshowstudioRequest.getTvShows() != null) {
        List<TVShow> managedTvShows = new ArrayList<>();

        for (var item : tvshowstudioRequest.getTvShows()) {
        if (item.getId() != null) {
        TVShow existingItem = tvShowsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("TVShow not found"));
        existingItem.setTvShowStudio(existing);
        managedTvShows.add(existingItem);
        } else {
        item.setTvShowStudio(existing);
        managedTvShows.add(item);
        }
        }
        existing.setTvShows(managedTvShows);
        }

    


        return tvshowstudioRepository.save(existing);
    }


}