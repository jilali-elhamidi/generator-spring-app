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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class PodcastGuestService extends BaseService<PodcastGuest> {

    protected final PodcastGuestRepository podcastguestRepository;
    
    protected final PodcastRepository podcastRepository;
    
    protected final PodcastEpisodeRepository appearancesRepository;
    

    public PodcastGuestService(PodcastGuestRepository repository, PodcastRepository podcastRepository, PodcastEpisodeRepository appearancesRepository)
    {
        super(repository);
        this.podcastguestRepository = repository;
        
        this.podcastRepository = podcastRepository;
        
        this.appearancesRepository = appearancesRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<PodcastGuest> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<PodcastGuest> search(Map<String, String> filters, Pageable pageable) {
        return super.search(PodcastGuest.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<PodcastGuest> saveAll(List<PodcastGuest> podcastguestList) {
        return super.saveAll(podcastguestList);
    }

}