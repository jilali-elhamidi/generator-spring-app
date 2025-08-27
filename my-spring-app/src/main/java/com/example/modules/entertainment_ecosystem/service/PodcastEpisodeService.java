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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class PodcastEpisodeService extends BaseService<PodcastEpisode> {

    protected final PodcastEpisodeRepository podcastepisodeRepository;
    
    protected final PodcastRepository podcastRepository;
    
    protected final EpisodeRepository relatedEpisodeRepository;
    
    protected final PodcastGuestRepository guestAppearancesRepository;
    

    public PodcastEpisodeService(PodcastEpisodeRepository repository, PodcastRepository podcastRepository, EpisodeRepository relatedEpisodeRepository, PodcastGuestRepository guestAppearancesRepository)
    {
        super(repository);
        this.podcastepisodeRepository = repository;
        
        this.podcastRepository = podcastRepository;
        
        this.relatedEpisodeRepository = relatedEpisodeRepository;
        
        this.guestAppearancesRepository = guestAppearancesRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<PodcastEpisode> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<PodcastEpisode> search(Map<String, String> filters, Pageable pageable) {
        return super.search(PodcastEpisode.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<PodcastEpisode> saveAll(List<PodcastEpisode> podcastepisodeList) {
        return super.saveAll(podcastepisodeList);
    }

}