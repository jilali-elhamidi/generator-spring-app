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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class SeasonService extends BaseService<Season> {

    protected final SeasonRepository seasonRepository;
    private final TVShowRepository showRepository;
    private final EpisodeRepository episodesRepository;

    public SeasonService(SeasonRepository repository, TVShowRepository showRepository, EpisodeRepository episodesRepository)
    {
        super(repository);
        this.seasonRepository = repository;
        this.showRepository = showRepository;
        this.episodesRepository = episodesRepository;
    }

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
        
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Season> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Season entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getEpisodes() != null) {
            for (var child : entity.getEpisodes()) {
                // retirer la référence inverse
                child.setSeason(null);
            }
            entity.getEpisodes().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getShow() != null) {
            entity.setShow(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Season> saveAll(List<Season> seasonList) {

        return seasonRepository.saveAll(seasonList);
    }

}