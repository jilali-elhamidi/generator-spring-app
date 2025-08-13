package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.PodcastGuest;
import com.example.modules.entertainment_ecosystem.repository.PodcastGuestRepository;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;
import com.example.modules.entertainment_ecosystem.model.PodcastEpisode;
import com.example.modules.entertainment_ecosystem.repository.PodcastEpisodeRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class PodcastGuestService extends BaseService<PodcastGuest> {

    protected final PodcastGuestRepository podcastguestRepository;
    private final PodcastRepository podcastRepository;
    private final PodcastEpisodeRepository appearancesRepository;

    public PodcastGuestService(PodcastGuestRepository repository,PodcastRepository podcastRepository,PodcastEpisodeRepository appearancesRepository)
    {
        super(repository);
        this.podcastguestRepository = repository;
        this.podcastRepository = podcastRepository;
        this.appearancesRepository = appearancesRepository;
    }

    @Override
    public PodcastGuest save(PodcastGuest podcastguest) {

        if (podcastguest.getPodcast() != null && podcastguest.getPodcast().getId() != null) {
        Podcast podcast = podcastRepository.findById(podcastguest.getPodcast().getId())
                .orElseThrow(() -> new RuntimeException("Podcast not found"));
        podcastguest.setPodcast(podcast);
        }

        return podcastguestRepository.save(podcastguest);
    }


    public PodcastGuest update(Long id, PodcastGuest podcastguestRequest) {
        PodcastGuest existing = podcastguestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PodcastGuest not found"));

    // Copier les champs simples
        existing.setName(podcastguestRequest.getName());
        existing.setBio(podcastguestRequest.getBio());

// Relations ManyToOne : mise à jour conditionnelle

        if (podcastguestRequest.getPodcast() != null && podcastguestRequest.getPodcast().getId() != null) {
        Podcast podcast = podcastRepository.findById(podcastguestRequest.getPodcast().getId())
                .orElseThrow(() -> new RuntimeException("Podcast not found"));
        existing.setPodcast(podcast);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (podcastguestRequest.getAppearances() != null) {
            existing.getAppearances().clear();
            List<PodcastEpisode> appearancesList = podcastguestRequest.getAppearances().stream()
                .map(item -> appearancesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("PodcastEpisode not found")))
                .collect(Collectors.toList());
        existing.getAppearances().addAll(appearancesList);
        }

// Relations OneToMany : synchronisation sécurisée

    

    


        return podcastguestRepository.save(existing);
    }
}