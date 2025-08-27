package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.TVShowMerchandise;
import com.example.modules.entertainment_ecosystem.repository.TVShowMerchandiseRepository;

import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class TVShowMerchandiseService extends BaseService<TVShowMerchandise> {

    protected final TVShowMerchandiseRepository tvshowmerchandiseRepository;
    
    protected final TVShowRepository tvShowRepository;
    

    public TVShowMerchandiseService(TVShowMerchandiseRepository repository, TVShowRepository tvShowRepository)
    {
        super(repository);
        this.tvshowmerchandiseRepository = repository;
        
        this.tvShowRepository = tvShowRepository;
        
    }

    @Transactional
    @Override
    public TVShowMerchandise save(TVShowMerchandise tvshowmerchandise) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (tvshowmerchandise.getTvShow() != null) {
            if (tvshowmerchandise.getTvShow().getId() != null) {
                TVShow existingTvShow = tvShowRepository.findById(
                    tvshowmerchandise.getTvShow().getId()
                ).orElseThrow(() -> new RuntimeException("TVShow not found with id "
                    + tvshowmerchandise.getTvShow().getId()));
                tvshowmerchandise.setTvShow(existingTvShow);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                TVShow newTvShow = tvShowRepository.save(tvshowmerchandise.getTvShow());
                tvshowmerchandise.setTvShow(newTvShow);
            }
        }
        
    // ---------- OneToOne ----------
    return tvshowmerchandiseRepository.save(tvshowmerchandise);
}

    @Transactional
    @Override
    public TVShowMerchandise update(Long id, TVShowMerchandise tvshowmerchandiseRequest) {
        TVShowMerchandise existing = tvshowmerchandiseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TVShowMerchandise not found"));

    // Copier les champs simples
        existing.setName(tvshowmerchandiseRequest.getName());
        existing.setPrice(tvshowmerchandiseRequest.getPrice());

    // ---------- Relations ManyToOne ----------
        if (tvshowmerchandiseRequest.getTvShow() != null &&
            tvshowmerchandiseRequest.getTvShow().getId() != null) {

            TVShow existingTvShow = tvShowRepository.findById(
                tvshowmerchandiseRequest.getTvShow().getId()
            ).orElseThrow(() -> new RuntimeException("TVShow not found"));

            existing.setTvShow(existingTvShow);
        } else {
            existing.setTvShow(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return tvshowmerchandiseRepository.save(existing);
}

    // Pagination simple
    public Page<TVShowMerchandise> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<TVShowMerchandise> search(Map<String, String> filters, Pageable pageable) {
        return super.search(TVShowMerchandise.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<TVShowMerchandise> saveAll(List<TVShowMerchandise> tvshowmerchandiseList) {
        return super.saveAll(tvshowmerchandiseList);
    }

}