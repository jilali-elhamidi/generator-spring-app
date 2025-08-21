package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.PodcastGuest;
import com.example.modules.entertainment_ecosystem.repository.PodcastGuestRepository;
import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.repository.PodcastRepository;
import com.example.modules.entertainment_ecosystem.model.PodcastEpisode;
import com.example.modules.entertainment_ecosystem.repository.PodcastEpisodeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class PodcastGuestService extends BaseService<PodcastGuest> {

    protected final PodcastGuestRepository podcastguestRepository;
    private final PodcastRepository podcastRepository;
    private final PodcastEpisodeRepository appearancesRepository;

    public PodcastGuestService(PodcastGuestRepository repository, PodcastRepository podcastRepository, PodcastEpisodeRepository appearancesRepository)
    {
        super(repository);
        this.podcastguestRepository = repository;
        this.podcastRepository = podcastRepository;
        this.appearancesRepository = appearancesRepository;
    }

    @Override
    public PodcastGuest save(PodcastGuest podcastguest) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (podcastguest.getAppearances() != null &&
            !podcastguest.getAppearances().isEmpty()) {

            List<PodcastEpisode> attachedAppearances = new ArrayList<>();
            for (PodcastEpisode item : podcastguest.getAppearances()) {
                if (item.getId() != null) {
                    PodcastEpisode existingItem = appearancesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("PodcastEpisode not found with id " + item.getId()));
                    attachedAppearances.add(existingItem);
                } else {

                    PodcastEpisode newItem = appearancesRepository.save(item);
                    attachedAppearances.add(newItem);
                }
            }

            podcastguest.setAppearances(attachedAppearances);

            // côté propriétaire (PodcastEpisode → PodcastGuest)
            attachedAppearances.forEach(it -> it.getGuestAppearances().add(podcastguest));
        }
        
    // ---------- ManyToOne ----------
        if (podcastguest.getPodcast() != null) {
            if (podcastguest.getPodcast().getId() != null) {
                Podcast existingPodcast = podcastRepository.findById(
                    podcastguest.getPodcast().getId()
                ).orElseThrow(() -> new RuntimeException("Podcast not found with id "
                    + podcastguest.getPodcast().getId()));
                podcastguest.setPodcast(existingPodcast);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Podcast newPodcast = podcastRepository.save(podcastguest.getPodcast());
                podcastguest.setPodcast(newPodcast);
            }
        }
        
    // ---------- OneToOne ----------
    return podcastguestRepository.save(podcastguest);
}


    public PodcastGuest update(Long id, PodcastGuest podcastguestRequest) {
        PodcastGuest existing = podcastguestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PodcastGuest not found"));

    // Copier les champs simples
        existing.setName(podcastguestRequest.getName());
        existing.setBio(podcastguestRequest.getBio());

    // ---------- Relations ManyToOne ----------
        if (podcastguestRequest.getPodcast() != null &&
            podcastguestRequest.getPodcast().getId() != null) {

            Podcast existingPodcast = podcastRepository.findById(
                podcastguestRequest.getPodcast().getId()
            ).orElseThrow(() -> new RuntimeException("Podcast not found"));

            existing.setPodcast(existingPodcast);
        } else {
            existing.setPodcast(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (podcastguestRequest.getAppearances() != null) {
            existing.getAppearances().clear();

            List<PodcastEpisode> appearancesList = podcastguestRequest.getAppearances().stream()
                .map(item -> appearancesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("PodcastEpisode not found")))
                .collect(Collectors.toList());

            existing.getAppearances().addAll(appearancesList);

            // Mettre à jour le côté inverse
            appearancesList.forEach(it -> {
                if (!it.getGuestAppearances().contains(existing)) {
                    it.getGuestAppearances().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return podcastguestRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<PodcastGuest> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        PodcastGuest entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
        if (entity.getAppearances() != null) {
            for (PodcastEpisode item : new ArrayList<>(entity.getAppearances())) {
                
                item.getGuestAppearances().remove(entity); // retire côté inverse
            }
            entity.getAppearances().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getPodcast() != null) {
            entity.setPodcast(null);
        }
        
        repository.delete(entity);
        return true;
    }
}