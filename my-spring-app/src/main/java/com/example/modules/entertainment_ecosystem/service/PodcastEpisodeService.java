package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.PodcastEpisode;
import com.example.modules.entertainment_ecosystem.repository.PodcastEpisodeRepository;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;
import com.example.modules.entertainment_ecosystem.model.Episode;
import com.example.modules.entertainment_ecosystem.repository.EpisodeRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class PodcastEpisodeService extends BaseService<PodcastEpisode> {

    protected final PodcastEpisodeRepository podcastepisodeRepository;
    private final PodcastRepository podcastRepository;
    private final EpisodeRepository relatedEpisodeRepository;

    public PodcastEpisodeService(PodcastEpisodeRepository repository,PodcastRepository podcastRepository,EpisodeRepository relatedEpisodeRepository)
    {
        super(repository);
        this.podcastepisodeRepository = repository;
        this.podcastRepository = podcastRepository;
            this.relatedEpisodeRepository = relatedEpisodeRepository;
    }

    @Override
    public PodcastEpisode save(PodcastEpisode podcastepisode) {

        if (podcastepisode.getPodcast() != null && podcastepisode.getPodcast().getId() != null) {
        Podcast podcast = podcastRepository.findById(podcastepisode.getPodcast().getId())
                .orElseThrow(() -> new RuntimeException("Podcast not found"));
        podcastepisode.setPodcast(podcast);
        }
        if (podcastepisode.getRelatedEpisode() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            podcastepisode.setRelatedEpisode(
            relatedEpisodeRepository.findById(podcastepisode.getRelatedEpisode().getId())
            .orElseThrow(() -> new RuntimeException("relatedEpisode not found"))
            );
        
        podcastepisode.getRelatedEpisode().setRelatedPodcastEpisode(podcastepisode);
        }

        return podcastepisodeRepository.save(podcastepisode);
    }


    public PodcastEpisode update(Long id, PodcastEpisode podcastepisodeRequest) {
        PodcastEpisode existing = podcastepisodeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PodcastEpisode not found"));

    // Copier les champs simples
        existing.setTitle(podcastepisodeRequest.getTitle());
        existing.setReleaseDate(podcastepisodeRequest.getReleaseDate());
        existing.setDurationMinutes(podcastepisodeRequest.getDurationMinutes());

// Relations ManyToOne : mise à jour conditionnelle

        if (podcastepisodeRequest.getPodcast() != null && podcastepisodeRequest.getPodcast().getId() != null) {
        Podcast podcast = podcastRepository.findById(podcastepisodeRequest.getPodcast().getId())
                .orElseThrow(() -> new RuntimeException("Podcast not found"));
        existing.setPodcast(podcast);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

        if (podcastepisodeRequest.getRelatedEpisode() != null
        && podcastepisodeRequest.getRelatedEpisode().getId() != null) {

        Episode relatedEpisode = relatedEpisodeRepository.findById(
        podcastepisodeRequest.getRelatedEpisode().getId()
        ).orElseThrow(() -> new RuntimeException("Episode not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setRelatedEpisode(relatedEpisode);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            relatedEpisode.setRelatedPodcastEpisode(existing);
        
        }

    


        return podcastepisodeRepository.save(existing);
    }
}