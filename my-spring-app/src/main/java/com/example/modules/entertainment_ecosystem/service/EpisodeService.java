package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Episode;
import com.example.modules.entertainment_ecosystem.repository.EpisodeRepository;
import com.example.modules.entertainment_ecosystem.model.Season;
import com.example.modules.entertainment_ecosystem.repository.SeasonRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.PodcastEpisode;
import com.example.modules.entertainment_ecosystem.repository.PodcastEpisodeRepository;
import com.example.modules.entertainment_ecosystem.model.EpisodeCredit;
import com.example.modules.entertainment_ecosystem.repository.EpisodeCreditRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class EpisodeService extends BaseService<Episode> {

    protected final EpisodeRepository episodeRepository;
    private final SeasonRepository seasonRepository;
    private final UserProfileRepository watchedByUsersRepository;
    private final PodcastEpisodeRepository relatedPodcastEpisodeRepository;
    private final EpisodeCreditRepository creditsRepository;

    public EpisodeService(EpisodeRepository repository,SeasonRepository seasonRepository,UserProfileRepository watchedByUsersRepository,PodcastEpisodeRepository relatedPodcastEpisodeRepository,EpisodeCreditRepository creditsRepository)
    {
        super(repository);
        this.episodeRepository = repository;
        this.seasonRepository = seasonRepository;
        this.watchedByUsersRepository = watchedByUsersRepository;
        this.relatedPodcastEpisodeRepository = relatedPodcastEpisodeRepository;
        this.creditsRepository = creditsRepository;
    }

    @Override
    public Episode save(Episode episode) {


    

    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (episode.getCredits() != null) {
            List<EpisodeCredit> managedCredits = new ArrayList<>();
            for (EpisodeCredit item : episode.getCredits()) {
            if (item.getId() != null) {
            EpisodeCredit existingItem = creditsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("EpisodeCredit not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setEpisode(episode);
            managedCredits.add(existingItem);
            } else {
            item.setEpisode(episode);
            managedCredits.add(item);
            }
            }
            episode.setCredits(managedCredits);
            }
        
    

    if (episode.getSeason() != null
        && episode.getSeason().getId() != null) {
        Season existingSeason = seasonRepository.findById(
        episode.getSeason().getId()
        ).orElseThrow(() -> new RuntimeException("Season not found"));
        episode.setSeason(existingSeason);
        }
    
    
    
    
        if (episode.getRelatedPodcastEpisode() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            episode.setRelatedPodcastEpisode(
            relatedPodcastEpisodeRepository.findById(episode.getRelatedPodcastEpisode().getId())
            .orElseThrow(() -> new RuntimeException("relatedPodcastEpisode not found"))
            );
        
        episode.getRelatedPodcastEpisode().setRelatedEpisode(episode);
        }

        return episodeRepository.save(episode);
    }


    public Episode update(Long id, Episode episodeRequest) {
        Episode existing = episodeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Episode not found"));

    // Copier les champs simples
        existing.setEpisodeNumber(episodeRequest.getEpisodeNumber());
        existing.setTitle(episodeRequest.getTitle());
        existing.setDescription(episodeRequest.getDescription());
        existing.setDurationMinutes(episodeRequest.getDurationMinutes());

// Relations ManyToOne : mise à jour conditionnelle
        if (episodeRequest.getSeason() != null &&
        episodeRequest.getSeason().getId() != null) {

        Season existingSeason = seasonRepository.findById(
        episodeRequest.getSeason().getId()
        ).orElseThrow(() -> new RuntimeException("Season not found"));

        existing.setSeason(existingSeason);
        } else {
        existing.setSeason(null);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (episodeRequest.getWatchedByUsers() != null) {
            existing.getWatchedByUsers().clear();
            List<UserProfile> watchedByUsersList = episodeRequest.getWatchedByUsers().stream()
                .map(item -> watchedByUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getWatchedByUsers().addAll(watchedByUsersList);
        }

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getCredits().clear();

        if (episodeRequest.getCredits() != null) {
        for (var item : episodeRequest.getCredits()) {
        EpisodeCredit existingItem;
        if (item.getId() != null) {
        existingItem = creditsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("EpisodeCredit not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setEpisode(existing);

        // Ajouter directement dans la collection existante
        existing.getCredits().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

        if (episodeRequest.getRelatedPodcastEpisode() != null
        && episodeRequest.getRelatedPodcastEpisode().getId() != null) {

        PodcastEpisode relatedPodcastEpisode = relatedPodcastEpisodeRepository.findById(
        episodeRequest.getRelatedPodcastEpisode().getId()
        ).orElseThrow(() -> new RuntimeException("PodcastEpisode not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setRelatedPodcastEpisode(relatedPodcastEpisode);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            relatedPodcastEpisode.setRelatedEpisode(existing);
        
        }

    

    


        return episodeRepository.save(existing);
    }


}