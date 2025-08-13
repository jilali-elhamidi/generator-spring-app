package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Season;
import com.example.modules.entertainment_ecosystem.repository.SeasonRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.Episode;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class SeasonService extends BaseService<Season> {

    protected final SeasonRepository seasonRepository;
    private final TVShowRepository showRepository;

    public SeasonService(SeasonRepository repository,TVShowRepository showRepository)
    {
        super(repository);
        this.seasonRepository = repository;
        this.showRepository = showRepository;
    }

    @Override
    public Season save(Season season) {

        if (season.getShow() != null && season.getShow().getId() != null) {
        TVShow show = showRepository.findById(season.getShow().getId())
                .orElseThrow(() -> new RuntimeException("TVShow not found"));
        season.setShow(show);
        }

        if (season.getEpisodes() != null) {
            for (Episode item : season.getEpisodes()) {
            item.setSeason(season);
            }
        }

        return seasonRepository.save(season);
    }


    public Season update(Long id, Season seasonRequest) {
        Season existing = seasonRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Season not found"));

    // Copier les champs simples
        existing.setSeasonNumber(seasonRequest.getSeasonNumber());

// Relations ManyToOne : mise à jour conditionnelle

        if (seasonRequest.getShow() != null && seasonRequest.getShow().getId() != null) {
        TVShow show = showRepository.findById(seasonRequest.getShow().getId())
                .orElseThrow(() -> new RuntimeException("TVShow not found"));
        existing.setShow(show);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getEpisodes().clear();
        if (seasonRequest.getEpisodes() != null) {
            for (var item : seasonRequest.getEpisodes()) {
            item.setSeason(existing);
            existing.getEpisodes().add(item);
            }
        }

    

    


        return seasonRepository.save(existing);
    }
}