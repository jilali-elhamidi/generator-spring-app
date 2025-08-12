package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Episode;
import com.example.modules.entertainment_ecosystem.repository.EpisodeRepository;
import com.example.modules.entertainment_ecosystem.model.Season;
import com.example.modules.entertainment_ecosystem.repository.SeasonRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class EpisodeService extends BaseService<Episode> {

    protected final EpisodeRepository episodeRepository;
    private final SeasonRepository seasonRepository;
    private final UserProfileRepository watchedByUsersRepository;

    public EpisodeService(EpisodeRepository repository,SeasonRepository seasonRepository,UserProfileRepository watchedByUsersRepository)
    {
        super(repository);
        this.episodeRepository = repository;
        this.seasonRepository = seasonRepository;
        this.watchedByUsersRepository = watchedByUsersRepository;
    }

    @Override
    public Episode save(Episode episode) {

        if (episode.getSeason() != null && episode.getSeason().getId() != null) {
        Season season = seasonRepository.findById(episode.getSeason().getId())
                .orElseThrow(() -> new RuntimeException("Season not found"));
        episode.setSeason(season);
        }
        if (episode.getRelatedPodcastEpisode() != null) {
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

        if (episodeRequest.getSeason() != null && episodeRequest.getSeason().getId() != null) {
        Season season = seasonRepository.findById(episodeRequest.getSeason().getId())
                .orElseThrow(() -> new RuntimeException("Season not found"));
        existing.setSeason(season);
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

        return episodeRepository.save(existing);
    }
}