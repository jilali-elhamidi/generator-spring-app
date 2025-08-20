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

    public SeasonService(SeasonRepository repository,TVShowRepository showRepository,EpisodeRepository episodesRepository)
    {
        super(repository);
        this.seasonRepository = repository;
        this.showRepository = showRepository;
        this.episodesRepository = episodesRepository;
    }

    @Override
    public Season save(Season season) {


    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (season.getEpisodes() != null) {
            List<Episode> managedEpisodes = new ArrayList<>();
            for (Episode item : season.getEpisodes()) {
            if (item.getId() != null) {
            Episode existingItem = episodesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Episode not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setSeason(season);
            managedEpisodes.add(existingItem);
            } else {
            item.setSeason(season);
            managedEpisodes.add(item);
            }
            }
            season.setEpisodes(managedEpisodes);
            }
        
    


    

    

    if (season.getShow() != null
        && season.getShow().getId() != null) {
        TVShow existingShow = showRepository.findById(
        season.getShow().getId()
        ).orElseThrow(() -> new RuntimeException("TVShow not found"));
        season.setShow(existingShow);
        }
    
    

        return seasonRepository.save(season);
    }


    public Season update(Long id, Season seasonRequest) {
        Season existing = seasonRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Season not found"));

    // Copier les champs simples
        existing.setSeasonNumber(seasonRequest.getSeasonNumber());

// Relations ManyToOne : mise à jour conditionnelle
        if (seasonRequest.getShow() != null &&
        seasonRequest.getShow().getId() != null) {

        TVShow existingShow = showRepository.findById(
        seasonRequest.getShow().getId()
        ).orElseThrow(() -> new RuntimeException("TVShow not found"));

        existing.setShow(existingShow);
        } else {
        existing.setShow(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getEpisodes().clear();

        if (seasonRequest.getEpisodes() != null) {
        for (var item : seasonRequest.getEpisodes()) {
        Episode existingItem;
        if (item.getId() != null) {
        existingItem = episodesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Episode not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setSeason(existing);

        // Ajouter directement dans la collection existante
        existing.getEpisodes().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    


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
        
            child.setSeason(null); // retirer la référence inverse
        
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
}