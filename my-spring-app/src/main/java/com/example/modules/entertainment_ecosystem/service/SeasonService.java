package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Season;
import com.example.modules.entertainment_ecosystem.repository.SeasonRepository;

import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;

import com.example.modules.entertainment_ecosystem.model.Episode;
import com.example.modules.entertainment_ecosystem.repository.EpisodeRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class SeasonService extends BaseService<Season> {

    protected final SeasonRepository seasonRepository;
    
    protected final TVShowRepository showRepository;
    
    protected final EpisodeRepository episodesRepository;
    

    public SeasonService(SeasonRepository repository, TVShowRepository showRepository, EpisodeRepository episodesRepository)
    {
        super(repository);
        this.seasonRepository = repository;
        
        this.showRepository = showRepository;
        
        this.episodesRepository = episodesRepository;
        
    }

    @Transactional
    @Override
    public Season save(Season season) {
    // ---------- OneToMany ----------
        if (season.getEpisodes() != null) {
            List<Episode> managedEpisodes = new ArrayList<>();
            for (Episode item : season.getEpisodes()) {
                if (item.getId() != null) {
                    Episode existingItem = episodesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Episode not found"));

                     existingItem.setSeason(season);
                     managedEpisodes.add(existingItem);
                } else {
                    item.setSeason(season);
                    managedEpisodes.add(item);
                }
            }
            season.setEpisodes(managedEpisodes);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (season.getShow() != null) {
            if (season.getShow().getId() != null) {
                TVShow existingShow = showRepository.findById(
                    season.getShow().getId()
                ).orElseThrow(() -> new RuntimeException("TVShow not found with id "
                    + season.getShow().getId()));
                season.setShow(existingShow);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                TVShow newShow = showRepository.save(season.getShow());
                season.setShow(newShow);
            }
        }
        
    // ---------- OneToOne ----------
    return seasonRepository.save(season);
}

    @Transactional
    @Override
    public Season update(Long id, Season seasonRequest) {
        Season existing = seasonRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Season not found"));

    // Copier les champs simples
        existing.setSeasonNumber(seasonRequest.getSeasonNumber());

    // ---------- Relations ManyToOne ----------
        if (seasonRequest.getShow() != null &&
            seasonRequest.getShow().getId() != null) {

            TVShow existingShow = showRepository.findById(
                seasonRequest.getShow().getId()
            ).orElseThrow(() -> new RuntimeException("TVShow not found"));

            existing.setShow(existingShow);
        } else {
            existing.setShow(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getEpisodes().clear();

        if (seasonRequest.getEpisodes() != null) {
            for (var item : seasonRequest.getEpisodes()) {
                Episode existingItem;
                if (item.getId() != null) {
                    existingItem = episodesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Episode not found"));
                } else {
                existingItem = item;
                }

                existingItem.setSeason(existing);
                existing.getEpisodes().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return seasonRepository.save(existing);
}

    // Pagination simple
    public Page<Season> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Season> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Season.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Season> saveAll(List<Season> seasonList) {
        return super.saveAll(seasonList);
    }

}