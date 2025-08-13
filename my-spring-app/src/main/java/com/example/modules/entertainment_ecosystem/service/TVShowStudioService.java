package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.TVShowStudio;
import com.example.modules.entertainment_ecosystem.repository.TVShowStudioRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class TVShowStudioService extends BaseService<TVShowStudio> {

    protected final TVShowStudioRepository tvshowstudioRepository;

    public TVShowStudioService(TVShowStudioRepository repository)
    {
        super(repository);
        this.tvshowstudioRepository = repository;
    }

    @Override
    public TVShowStudio save(TVShowStudio tvshowstudio) {

        if (tvshowstudio.getTvShows() != null) {
            for (TVShow item : tvshowstudio.getTvShows()) {
            item.setTvShowStudio(tvshowstudio);
            }
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
            for (var item : tvshowstudioRequest.getTvShows()) {
            item.setTvShowStudio(existing);
            existing.getTvShows().add(item);
            }
        }

    


        return tvshowstudioRepository.save(existing);
    }
}