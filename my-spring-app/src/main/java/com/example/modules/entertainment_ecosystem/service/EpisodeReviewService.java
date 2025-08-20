package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EpisodeReview;
import com.example.modules.entertainment_ecosystem.repository.EpisodeReviewRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Episode;
import com.example.modules.entertainment_ecosystem.repository.EpisodeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class EpisodeReviewService extends BaseService<EpisodeReview> {

    protected final EpisodeReviewRepository episodereviewRepository;
    private final UserProfileRepository userRepository;
    private final EpisodeRepository episodeRepository;

    public EpisodeReviewService(EpisodeReviewRepository repository, UserProfileRepository userRepository, EpisodeRepository episodeRepository)
    {
        super(repository);
        this.episodereviewRepository = repository;
        this.userRepository = userRepository;
        this.episodeRepository = episodeRepository;
    }

    @Override
    public EpisodeReview save(EpisodeReview episodereview) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (episodereview.getUser() != null &&
            episodereview.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                episodereview.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            episodereview.setUser(existingUser);
        }
        
        if (episodereview.getEpisode() != null &&
            episodereview.getEpisode().getId() != null) {

            Episode existingEpisode = episodeRepository.findById(
                episodereview.getEpisode().getId()
            ).orElseThrow(() -> new RuntimeException("Episode not found"));

            episodereview.setEpisode(existingEpisode);
        }
        
    // ---------- OneToOne ----------

    return episodereviewRepository.save(episodereview);
}


    public EpisodeReview update(Long id, EpisodeReview episodereviewRequest) {
        EpisodeReview existing = episodereviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EpisodeReview not found"));

    // Copier les champs simples
        existing.setRating(episodereviewRequest.getRating());
        existing.setComment(episodereviewRequest.getComment());
        existing.setReviewDate(episodereviewRequest.getReviewDate());

    // ---------- Relations ManyToOne ----------
        if (episodereviewRequest.getUser() != null &&
            episodereviewRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                episodereviewRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (episodereviewRequest.getEpisode() != null &&
            episodereviewRequest.getEpisode().getId() != null) {

            Episode existingEpisode = episodeRepository.findById(
                episodereviewRequest.getEpisode().getId()
            ).orElseThrow(() -> new RuntimeException("Episode not found"));

            existing.setEpisode(existingEpisode);
        } else {
            existing.setEpisode(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------

    return episodereviewRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<EpisodeReview> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        EpisodeReview entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getUser() != null) {
            entity.setUser(null);
        }
        
        if (entity.getEpisode() != null) {
            entity.setEpisode(null);
        }
        
        repository.delete(entity);
        return true;
    }
}