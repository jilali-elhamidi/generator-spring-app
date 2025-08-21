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

    public PodcastEpisodeService(PodcastEpisodeRepository repository, PodcastRepository podcastRepository, EpisodeRepository relatedEpisodeRepository, PodcastGuestRepository guestAppearancesRepository)
    {
        super(repository);
        this.podcastepisodeRepository = repository;
        this.podcastRepository = podcastRepository;
        this.relatedEpisodeRepository = relatedEpisodeRepository;
        this.guestAppearancesRepository = guestAppearancesRepository;
    }

    @Override
    public PodcastEpisode save(PodcastEpisode podcastepisode) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (podcastepisode.getGuestAppearances() != null &&
            !podcastepisode.getGuestAppearances().isEmpty()) {

            List<PodcastGuest> attachedGuestAppearances = new ArrayList<>();
            for (PodcastGuest item : podcastepisode.getGuestAppearances()) {
                if (item.getId() != null) {
                    PodcastGuest existingItem = guestAppearancesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("PodcastGuest not found with id " + item.getId()));
                    attachedGuestAppearances.add(existingItem);
                } else {

                    PodcastGuest newItem = guestAppearancesRepository.save(item);
                    attachedGuestAppearances.add(newItem);
                }
            }

            podcastepisode.setGuestAppearances(attachedGuestAppearances);

            // côté propriétaire (PodcastGuest → PodcastEpisode)
            attachedGuestAppearances.forEach(it -> it.getAppearances().add(podcastepisode));
        }
        
    // ---------- ManyToOne ----------
        if (podcastepisode.getPodcast() != null) {
            if (podcastepisode.getPodcast().getId() != null) {
                Podcast existingPodcast = podcastRepository.findById(
                    podcastepisode.getPodcast().getId()
                ).orElseThrow(() -> new RuntimeException("Podcast not found with id "
                    + podcastepisode.getPodcast().getId()));
                podcastepisode.setPodcast(existingPodcast);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Podcast newPodcast = podcastRepository.save(podcastepisode.getPodcast());
                podcastepisode.setPodcast(newPodcast);
            }
        }
        
    // ---------- OneToOne ----------
        if (podcastepisode.getRelatedEpisode() != null) {
            if (podcastepisode.getRelatedEpisode().getId() != null) {
                Episode existingRelatedEpisode = relatedEpisodeRepository.findById(podcastepisode.getRelatedEpisode().getId())
                    .orElseThrow(() -> new RuntimeException("Episode not found with id "
                        + podcastepisode.getRelatedEpisode().getId()));
                podcastepisode.setRelatedEpisode(existingRelatedEpisode);
            } else {
                // Nouvel objet → sauvegarde d'abord
                Episode newRelatedEpisode = relatedEpisodeRepository.save(podcastepisode.getRelatedEpisode());
                podcastepisode.setRelatedEpisode(newRelatedEpisode);
            }

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

    // ---------- Relations ManyToOne ----------
        if (podcastepisodeRequest.getPodcast() != null &&
            podcastepisodeRequest.getPodcast().getId() != null) {

            Podcast existingPodcast = podcastRepository.findById(
                podcastepisodeRequest.getPodcast().getId()
            ).orElseThrow(() -> new RuntimeException("Podcast not found"));

            existing.setPodcast(existingPodcast);
        } else {
            existing.setPodcast(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (podcastepisodeRequest.getGuestAppearances() != null) {
            existing.getGuestAppearances().clear();

            List<PodcastGuest> guestAppearancesList = podcastepisodeRequest.getGuestAppearances().stream()
                .map(item -> guestAppearancesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("PodcastGuest not found")))
                .collect(Collectors.toList());

            existing.getGuestAppearances().addAll(guestAppearancesList);

            // Mettre à jour le côté inverse
            guestAppearancesList.forEach(it -> {
                if (!it.getAppearances().contains(existing)) {
                    it.getAppearances().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (podcastepisodeRequest.getRelatedEpisode() != null &&podcastepisodeRequest.getRelatedEpisode().getId() != null) {

        Episode relatedEpisode = relatedEpisodeRepository.findById(podcastepisodeRequest.getRelatedEpisode().getId())
                .orElseThrow(() -> new RuntimeException("Episode not found"));

        existing.setRelatedEpisode(relatedEpisode);
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
                
                item.getAppearances().remove(entity); // retire côté inverse
            }
            entity.getGuestAppearances().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
        if (entity.getRelatedEpisode() != null) {
            entity.getRelatedEpisode().setRelatedPodcastEpisode(null);
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