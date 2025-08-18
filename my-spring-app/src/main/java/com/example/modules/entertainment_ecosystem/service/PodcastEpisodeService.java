package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.PodcastEpisode;
import com.example.modules.entertainment_ecosystem.repository.PodcastEpisodeRepository;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;
import com.example.modules.entertainment_ecosystem.model.Episode;
import com.example.modules.entertainment_ecosystem.repository.EpisodeRepository;
import com.example.modules.entertainment_ecosystem.model.PodcastGuest;
import com.example.modules.entertainment_ecosystem.repository.PodcastGuestRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class PodcastEpisodeService extends BaseService<PodcastEpisode> {

    protected final PodcastEpisodeRepository podcastepisodeRepository;
    private final PodcastRepository podcastRepository;
    private final EpisodeRepository relatedEpisodeRepository;
    private final PodcastGuestRepository guestAppearancesRepository;

    public PodcastEpisodeService(PodcastEpisodeRepository repository,PodcastRepository podcastRepository,EpisodeRepository relatedEpisodeRepository,PodcastGuestRepository guestAppearancesRepository)
    {
        super(repository);
        this.podcastepisodeRepository = repository;
        this.podcastRepository = podcastRepository;
        this.relatedEpisodeRepository = relatedEpisodeRepository;
        this.guestAppearancesRepository = guestAppearancesRepository;
    }

    @Override
    public PodcastEpisode save(PodcastEpisode podcastepisode) {


    

    

    

    if (podcastepisode.getPodcast() != null
        && podcastepisode.getPodcast().getId() != null) {
        Podcast existingPodcast = podcastRepository.findById(
        podcastepisode.getPodcast().getId()
        ).orElseThrow(() -> new RuntimeException("Podcast not found"));
        podcastepisode.setPodcast(existingPodcast);
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
        if (podcastepisodeRequest.getPodcast() != null &&
        podcastepisodeRequest.getPodcast().getId() != null) {

        Podcast existingPodcast = podcastRepository.findById(
        podcastepisodeRequest.getPodcast().getId()
        ).orElseThrow(() -> new RuntimeException("Podcast not found"));

        existing.setPodcast(existingPodcast);
        } else {
        existing.setPodcast(null);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (podcastepisodeRequest.getGuestAppearances() != null) {
            existing.getGuestAppearances().clear();
            List<PodcastGuest> guestAppearancesList = podcastepisodeRequest.getGuestAppearances().stream()
                .map(item -> guestAppearancesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("PodcastGuest not found")))
                .collect(Collectors.toList());
        existing.getGuestAppearances().addAll(guestAppearancesList);
        }

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
@Transactional
public boolean deleteById(Long id) {
Optional<PodcastEpisode> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

PodcastEpisode entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    

    


// --- Dissocier ManyToMany ---

    

    

    
        if (entity.getGuestAppearances() != null) {
        for (PodcastGuest item : new ArrayList<>(entity.getGuestAppearances())) {
        
        }
        entity.getGuestAppearances().clear(); // puis vide côté courant
        }
    



// --- Dissocier OneToOne ---

    

    
        if (entity.getRelatedEpisode() != null) {
        // Dissocier côté inverse automatiquement
        entity.getRelatedEpisode().setRelatedPodcastEpisode(null);
        // Dissocier côté direct
        entity.setRelatedEpisode(null);
        }
    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getPodcast() != null) {
        entity.setPodcast(null);
        }
    

    

    


repository.delete(entity);
return true;
}
}