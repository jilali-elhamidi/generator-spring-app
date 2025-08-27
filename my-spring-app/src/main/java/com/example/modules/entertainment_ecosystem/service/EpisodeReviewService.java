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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class EpisodeReviewService extends BaseService<EpisodeReview> {

    protected final EpisodeReviewRepository episodereviewRepository;
    
    protected final UserProfileRepository userRepository;
    
    protected final EpisodeRepository episodeRepository;
    

    public EpisodeReviewService(EpisodeReviewRepository repository, UserProfileRepository userRepository, EpisodeRepository episodeRepository)
    {
        super(repository);
        this.episodereviewRepository = repository;
        
        this.userRepository = userRepository;
        
        this.episodeRepository = episodeRepository;
        
    }

    @Transactional
    @Override
    public EpisodeReview save(EpisodeReview episodereview) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (episodereview.getUser() != null) {
            if (episodereview.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    episodereview.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + episodereview.getUser().getId()));
                episodereview.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(episodereview.getUser());
                episodereview.setUser(newUser);
            }
        }
        
        if (episodereview.getEpisode() != null) {
            if (episodereview.getEpisode().getId() != null) {
                Episode existingEpisode = episodeRepository.findById(
                    episodereview.getEpisode().getId()
                ).orElseThrow(() -> new RuntimeException("Episode not found with id "
                    + episodereview.getEpisode().getId()));
                episodereview.setEpisode(existingEpisode);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Episode newEpisode = episodeRepository.save(episodereview.getEpisode());
                episodereview.setEpisode(newEpisode);
            }
        }
        
    // ---------- OneToOne ----------
    return episodereviewRepository.save(episodereview);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return episodereviewRepository.save(existing);
}

    // Pagination simple
    public Page<EpisodeReview> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<EpisodeReview> search(Map<String, String> filters, Pageable pageable) {
        return super.search(EpisodeReview.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<EpisodeReview> saveAll(List<EpisodeReview> episodereviewList) {
        return super.saveAll(episodereviewList);
    }

}