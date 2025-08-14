package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.TVShowMerchandise;
import com.example.modules.entertainment_ecosystem.repository.TVShowMerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class TVShowMerchandiseService extends BaseService<TVShowMerchandise> {

    protected final TVShowMerchandiseRepository tvshowmerchandiseRepository;
    private final TVShowRepository tvShowRepository;

    public TVShowMerchandiseService(TVShowMerchandiseRepository repository,TVShowRepository tvShowRepository)
    {
        super(repository);
        this.tvshowmerchandiseRepository = repository;
        this.tvShowRepository = tvShowRepository;
    }

    @Override
    public TVShowMerchandise save(TVShowMerchandise tvshowmerchandise) {


    

    if (tvshowmerchandise.getTvShow() != null
        && tvshowmerchandise.getTvShow().getId() != null) {
        TVShow existingTvShow = tvShowRepository.findById(
        tvshowmerchandise.getTvShow().getId()
        ).orElseThrow(() -> new RuntimeException("TVShow not found"));
        tvshowmerchandise.setTvShow(existingTvShow);
        }
    

        return tvshowmerchandiseRepository.save(tvshowmerchandise);
    }


    public TVShowMerchandise update(Long id, TVShowMerchandise tvshowmerchandiseRequest) {
        TVShowMerchandise existing = tvshowmerchandiseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TVShowMerchandise not found"));

    // Copier les champs simples
        existing.setName(tvshowmerchandiseRequest.getName());
        existing.setPrice(tvshowmerchandiseRequest.getPrice());

// Relations ManyToOne : mise à jour conditionnelle
        if (tvshowmerchandiseRequest.getTvShow() != null &&
        tvshowmerchandiseRequest.getTvShow().getId() != null) {

        TVShow existingTvShow = tvShowRepository.findById(
        tvshowmerchandiseRequest.getTvShow().getId()
        ).orElseThrow(() -> new RuntimeException("TVShow not found"));

        existing.setTvShow(existingTvShow);
        } else {
        existing.setTvShow(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


        return tvshowmerchandiseRepository.save(existing);
    }


}