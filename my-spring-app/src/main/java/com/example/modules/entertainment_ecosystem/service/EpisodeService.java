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
import com.example.modules.entertainment_ecosystem.model.EpisodeReview;
import com.example.modules.entertainment_ecosystem.repository.EpisodeReviewRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final EpisodeReviewRepository reviewsRepository;

    public EpisodeService(EpisodeRepository repository, SeasonRepository seasonRepository, UserProfileRepository watchedByUsersRepository, PodcastEpisodeRepository relatedPodcastEpisodeRepository, EpisodeCreditRepository creditsRepository, EpisodeReviewRepository reviewsRepository)
    {
        super(repository);
        this.episodeRepository = repository;
        this.seasonRepository = seasonRepository;
        this.watchedByUsersRepository = watchedByUsersRepository;
        this.relatedPodcastEpisodeRepository = relatedPodcastEpisodeRepository;
        this.creditsRepository = creditsRepository;
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public Episode save(Episode episode) {
    // ---------- OneToMany ----------
        if (episode.getCredits() != null) {
            List<EpisodeCredit> managedCredits = new ArrayList<>();
            for (EpisodeCredit item : episode.getCredits()) {
                if (item.getId() != null) {
                    EpisodeCredit existingItem = creditsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("EpisodeCredit not found"));

                     existingItem.setEpisode(episode);
                     managedCredits.add(existingItem);
                } else {
                    item.setEpisode(episode);
                    managedCredits.add(item);
                }
            }
            episode.setCredits(managedCredits);
        }
    
        if (episode.getReviews() != null) {
            List<EpisodeReview> managedReviews = new ArrayList<>();
            for (EpisodeReview item : episode.getReviews()) {
                if (item.getId() != null) {
                    EpisodeReview existingItem = reviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("EpisodeReview not found"));

                     existingItem.setEpisode(episode);
                     managedReviews.add(existingItem);
                } else {
                    item.setEpisode(episode);
                    managedReviews.add(item);
                }
            }
            episode.setReviews(managedReviews);
        }
    
    // ---------- ManyToMany ----------
        if (episode.getWatchedByUsers() != null &&
            !episode.getWatchedByUsers().isEmpty()) {

            List<UserProfile> attachedWatchedByUsers = episode.getWatchedByUsers().stream()
            .map(item -> watchedByUsersRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId())))
            .toList();

            episode.setWatchedByUsers(attachedWatchedByUsers);

            // côté propriétaire (UserProfile → Episode)
            attachedWatchedByUsers.forEach(it -> it.getWatchedEpisodes().add(episode));
        }
        
    // ---------- ManyToOne ----------
        if (episode.getSeason() != null &&
            episode.getSeason().getId() != null) {

            Season existingSeason = seasonRepository.findById(
                episode.getSeason().getId()
            ).orElseThrow(() -> new RuntimeException("Season not found"));

            episode.setSeason(existingSeason);
        }
        
    // ---------- OneToOne ----------
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

    // ---------- Relations ManyToOne ----------
        if (episodeRequest.getSeason() != null &&
            episodeRequest.getSeason().getId() != null) {

            Season existingSeason = seasonRepository.findById(
                episodeRequest.getSeason().getId()
            ).orElseThrow(() -> new RuntimeException("Season not found"));

            existing.setSeason(existingSeason);
        } else {
            existing.setSeason(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (episodeRequest.getWatchedByUsers() != null) {
            existing.getWatchedByUsers().clear();

            List<UserProfile> watchedByUsersList = episodeRequest.getWatchedByUsers().stream()
                .map(item -> watchedByUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());

            existing.getWatchedByUsers().addAll(watchedByUsersList);

            // Mettre à jour le côté inverse
            watchedByUsersList.forEach(it -> {
                if (!it.getWatchedEpisodes().contains(existing)) {
                    it.getWatchedEpisodes().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getCredits().clear();

        if (episodeRequest.getCredits() != null) {
            for (var item : episodeRequest.getCredits()) {
                EpisodeCredit existingItem;
                if (item.getId() != null) {
                    existingItem = creditsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("EpisodeCredit not found"));
                } else {
                existingItem = item;
                }

                existingItem.setEpisode(existing);
                existing.getCredits().add(existingItem);
            }
        }
        
        existing.getReviews().clear();

        if (episodeRequest.getReviews() != null) {
            for (var item : episodeRequest.getReviews()) {
                EpisodeReview existingItem;
                if (item.getId() != null) {
                    existingItem = reviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("EpisodeReview not found"));
                } else {
                existingItem = item;
                }

                existingItem.setEpisode(existing);
                existing.getReviews().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
            if (episodeRequest.getRelatedPodcastEpisode() != null &&
            episodeRequest.getRelatedPodcastEpisode().getId() != null) {

            PodcastEpisode relatedPodcastEpisode = relatedPodcastEpisodeRepository.findById(
                episodeRequest.getRelatedPodcastEpisode().getId()
            ).orElseThrow(() -> new RuntimeException("PodcastEpisode not found"));

            existing.setRelatedPodcastEpisode(relatedPodcastEpisode);

            
            relatedPodcastEpisode.setRelatedEpisode(existing);
            
        }
        

    return episodeRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Episode> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Episode entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getCredits() != null) {
            for (var child : entity.getCredits()) {
                
                child.setEpisode(null); // retirer la référence inverse
                
            }
            entity.getCredits().clear();
        }
        
        if (entity.getReviews() != null) {
            for (var child : entity.getReviews()) {
                
                child.setEpisode(null); // retirer la référence inverse
                
            }
            entity.getReviews().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getWatchedByUsers() != null) {
            for (UserProfile item : new ArrayList<>(entity.getWatchedByUsers())) {
                
                item.getWatchedEpisodes().remove(entity); // retire côté inverse
                
            }
            entity.getWatchedByUsers().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
        if (entity.getRelatedPodcastEpisode() != null) {
            entity.getRelatedPodcastEpisode().setRelatedEpisode(null);
            entity.setRelatedPodcastEpisode(null);
        }
        
    // --- Dissocier ManyToOne ---
        if (entity.getSeason() != null) {
            entity.setSeason(null);
        }
        
        repository.delete(entity);
        return true;
    }
}